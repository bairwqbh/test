package per.cby.test.aop;

import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import per.cby.terminal.model.Terminal;

/**
 * 上报业务统一处理切面
 * 
 * @author chenboyang
 * @since 2020年2月28日
 *
 */
@Aspect
@Component
public class ReportHandleAspect {

    /** 匹配表达式 */
    private static final String EXPRESSION = "@annotation(per.cby.test.aop.ReportCommonHandle)";

    /**
     * 执行前拦截处理终端消息
     * 
     * @param point    连接点
     * @param logInter 日志信息
     */
    @Before(EXPRESSION)
    public void before(JoinPoint point) {
        Object[] args = point.getArgs();
        if (ArrayUtils.isEmpty(args) || args[0] == null || !Terminal.class.equals(args[0].getClass())) {
            return;
        }
        Terminal terminal = (Terminal) args[0];
        terminal.setId(123L);
    }

}
