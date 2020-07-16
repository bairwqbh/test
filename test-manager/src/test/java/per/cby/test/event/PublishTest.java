package per.cby.test.event;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import per.cby.frame.common.base.BaseBootTest;
import per.cby.frame.common.event.EventPublisher;

/**
 * 
 * 
 * @author chenboyang
 * @since 2020年2月28日
 *
 */
public class PublishTest extends BaseBootTest {

    @Autowired
    protected EventPublisher eventPublisher;

    @Test
    public void test() {
        eventPublisher.publish(new DataEvent("123"));
        eventPublisher.publish(new MsgEvent("abc"));
        System.out.println();
    }

}
