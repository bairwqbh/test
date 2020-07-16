package per.cby.test.ogi;

import java.io.IOException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import per.cby.collect.orbcomm.client.Igws2Client;
import per.cby.collect.orbcomm.model.igws.IGWSInformationResponse;
import per.cby.frame.common.base.BaseBootTest;
import per.cby.frame.common.util.JsonUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OGITest extends BaseBootTest {

//    private final ZoneOffset DIFF_ZONE = ZoneOffset.ofHours(16);
//
//    private long[][] timeArr = new long[][] { { 1575028800, 1575032400 }, { 1575327600, 1575331200 },
//            { 1575601200, 1575604800 }, { 1575594000, 1575597600 }, { 1575590400, 1575594000 },
//            { 1575615600, 1575619200 }, { 1575622800, 1575626400 }, { 1575637200, 1575640800 },
//            { 1575630000, 1575633600 }, { 1575597600, 1575601200 }, { 1575608400, 1575612000 },
//            { 1575644400, 1575648000 }, { 1575651600, 1575655200 }, { 1575666000, 1575669600 },
//            { 1575694800, 1575698400 }, { 1575658800, 1575662400 }, { 1575673200, 1575676800 },
//            { 1575687600, 1575691200 }, { 1575702000, 1575705600 }, { 1575745200, 1575748800 },
//            { 1575709200, 1575712800 }, { 1575680400, 1575684000 }, { 1575752400, 1575756000 },
//            { 1575730800, 1575734400 }, { 1575723600, 1575727200 }, { 1575716400, 1575720000 },
//            { 1575738000, 1575741600 }, { 1575766800, 1575770400 }, { 1575759600, 1575763200 },
//            { 1575802800, 1575806400 }, { 1575795600, 1575799200 }, { 1575781200, 1575784800 },
//            { 1575810000, 1575813600 }, { 1575774000, 1575777600 }, { 1575788400, 1575792000 },
//            { 1575817200, 1575820800 }, { 1575824400, 1575828000 }, { 1576634400, 1576638000 },
//            { 1577210400, 1577214000 }, { 1578438000, 1578441600 }, { 1578441600, 1578445200 } };

    @Autowired
    private Igws2Client igws2Client;

//    private TianyouTestService tianyouTestService = new TianyouTestService();

    /** 访问账户 */
    private String accessId = "70002729";

    /** 密码 */
    private String password = "CCQCVLSO";

    @Test
    public void test() throws IOException, InterruptedException {

        IGWSInformationResponse response = igws2Client.igwsInformation(accessId, password, true);
        log.debug(JsonUtil.toJson(response));

//        while (true) {
//            ThreadPoolUtil.execute(() -> {
//                try {
//                    LocalDateTime time = LocalDateTime.now().minusHours(8);
//                    GetReturnMessagesResult result = igws2Client.getReturnMessages(accessId, password,
//                            DateUtil.format(time), true, null, null);
//                    log.debug(JsonUtil.toJson(result));
//                } catch (Exception e) {
//                    log.error(e.getMessage(), e);
//                }
//            });
//            Thread.sleep(1000);
//        }
                
//        LocalDateTime time = LocalDate.now().atStartOfDay().minusHours(8);
//        GetReturnMessagesResult result = igws2Client.getReturnMessages(accessId, password, DateUtil.format(time), true,
//                null, null);
//        if (CollectionUtils.isNotEmpty(result.getMessages())) {
//            result.getMessages().stream().filter(t -> ArrayUtils.isNotEmpty(t.getRawPayload())).forEach(t -> {
//                System.err.println(t.getMobileId() + " - " + t.getMessageUtc().plusHours(8) + " - " + Hex.encodeHexString(t.getRawPayload()));
//            });
//        }

//        System.out.println(result);
//        List<TerminalMessage> list = orbcommService.getMessage(time);
//        list.stream().map(TerminalMessage::getPayload).map(ByteBufUtil::hexDump).forEach(System.err::println);
//        LocalDateTime startTime = LocalDateTime.of(2020, 5, 15, 10, 15);
//        LocalDateTime endTime = startTime.plusHours(1);
//        long start = startTime.toEpochSecond(DIFF_ZONE);
//        long end = endTime.toEpochSecond(DIFF_ZONE);
//        
//        for (long[] ls : timeArr) {
//            List<TerminalMessage> list = tianyouTestService.getMessage(ls[0], ls[1]);
//            list.forEach(t -> System.err.println(t.getImei() + " - " + t.getTimestamp() + " - " + Hex.encodeHexString(t.getPayload())));
//        }
    }

}
