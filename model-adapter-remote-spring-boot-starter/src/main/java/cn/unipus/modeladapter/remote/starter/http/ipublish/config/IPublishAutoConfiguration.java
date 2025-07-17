package cn.unipus.modeladapter.remote.starter.http.ipublish.config;

import cn.unipus.modeladapter.remote.starter.common.utils.HttpUtils;
import cn.unipus.modeladapter.remote.starter.http.ipublish.IPublishTemplate;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.boot.autoconfigure.codec.CodecProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * IPublish自动配置
 *
 * @author haijun.gao
 * @date 2025/7/1
 */
@Configuration
@ConditionalOnProperty(name = "http.ipublish.enabled", havingValue = "true")
@EnableConfigurationProperties(IPublishRemoteProperties.class)
public class IPublishAutoConfiguration {

    @Resource
    private IPublishRemoteProperties properties;

    @Resource
    private CodecProperties codecProperties;

    @Bean
    public IPublishTemplate iPublishTemplate() {
        return new IPublishTemplate(properties,
                HttpUtils.createWebClient(properties.getBaseUrl(), properties.getTimeout(),
                        (int) codecProperties.getMaxInMemorySize().toBytes()), this.getCache());
    }

    /**
     * 获取缓存
     *
     * @return 缓存对象
     */
    private Cache<String, String> getCache() {
        return CacheBuilder.newBuilder().maximumSize(properties.getCacheSize())
                .expireAfterWrite(properties.getCacheExpireTime(), TimeUnit.SECONDS).build();
    }
}
