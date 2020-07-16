package per.cby.test.event;

import org.springframework.stereotype.Component;

/**
 * 
 * 
 * @author chenboyang
 * @since 2020年2月28日
 *
 */
@Component
public class MsgListener implements BasedListener<MsgEvent> {

    @Override
    public void accept(String t) {
        System.out.println(t);
    }

    @Override
    public void onApplicationEvent(MsgEvent event) {
        System.out.println(event.getSource());
    }

}
