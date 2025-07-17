package cn.unipus.modeladapter.remote.starter.http.ipublish.dto;

import lombok.Data;

/**
 * 题目标签
 *
 * @author haijun.gao
 * @date 2025/7/2
 */
@Data
public class QuestionTagDTO {

    /**
     * 标签id
     */
    private Long id;

    /**
     * 标签名称
     */
    private String name;

    /**
     * 标签父id
     */
    private Long parentId;

    /**
     * 标签类型
     */
    private Integer tagType;
}