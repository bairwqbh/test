package per.cby.test.redis;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import per.cby.frame.common.base.BaseBootTest;
import per.cby.terminal.redis.FotaDataShardList;

/**
 * FOTA升级数据分片列表存储测试
 * 
 * @author chenboyang
 * @since 2019年11月29日
 *
 */
public class FotaDataShardListTest extends BaseBootTest {

    @Autowired
    private FotaDataShardList fotaDataShardList;

    @Test
    public void exe() {
//        fotaDataShardList.rightPush("def", new byte[] { 0x01, 0x02, 0x03 });
//        fotaDataShardList.rightPush("def", new byte[] { 0x04, 0x05, 0x06 });
//        fotaDataShardList.rightPush("def", new byte[] { 0x07, 0x08, 0x09 });
        byte[] data = fotaDataShardList.index("def", 0);
        fotaDataShardList.clear("abc");
        System.out.println(data);
    }

}
