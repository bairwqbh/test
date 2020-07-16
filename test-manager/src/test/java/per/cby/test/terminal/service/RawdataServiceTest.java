package per.cby.test.terminal.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import per.cby.frame.common.base.BaseBootTest;
import per.cby.frame.common.util.BaseUtil;
import per.cby.frame.common.util.DateUtil;
import per.cby.frame.common.util.IDUtil;
import per.cby.terminal.constant.TransportType;
import per.cby.terminal.model.Rawdata;
import per.cby.terminal.service.RawdataService;

/**
 * 终端上报数据服务测试
 * 
 * @author chenboyang
 * @since 2019年11月8日
 *
 */
public class RawdataServiceTest extends BaseBootTest {

    @Autowired
    private RawdataService rawdataService;

    @Test
    public void exe() {
        LocalDateTime now = LocalDateTime.now();
        Map<String, Object> map = BaseUtil.newHashMap();
        map.put("startTime", DateUtil.format(now.minusDays(1)));
        map.put("endTime", DateUtil.format(now.plusDays(1)));
//        List<Rawdata> list = rawdataService.list(map);
//        List<Rawdata> list = rawdataService.list(null, null, null);
//        System.out.println(list);
        addList();
    }

    public void addList() {
        byte[] payload = new byte[] { 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, 0x1a, 0x1b, 0x1c, 0x1d,
                0x1e, 0x1f };
        LocalDateTime now = LocalDateTime.now();
        String sn = IDUtil.createUniqueTimeId();
        String imei = IDUtil.createUniqueTimeId();
        List<Rawdata> list = IntStream.range(0, 10).mapToObj(i -> {
            Rawdata rawdata = new Rawdata();
            rawdata.setMessageId(IDUtil.createUniqueTimeId());
            rawdata.setTerminalId(sn);
            rawdata.setImei(imei);
            rawdata.setChannelId("2g");
            rawdata.setTransportType(TransportType.REPORT.getCode());
            rawdata.setPayload(payload);
            rawdata.setDataLength(payload.length);
            rawdata.setTimestamp(now);
            return rawdata;
        }).collect(Collectors.toList());
        rawdataService.saveAll(list);
    }
    
}
