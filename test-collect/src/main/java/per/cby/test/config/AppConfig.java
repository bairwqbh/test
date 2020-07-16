package per.cby.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import per.cby.collect.ground.common.constant.Network;
import per.cby.collect.ground.common.constant.Protocol;
import per.cby.collect.ground.common.model.NetworkMapper;

/**
 * 应用配置
 * 
 * @author chenboyang
 * @since 2020年2月13日
 *
 */
/**
 * @author chenboyang
 * @since 2020年2月13日
 *
 */
@Configuration
public class AppConfig {

    @Bean
    public NetworkMapper networkMapper() {
        NetworkMapper networkMapper = new NetworkMapper();
        networkMapper.put(Protocol.MQTT, Network.CM_2G);
        return networkMapper;
    }

}
