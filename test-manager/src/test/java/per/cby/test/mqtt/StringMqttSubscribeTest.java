package per.cby.test.mqtt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import per.cby.frame.common.exception.BusinessAssert;
import per.cby.frame.mqtt.StringMqttSubscriber;
import per.cby.frame.mqtt.annotation.MqttConsumer;
import per.cby.frame.task.scheduler.DistributeTaskScheduler;

@Component
@MqttConsumer(topic = "test/#")
public class StringMqttSubscribeTest extends StringMqttSubscriber {

    /** 业务路径数量 */
    private final int BIZ_PATH_NUM = 2;

    /** 终端编号的路径最后坐标 */
    private final int SN_LAST_INDEX = 2;

    /** 帧计数的路径最后坐标 */
    private final int COUNT_LAST_INDEX = 1;

    @Autowired(required = false)
    protected DistributeTaskScheduler distributeTaskScheduler;

    @Override
    protected void handleMessage(MessageHeaders headers, String payload) {
        String topic = headers.get(MqttHeaders.RECEIVED_TOPIC).toString();
        String[] paths = topic.split("/");
        BusinessAssert.isTrue(paths.length > BIZ_PATH_NUM, "TOPIC格式错误");
        String sn = paths[paths.length - SN_LAST_INDEX];
        String count = paths[paths.length - COUNT_LAST_INDEX];
        String uniqueId = sn + "." + count;
        distributeTaskScheduler.schedule(uniqueId, () -> {
            System.out.println(payload);
        });
    }

}
