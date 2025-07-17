package cn.unipus.modeladapter.remote.starter.http.ipublish.dto;

import lombok.Data;

import java.util.List;

/**
 * 章节内的节点数据的传输对象
 *
 * @author haijun.gao
 * @date 2025/7/2
 */
@Data
public class ContentNodeDTO {

    /**
     * 节点下的中日韩文字数量
     */
    private Long cjkWordCount;

    /**
     * 节点下的非中日韩文字数量
     */
    private Long nonCjkWordCount;

    /**
     * 节点下的题型
     */
    private String questionType;

    /**
     * 章节唯一标识符
     */
    private String id;

    /**
     * 章节节点的文本内容
     */
    private String text;

    /**
     * 章节节点的类型
     */
    private String type;

    /**
     * 节点下的文字数量
     */
    private Long wordCount;

    /**
     * 节点下的音频时长（单位：毫秒）
     */
    private Long audioDuration;

    /**
     * 节点下的视频时长（单位：毫秒）
     */
    private Long videoDuration;

    /**
     * 节点下的子节点列表（递归结构）
     */
    private List<ContentNodeDTO> children;
}