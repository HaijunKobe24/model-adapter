package cn.unipus.modeladapter.remote.starter.http.ipublish.dto;

import lombok.Data;

/**
 * 关联参数
 *
 * @author haijun.gao
 * @date 2025/7/2
 */
@Data
public class QuestionRelevancyDTO {

    /**
     * 关联id
     */
    private String id;

    /**
     * 关联类型
     */
    private String type;
}