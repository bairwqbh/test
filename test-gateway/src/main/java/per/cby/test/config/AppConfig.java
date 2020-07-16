package per.cby.test.config;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import per.cby.frame.common.config.properties.SysProperties;
import per.cby.frame.redis.storage.hash.DefaultRedisHashStorage;
import per.cby.frame.web.exception.JsonExceptionHandler;
import per.cby.frame.web.filter.CorsFilter;
import per.cby.frame.web.session.BizSessionIdGenerator;
import per.cby.frame.web.session.L2CacheSessionManager;
import per.cby.frame.web.session.RedisSessionCache;
import per.cby.frame.web.session.SessionManager;
import per.cby.system.constant.SystemConstants;
import per.cby.system.filter.LogFilter;
import per.cby.system.filter.TokenFilter;
import per.cby.system.model.Api;

/**
 * 应用配置
 * 
 * @author chenboyang
 *
 */
@Configuration
public class AppConfig {

    /**
     * 接口信息缓存
     * 
     * @return 接口信息缓存
     */
    @Bean(SystemConstants.API_CACHE_HASH)
    public DefaultRedisHashStorage<String, Api> apiCacheHash() {
        return new DefaultRedisHashStorage<String, Api>("sys:api:cache:hash", String.class, Api.class);
    }

    /**
     * 统一会话管理器
     * 
     * @param sysProperties 系统属性
     * @return 会话管理器
     */
    @Bean
    public SessionManager sessionManager(SysProperties sysProperties) {
        L2CacheSessionManager manager = new L2CacheSessionManager();
        manager.setGlobalTimeout(sysProperties.getTimeout(), TimeUnit.MINUTES);
        manager.setSessionCache(new RedisSessionCache());
        manager.setSessionIdGenerator(new BizSessionIdGenerator());
        return manager;
    }

    /**
     * JSON异常处理器
     * 
     * @return 异常处理器
     */
    @Bean
    public JsonExceptionHandler jsonExceptionHandler() {
        return new JsonExceptionHandler();
    }

    /**
     * 跨域过滤器
     * 
     * @return 过滤器
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public CorsFilter corsFilter() {
        return new CorsFilter();
    }

    /**
     * token过滤器
     * 
     * @return 过滤器
     */
    @Bean
    @Order(0)
    public TokenFilter tokenFilter() {
        return new TokenFilter();
    }

    /**
     * 日志过滤器
     * 
     * @return 过滤器
     */
    @Bean
    @Order(1)
    public LogFilter logFilter() {
        return new LogFilter();
    }

}
