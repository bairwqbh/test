package per.cby.test.mqtt.subscriber;

import org.springframework.messaging.MessageHeaders;

import per.cby.collect.common.model.TerminalMessage;
import per.cby.frame.mqtt.GenericMqttSubscriber;
import per.cby.frame.mqtt.annotation.MqttConsumer;

/**
 * MQTT终端数据上报订阅者
 * 
 * @author chenboyang
 * @since 2019年10月29日
 *
 */
@MqttConsumer(clientId = "test003", topic = "satellite/upload/test")
public class MqttReportSubscriber extends GenericMqttSubscriber<TerminalMessage> {

    @Override
    protected void handleMessage(MessageHeaders headers, TerminalMessage payload) {
        System.out.println(payload);
    }

}
