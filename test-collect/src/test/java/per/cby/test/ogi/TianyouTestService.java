package per.cby.test.ogi;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import per.cby.collect.common.model.TerminalMessage;
import per.cby.collect.common.util.CollectUtil;
import per.cby.collect.orbcomm.constant.GetMsgStatus;
import per.cby.collect.orbcomm.constant.OrbcommConstant;
import per.cby.collect.orbcomm.constant.SendMsgStatus;
import per.cby.collect.orbcomm.model.OrbcommMessage;
import per.cby.collect.orbcomm.model.OrbcommResult;
import per.cby.collect.orbcomm.service.TianyouService;
import per.cby.collect.satellite.common.constant.SatelliteChannel;
import per.cby.collect.satellite.common.constant.SatelliteConstant;
import per.cby.frame.common.exception.BusinessAssert;
import per.cby.frame.common.util.BaseUtil;
import per.cby.frame.common.util.DateUtil;
import per.cby.frame.common.util.HttpUtil;
import per.cby.frame.common.util.JsonUtil;
import per.cby.frame.common.util.StringUtil;
import per.cby.frame.common.util.ThreadPoolUtil;
import com.fasterxml.jackson.core.type.TypeReference;

import lombok.extern.slf4j.Slf4j;

/**
 * OGI天佑接口服务实现
 * 
 * @author chenboyang
 * @since 2020年2月12日
 *
 */
@Slf4j
public class TianyouTestService implements TianyouService, OrbcommConstant {

    /** 差异时区 */
    private final ZoneOffset DIFF_ZONE = ZoneOffset.ofHours(16);

    /** OGI服务器接口地址 */
    private String api = "http://123.58.32.88/starchat/index.php/Business/Coop";

    /** OGI服务器账户名称 */
    private String name = "jizhi";

    /** OGI服务器密钥 */
    private String key = "82e72454d22ce82781ae9e30ea0d4315";

    @Override
    public void pollMessage(LocalDateTime startTime, LocalDateTime endTime) {
        long start = startTime.toEpochSecond(DIFF_ZONE);
        long end = endTime.toEpochSecond(DIFF_ZONE);
        BusinessAssert.isTrue(start < end, "开始时间不能大于结束时间！");
        double diff = end - start;
        if (diff > TIME_RANGE_LIMIT) {
            double num = Math.ceil(diff / TIME_RANGE_LIMIT);
            for (int i = 0; i < num; i++) {
                long startOffset = start + (TIME_RANGE_LIMIT * i);
                long endOffset = i == num - 1 ? end : startOffset + TIME_RANGE_LIMIT;
                ThreadPoolUtil.execute(() -> report(getMessage(startOffset, endOffset)));
            }
        } else {
            report(getMessage(start, end));
        }
    }

    @Override
    public List<TerminalMessage> getMessage(long startTime, long endTime) {
        BusinessAssert.isTrue(startTime < endTime, "开始时间不能大于结束时间！");
        long diff = endTime - startTime;
        BusinessAssert.isTrue(diff <= TIME_RANGE_LIMIT, "时间范围不能大于1小时！");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add(START_TIME, String.valueOf(startTime));
        params.add(END_TIME, String.valueOf(endTime));
        OrbcommResult result = exchange(GET_MESSAGE, params);
        GetMsgStatus status = GetMsgStatus.value(result.getStatus());
        BusinessAssert.notNull(status, "返回状态异常！");
        if (GetMsgStatus.SUCCESS.equals(status)) {
//            log.info("收到OGI设备数据：{}", JsonUtil.toJson(result));
            List<OrbcommMessage> list = JsonUtil.convert(result.getMsg(), new TypeReference<List<OrbcommMessage>>() {
            });
            return list.stream().map(converter()).collect(Collectors.toList());
        } else {
            if (!GetMsgStatus.NO_DATA.equals(status)) {
                log.warn(status.getDesc());
            }
            return BaseUtil.newArrayList();
        }
    }

    @Override
    public OrbcommResult sendMessage(TerminalMessage message) {
        log.info("发送OGI终端数据：{}", JsonUtil.toJson(message));
        BusinessAssert.notNull(message, "终端消息不能为空！");
        String imei = message.getImei();
        BusinessAssert.hasText(imei, "IMEI不能为空！");
        BusinessAssert.isTrue(imei.length() == SatelliteConstant.IMEI_LENGTH, "IMEI的长度有误！");
        byte[] payload = message.getPayload();
        BusinessAssert.isTrue(ArrayUtils.isNotEmpty(payload), "载荷数据不能为空！");
        String msg = Hex.encodeHexString(payload);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add(DEVICE_ID, imei);
        params.add(MSG, msg);
        OrbcommResult result = exchange(SEND_MESSAGE, params);
        SendMsgStatus status = SendMsgStatus.value(result.getStatus());
        BusinessAssert.notNull(status, "返回状态异常！");
        if (SendMsgStatus.SUCCESS.equals(status)) {
            log.info("发送OGI终端数据成功：{}", JsonUtil.toJson(message));
        } else {
            log.warn(status.getDesc());
        }
        return result;
    }

    /**
     * 接口请求
     * 
     * @param url    接口地址
     * @param params 请求参数
     * @return 返回结果
     */
    private OrbcommResult exchange(String url, MultiValueMap<String, String> params) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        params.add(NAME, name);
        params.add(SIGN, sign(name, key));
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params,
                headers);
        String str = HttpUtil.restTemplate().postForObject(api + url, requestEntity, String.class);
        BusinessAssert.hasText(str, "返回结果为空！");
        return JsonUtil.toObject(str, OrbcommResult.class);
    }

    /**
     * 消息转换器
     * 
     * @return 转换器
     */
    private Function<OrbcommMessage, TerminalMessage> converter() {
        return om -> {
            TerminalMessage tm = TerminalMessage.create();
            if (StringUtils.isNumeric(om.getMessageTime())) {
                long messageTime = Long.valueOf(om.getMessageTime());
                LocalDateTime time = LocalDateTime.ofEpochSecond(messageTime, 0, DIFF_ZONE);
                tm.setTimestamp(time);
            }
            tm.setChannel(SatelliteChannel.ORBCOMM.getCode());
            tm.getHeader().put(GATEWAY, tm.getChannel());
            if (VENDOR.equals(om.getMessageTo())) {
                tm.setImei(om.getMessageFrom());
            } else {
                tm.setImei(om.getMessageTo());
            }
            byte[] payload = null;
            String messageBody = om.getMessageBody();
            if (StringUtils.isEmpty(messageBody)) {
                payload = new byte[0];
            } else {
                if (StringUtil.isHex(messageBody)) {
                    try {
                        payload = Hex.decodeHex(om.getMessageBody());
                    } catch (DecoderException e) {

                    }
                } else {
                    payload = messageBody.getBytes();
                }
            }
            tm.setPayload(payload);
            return tm;
        };
    }

    /**
     * 生成签名
     * 
     * @param name 账号
     * @param key  密钥
     * @return 签名
     */
    private String sign(String name, String key) {
        String origin = name + key
                + LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).toEpochSecond(DateUtil.ZONE_E_8);
        return BaseUtil.md5Encode(origin);
    }

    /**
     * 上报消息
     * 
     * @param list 消息列表
     */
    private void report(List<TerminalMessage> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        list.forEach(message -> {
            if (CollectUtil.isFotaMsg(message.getPayload())) {
            } else {
            }
        });
    }

}
