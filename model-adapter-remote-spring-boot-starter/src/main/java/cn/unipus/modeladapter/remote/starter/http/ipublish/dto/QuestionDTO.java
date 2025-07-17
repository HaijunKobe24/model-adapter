package cn.unipus.modeladapter.remote.starter.http.ipublish.dto;

import lombok.Data;

import java.util.List;

/**
 * 题目信息
 *
 * @author haijun.gao
 * @date 2025/7/2
 */
@Data
public class QuestionDTO {

    /**
     * 题目ID
     */
    private String id;

    /**
     * 子题目ID
     */
    private String childId;

    /**
     * 题型
     */
    private String questionType;

    /**
     * 小题解析
     */
    private String analysis;

    /**
     * 题干
     */
    private String quesText;

    /**
     * 文本题干
     */
    private String quesTextString;

    /**
     * 是否计分
     */
    private Boolean isScoring;

    /**
     * 是否自动判题
     */
    private Boolean isJudgment;

    /**
     * 题目分数
     */
    private Double score;

    /**
     * 难度
     */
    private Integer difficulty;

    /**
     * 角色
     */
    private String role;

    /**
     * 媒体
     */
    private String media;

    /**
     * 音标
     */
    private String phoneticSymbol;

    /**
     * 答题间隔时间
     */
    private Integer answerTime;

    /**
     * 语音准备时间
     */
    private Integer prepareTime;

    /**
     * 答题字数限制
     */
    private Integer answerWordLimit;

    /**
     * 关键字
     */
    private List<KeywordDTO> keywords;

    /**
     * 关联参数
     */
    private List<QuestionRelevancyDTO> relevancyList;

    /**
     * 选项
     */
    private List<QuestionOptionDTO> options;

    /**
     * 答案
     */
    private List<QuestionAnswerDTO> answers;

    /**
     * 子题
     */
    private List<QuestionDTO> children;

    /**
     * 标签
     */
    private List<List<QuestionTagDTO>> tagList;
}