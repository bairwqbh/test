package per.cby.test.service;

import org.junit.Test;

import per.cby.frame.common.base.BaseBootTest;

/**
 * 
 * 
 * @author chenboyang
 * @since 2020年3月23日
 *
 */
public class ServiceTest extends BaseBootTest {

//    @Autowired
    private TestService testService;
    
    @Test
    public void test() {
        System.out.println(testService);
    }

}
