package cn.unipus.modeladapter.consumer.config;

import lombok.Data;

/**
 * 消费者配置
 *
 * @author haijun.gao
 * @date 2025/7/17
 */
@Data
public class ConsumerProperties {

    /**
     * 主题
     */
    private String topic;
    /**
     * 消费组
     */
    private String group;
    /**
     * 消费线程数/消费组
     */
    private int concurrency;
    /**
     * 批量消费的记录数
     */
    private int maxRecords;
    /**
     * 消费失败重试次数，默认3次
     */
    private int retryCount;
    /**
     * 消费失败重试延时（毫秒），默认1000ms
     */
    private int retryDelay;
}
