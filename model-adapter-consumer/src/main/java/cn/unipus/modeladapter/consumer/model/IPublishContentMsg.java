package cn.unipus.modeladapter.consumer.model;

import lombok.Data;

/**
 * iPublish 内容变更消息
 *
 * @author haijun.gao
 * @date 2025/7/2
 */
@Data
public class IPublishContentMsg {
    private String bizId;
    private String dataPackage;
    private String event;

    /**
     * 1：章节，2：段落
     */
    private Integer type;

    /**
     * 0：编辑中，1：待发布，2：已发布
     */
    private Integer status;
}
