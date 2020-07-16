package per.cby.test;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

import per.cby.frame.common.constant.BizConstant;

@EnableAsync
@EnableFeignClients(BizConstant.BASE_PACKAGE)
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class, scanBasePackages = BizConstant.BASE_PACKAGE)
public class Application {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }

}
