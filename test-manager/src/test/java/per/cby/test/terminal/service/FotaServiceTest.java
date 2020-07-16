package per.cby.test.terminal.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import per.cby.frame.common.base.BaseBootTest;
import per.cby.terminal.model.Fota;
import per.cby.terminal.service.FotaService;

/**
 * 
 * 
 * @author chenboyang
 * @since 2019年11月14日
 *
 */
public class FotaServiceTest extends BaseBootTest {

    @Autowired
    private FotaService fotaService;

    @Test
    public void test() {
        Fota fota = new Fota();
        fotaService.save(fota);
    }

}
