package cn.unipus.modeladapter.remote.starter.http.ipublish.config;

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
@ConfigurationProperties(prefix = "http.ipublish")
public class IPublishRemoteProperties extends HttpRemoteProperties {

    private Long appId;

    private Integer readerType;

    private String tokenHeader;

    private Long cacheExpireTime = 3600L * 20;

    private Long cacheSize = 64L;

    private final IPublishUriProperties uri = new IPublishUriProperties();
}
