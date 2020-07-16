package per.cby.test.aop;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import per.cby.frame.common.base.BaseBootTest;
import per.cby.terminal.model.Terminal;

/**
 * 
 * 
 * @author chenboyang
 * @since 2020年2月28日
 *
 */
public class AopTest extends BaseBootTest {

    @Autowired
    private ReportTestHandler reportTestHandler;
    
    @Test
    public void test() {
        reportTestHandler.accept(new Terminal());
    }

}
