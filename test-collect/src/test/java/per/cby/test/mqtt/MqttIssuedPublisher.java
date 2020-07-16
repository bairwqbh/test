package per.cby.test.mqtt;
//package per.cby.test.mqtt;
//
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import per.cby.collect.common.constant.CollectConstant;
//import per.cby.collect.common.event.MqttIssuedEvent;
//import per.cby.collect.common.model.TerminalMessage;
//import per.cby.collect.common.service.IssuedService;
//import per.cby.collect.ecsat.constant.EcsatCollectConstant;
//import per.cby.frame.common.base.BaseBootTest;
//import per.cby.frame.common.event.EventPublisher;
//import per.cby.frame.mqtt.MqttPublisherAdapter;
//import per.cby.frame.rabbitmq.exchange.TopicExchange;
//
///**
// * MQTT终端数据下发发布者
// * 
// * @author chenboyang
// * @since 2019年10月29日
// *
// */
//public class MqttIssuedPublisher extends BaseBootTest {
//
//    @Autowired
//    private MqttPublisherAdapter mqttPublisher;
//
//    @Autowired
//    private TopicExchange topicExchange;
//
//    @Autowired
//    private EventPublisher eventPublisher;
//
//    @Autowired
//    private IssuedService issuedService;
//
//    @Test
//    public void publish() {
//        TerminalMessage message = TerminalMessage.create();
//        message.setChannel("123");
//        message.setTerminalId("20191003");
//        message.setPayload(new byte[] { 0x11, 0x22, 0x33 });
//        mqttPublisher.publish(EcsatCollectConstant.REPORT_TOPIC + "test", message);
//        topicExchange.send(CollectConstant.TERMINAL_ISSUED_MQTT, message);
//        eventPublisher.publish(new MqttIssuedEvent(message));
//        issuedService.mqttIssued(message);
//    }
//
//}
