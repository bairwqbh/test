package per.cby.test.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 应用配置
 * 
 * @author chenboyang
 *
 */
@Configuration
public class AppConfig implements WebMvcConfigurer {

//    @Autowired
//    private WebLogService webLogService;

//    @Autowired
//    private SessionManager sessionManager;

    /**
     * 添加拦截器
     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        final String[] swaggerPaths = {
//            "/swagger-ui.html", 
//            "/webjars/springfox-swagger-ui/**",
//            "/swagger-resources/**", 
//            "/v2/api-docs"
//        };
//        registry.addInterceptor(new TokenInterceptor(sessionManager))
//            .addPathPatterns("/**")
//            .excludePathPatterns(
//                "/login",
//                "/druid/**"
//            ).excludePathPatterns(swaggerPaths);
//        registry.addInterceptor(new LogInterceptor(webLogService))
//            .addPathPatterns("/**")
//            .excludePathPatterns(
//                "/login",
//                "/logout",
//                "/modPwd",
//                "/log/save",
//                "/druid/**"
//            ).excludePathPatterns(swaggerPaths);
//    }

}
