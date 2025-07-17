package cn.unipus.modeladapter.consumer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Kafka 消费者配置
 *
 * @author haijun.gao
 * @date 2025/7/2
 */
@Configuration
public class ConsumerConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.kafka.consumer.ipublish-content")
    public ConsumerProperties iPublishContent() {
        return new ConsumerProperties();
    }
}
