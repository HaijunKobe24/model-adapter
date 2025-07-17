package cn.unipus.modeladapter.remote.starter.http.course.config;

import cn.unipus.modeladapter.remote.starter.common.utils.HttpUtils;
import cn.unipus.modeladapter.remote.starter.http.course.CourseTemplate;
import org.springframework.boot.autoconfigure.codec.CodecProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * 课程自动配置
 *
 * @author haijun.gao
 * @date 2025/7/1
 */
@Configuration
@ConditionalOnProperty(name = "http.course.enabled", havingValue = "true")
@EnableConfigurationProperties(CourseRemoteProperties.class)
public class CourseAutoConfiguration {

    @Resource
    private CourseRemoteProperties properties;

    @Resource
    private CodecProperties codecProperties;

    @Bean
    public CourseTemplate courseTemplate() {
        return new CourseTemplate(properties,
                HttpUtils.createWebClient(properties.getBaseUrl(), properties.getTimeout(),
                        (int) codecProperties.getMaxInMemorySize().toBytes()));
    }
}
