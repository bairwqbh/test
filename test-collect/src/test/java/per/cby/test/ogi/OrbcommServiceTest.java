package per.cby.test.ogi;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.junit.Test;

import per.cby.frame.common.base.BaseBootTest;
import per.cby.frame.common.util.DateUtil;
import per.cby.frame.common.util.ThreadPoolUtil;

@SuppressWarnings("unused")
public class OrbcommServiceTest extends BaseBootTest {

//    @Autowired
//    private OrbcommService orbcommService;

    private long[][] timeArr = new long[][] { { 1575028800, 1575032400 }, { 1575327600, 1575331200 },
            { 1575601200, 1575604800 }, { 1575594000, 1575597600 }, { 1575590400, 1575594000 },
            { 1575615600, 1575619200 }, { 1575622800, 1575626400 }, { 1575637200, 1575640800 },
            { 1575630000, 1575633600 }, { 1575597600, 1575601200 }, { 1575608400, 1575612000 },
            { 1575644400, 1575648000 }, { 1575651600, 1575655200 }, { 1575666000, 1575669600 },
            { 1575694800, 1575698400 }, { 1575658800, 1575662400 }, { 1575673200, 1575676800 },
            { 1575687600, 1575691200 }, { 1575702000, 1575705600 }, { 1575745200, 1575748800 },
            { 1575709200, 1575712800 }, { 1575680400, 1575684000 }, { 1575752400, 1575756000 },
            { 1575730800, 1575734400 }, { 1575723600, 1575727200 }, { 1575716400, 1575720000 },
            { 1575738000, 1575741600 }, { 1575766800, 1575770400 }, { 1575759600, 1575763200 },
            { 1575802800, 1575806400 }, { 1575795600, 1575799200 }, { 1575781200, 1575784800 },
            { 1575810000, 1575813600 }, { 1575774000, 1575777600 }, { 1575788400, 1575792000 },
            { 1575817200, 1575820800 }, { 1575824400, 1575828000 }, { 1576634400, 1576638000 },
            { 1577210400, 1577214000 }, { 1578438000, 1578441600 }, { 1578441600, 1578445200 } };

    @Test
    public void test() throws IOException {
        LocalDateTime now = LocalDateTime.of(2020, 3, 16, 17, 0);
        long s = now.toEpochSecond(ZoneOffset.of("+16"));
//      long e = now.toEpochSecond(ZoneOffset.of("+16"));
//      List<TerminalMessage> list = orbcommService.getMessage(s, e);
//        long s = 1584318719;
//        long e = now.toEpochSecond(DateUtil.ZONE_E_8);

        long[][] arr = new long[24][2];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = new long[] { s, s += 3600 };
        }

        for (int i = 0; i < arr.length; i++) {
            int num = i;
            ThreadPoolUtil.execute(() -> {
//                List<TerminalMessage> list = orbcommService.getMessage(arr[num][0], arr[num][1]);
//                System.err.println(JsonUtil.toJson(list));
            });
        }
        System.in.read();

//        ZoneOffset zo = ZoneOffset.UTC;
//        ZoneOffset zo8 = DateUtil.ZONE_E_8;
//        ZoneOffset zoneOffset = zo;
//        LocalDateTime now = LocalDateTime.now();
//        long start = now.minusHours(1).toEpochSecond(zoneOffset);
//        long end = now.toEpochSecond(zoneOffset);
//        List<TerminalMessage> list = orbcommService.getMessage(1575028800, 1575032400);
//        System.out.println(list);
    }

}
