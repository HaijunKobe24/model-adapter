package cn.unipus.modeladapter.remote.starter.http.ipublish.dto;

import lombok.Data;

/**
 * 选项DTO
 *
 * @author haijun.gao
 * @date 2025/7/2
 */
@Data
public class QuestionOptionDTO {

    /**
     * 选项名称，如 A、B、C、D
     */
    private String label;

    /**
     * 选项的Id
     */
    private String optionId;

    /**
     * 选项的文本内容
     */
    private String optionValue;
}