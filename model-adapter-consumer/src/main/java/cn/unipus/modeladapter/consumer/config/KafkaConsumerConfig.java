package cn.unipus.modeladapter.consumer.config;

import cn.unipus.qs.common.config.mq.kafka.KafkaMessageConsumerConfig;
import cn.unipus.qs.common.mq.dto.MessageConsumerConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * Kafka 消费者配置
 *
 * @author haijun.gao
 * @date 2025/7/2
 */
@Configuration
@ConditionalOnExpression("${mq.consumer.enabled:true} && '${mq.provider}'.equals('kafka')")
@EnableConfigurationProperties(KafkaConsumerProperties.class)
public class KafkaConsumerConfig {

    @Resource
    private KafkaConsumerProperties properties;

    @Bean
    @ConfigurationProperties(prefix = "kafka.consumer.ipublish-content")
    public MessageConsumerConfig iPublishContent() {
        return this.buildMessageConsumerConfig();
    }

    /**
     * 构建 Kafka 消费者配置
     *
     * @return 消费者配置
     */
    private MessageConsumerConfig buildMessageConsumerConfig() {
        KafkaMessageConsumerConfig kafkaMessageConsumerConfig = new KafkaMessageConsumerConfig();
        kafkaMessageConsumerConfig.setSessionTimeout(properties.getSessionTimeout());
        kafkaMessageConsumerConfig.setPollTimeout(properties.getPollTimeout());
        kafkaMessageConsumerConfig.setAutoOffsetReset(properties.getAutoOffsetReset());
        return kafkaMessageConsumerConfig;
    }
}
