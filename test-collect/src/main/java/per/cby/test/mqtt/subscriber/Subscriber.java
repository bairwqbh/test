package per.cby.test.mqtt.subscriber;

import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import per.cby.frame.mqtt.StringMqttSubscriber;
import per.cby.frame.mqtt.annotation.MqttConsumer;

/**
 * MQTT终端数据上报订阅者
 * 
 * @author chenboyang
 * @since 2019年10月29日
 *
 */
@Component
@MqttConsumer(clientId = "test001", topic = "test")
public class Subscriber extends StringMqttSubscriber {

    @Override
    protected void handleMessage(MessageHeaders headers, String payload) {
        System.err.println("Subscriber - " + payload);
    }

}
