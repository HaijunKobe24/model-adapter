package cn.unipus.modeladapter.consumer.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * kafka消费者配置
 *
 * @author haijun.gao
 * @date 2025/7/2
 */
@Data
@ConfigurationProperties(prefix = "kafka.consumer")
public class KafkaConsumerProperties {

    private int sessionTimeout;
    private int pollTimeout;
    private String autoOffsetReset;
    private boolean enableAutoCommit;
    private int autoCommitInterval;
}
