package cn.unipus.modeladapter.remote.starter.http.ipublish.dto;

import lombok.Data;

/**
 * 题目设置DTO
 *
 * @author haijun.gao
 * @date 2025/7/2
 */
@Data
public class QuestionSettingDTO {

    /**
     * 答题方式，如拖拽、点选
     */
    private String answerType;

    /**
     * pc端布局类型 瀑布|分页|左右
     */
    private String pcLayoutType;

    /**
     * app端布局类型 瀑布|分页
     */
    private String appLayoutType;

    /**
     * 答题角色，如学生、老师
     */
    private String answerRole;

    /**
     * 听原音 可选 'before' | 'after'| null
     */
    private String answerTiming;

    /**
     * 作答量级
     */
    private String answerLevel;

    /**
     * 音频设置信息
     */
    private AudioSetting audioSetting;

    /**
     * 视频设置信息
     */
    private VideoSetting videoSetting;
}