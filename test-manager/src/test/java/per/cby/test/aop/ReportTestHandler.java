package per.cby.test.aop;

import java.util.function.Consumer;

import org.springframework.stereotype.Component;

import per.cby.terminal.model.Terminal;

/**
 * 上报业务统一处理切面
 * 
 * @author chenboyang
 * @since 2020年2月28日
 *
 */
@Component
public class ReportTestHandler implements Consumer<Terminal> {

    @Override
    @ReportCommonHandle
    public void accept(Terminal t) {
        System.out.println(t);
    }

}
