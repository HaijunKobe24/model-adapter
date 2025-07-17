package cn.unipus.modeladapter.remote.starter.http.course.dto;

import lombok.Data;

import java.util.List;


/**
 * 结构化节点实体类，对应树形结构内容。
 */
@Data
public class BlockStructDTO {
    /**
     * 节点唯一ID
     */
    private String id;
    /**
     * 节点文本内容
     */
    private String text;
    /**
     * 节点类型，如 h1、h2、insert-video、question-block 等
     */
    private String type;
    /**
     * 节点创建类型，如 customized
     */
    private String createdType;
    /**
     * 字数统计
     */
    private Integer wordCount;
    /**
     * 题目ID（仅题目节点有）
     */
    private String questId;
    /**
     * 题目版本（仅题目节点有）
     */
    private String questVersion;
    /**
     * 子题数量（仅题目节点有）
     */
    private Integer subQuestionCount;
    /**
     * 子题分数列表（仅题目节点有）
     */
    private List<Integer> subQuestionScore;
    /**
     * 子题是否计分（仅题目节点有）
     */
    private List<Boolean> isScore;
    /**
     * 子题是否判分（仅题目节点有）
     */
    private List<Boolean> isJudgment;
    /**
     * 题目类型（仅题目节点有）
     */
    private String questionType;
    /**
     * 子节点列表，递归结构
     */
    private List<BlockStructDTO> children;
}