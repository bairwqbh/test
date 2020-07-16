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
public class DataListener implements BasedListener<DataEvent> {

    @Override
    public void accept(String t) {
        System.out.println(t);
    }

    @Override
    public void onApplicationEvent(DataEvent event) {
        System.out.println(event.getSource());
    }

}
