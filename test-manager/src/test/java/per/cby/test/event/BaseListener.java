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
public class BaseListener implements BasedListener<BaseEvent> {

    @Override
    public void accept(String t) {
        System.out.println(t);
    }

    @Override
    public void onApplicationEvent(BaseEvent event) {
        System.out.println(event.getSource());
    }

}
