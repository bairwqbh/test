package per.cby.test.terminal.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import per.cby.frame.common.base.BaseBootTest;
import per.cby.terminal.model.TerminalCommodu;
import per.cby.terminal.service.TerminalCommoduService;

/**
 * 
 * 
 * @author chenboyang
 * @since 2019年11月13日
 *
 */
public class TerminalCommoduServiceTest extends BaseBootTest {

    @Autowired
    private TerminalCommoduService terminalCommoduService;
 
    @Test
    public void exe() {
        List<TerminalCommodu> list = terminalCommoduService.list();
        System.out.println(list);
    }
    
}
