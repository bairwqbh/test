package per.cby.test.service;

import org.springframework.context.annotation.Configuration;

/**
 * 
 * 
 * @author chenboyang
 * @since 2020年3月23日
 *
 */
@Configuration
public class ServiceConfigExt {

//    @Bean
//    @ConditionalOnMissingBean
    public TestService testService() {
        return new SimpleTestService();
    }

}
