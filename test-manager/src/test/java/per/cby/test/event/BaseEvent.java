package per.cby.test.event;

import per.cby.frame.common.event.AbstractEvent;

/**
 * 
 * 
 * @author chenboyang
 * @since 2020年2月28日
 *
 */
public abstract class BaseEvent extends AbstractEvent<String> {

    private static final long serialVersionUID = 1L;

    public BaseEvent(String t) {
        super(t);
    }

    @Override
    public String getSource() {
        return (String) super.getSource();
    }

}
