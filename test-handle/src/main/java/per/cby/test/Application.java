package per.cby.test;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import per.cby.frame.common.constant.BizConstant;

@EnableDiscoveryClient
@EnableTransactionManagement(proxyTargetClass = true)
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class, scanBasePackages = BizConstant.BASE_PACKAGE)
public class Application {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }

}
