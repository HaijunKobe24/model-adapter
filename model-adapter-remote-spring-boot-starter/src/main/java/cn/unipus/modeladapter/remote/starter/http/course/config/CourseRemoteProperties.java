package cn.unipus.modeladapter.remote.starter.http.course.config;

import cn.unipus.modeladapter.remote.starter.http.HttpRemoteProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * iPublish远程配置
 *
 * @author haijun.gao
 * @date 2025/7/1
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ConfigurationProperties(prefix = "http.course")
public class CourseRemoteProperties extends HttpRemoteProperties {
    private String structSyncUri;
    private TokenProperties token;
}
