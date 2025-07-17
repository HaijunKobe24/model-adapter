package cn.unipus.modeladapter.remote.starter.http.ipublish.dto;

import lombok.Data;

/**
 * 题目答案 DTO
 *
 * @author haijun.gao
 * @date 2025/7/2
 */
@Data
public class QuestionAnswerDTO {

    /**
     * 答案的文本内容
     */
    private String answerValue;

    /**
     * 答案的id
     */
    private String answerId;
}