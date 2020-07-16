package per.cby.test.mqtt.publisher;

import org.springframework.beans.factory.annotation.Autowired;

import per.cby.collect.common.model.TerminalMessage;
import per.cby.frame.mqtt.MqttPublisherAdapter;

/**
 * MQTT终端数据下发发布者
 * 
 * @author chenboyang
 * @since 2019年10月29日
 *
 */
public class MqttIssuedPublisher {

    @Autowired(required = false)
    private MqttPublisherAdapter mqttPublisher;

    /**
     * MQTT终端消息发布
     * 
     * @param message 下发消息
     */
    public void publish(TerminalMessage message) {
        mqttPublisher.publish("satellite/download/test", message);
    }

}
