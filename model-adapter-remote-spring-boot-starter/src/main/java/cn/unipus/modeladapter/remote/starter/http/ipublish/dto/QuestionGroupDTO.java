package cn.unipus.modeladapter.remote.starter.http.ipublish.dto;

import lombok.Data;

import java.util.List;

/**
 * 题组信息
 *
 * @author haijun.gao
 * @date 2025/7/2
 */
@Data
public class QuestionGroupDTO {

    /**
     * 题组ID
     */
    private String groupId;

    /**
     * 题组版本
     */
    private String versionNumber;

    /**
     * 题组类型，例如：single_choice_group
     */
    private String groupType;

    /**
     * 题组说明信息
     */
    private String direction;

    /**
     * 题组内容列表
     */
    private String material;

    /**
     * 难度星级 1-5
     */
    private Integer groupDifficulty;

    /**
     * 题目列表
     */
    private List<QuestionDTO> list;

    /**
     * 题组答案解析
     */
    private String analysis;

    /**
     * 题组设置信息，如答题方式、计分等
     */
    private QuestionSettingDTO groupSetting;

    /**
     * 题组总分
     */
    private Double score;
}