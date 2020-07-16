package per.cby.test.event;

import java.util.function.Consumer;

import org.springframework.context.ApplicationListener;

import per.cby.frame.common.event.AbstractEvent;

/**
 * 
 * 
 * @author chenboyang
 * @since 2020年2月28日
 *
 */
public interface ConsumerListener<T, E extends AbstractEvent<T>> extends Consumer<T>, ApplicationListener<E> {

}
