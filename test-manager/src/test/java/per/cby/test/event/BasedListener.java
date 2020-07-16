package per.cby.test.event;

/**
 * 
 * 
 * @author chenboyang
 * @since 2020年2月28日
 *
 */
public interface BasedListener<E extends BaseEvent> extends ConsumerListener<String, E> {

}
