package per.cby.test.mqtt;

import java.io.IOException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import per.cby.frame.common.base.BaseBootTest;
import per.cby.frame.mqtt.MqttPublisherAdapter;

public class Publisher extends BaseBootTest {

    @Autowired
    private MqttPublisherAdapter mqttPublisher;

    @Test
    public void publish() throws IOException {
//        byte[] payload = new byte[] { 0x42, 0x44, 0x0d, 0x04, (byte) 0x89, 0x43, 0x24, 0x23, 0x00, 0x00, 0x00, 0x00,
//                0x00, 0x01, 0x01, 0x18, 0x31 };
//        System.out.println(CodeUtil.byteToHex(payload));
        mqttPublisher.publish("test", 1, "来了");
    }

}
