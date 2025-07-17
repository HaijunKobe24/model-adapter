package cn.unipus.modeladapter.remote.starter.http.ipublish.dto;

import lombok.Data;

import java.util.List;

/**
 * 章节数据的传输对象
 *
 * @author haijun.gao
 * @date 2025/7/3
 */
@Data
public class ChapterStructDTO {

    /**
     * 章节唯一标识符
     */
    private String chapterId;

    /**
     * 章节名称
     */
    private String chapterName;

    /**
     * 版本号
     */
    private String versionNumber;

    /**
     * 在书内的排序
     */
    private Integer number;

    /**
     * 章节内容的节点列表，根节点下的一级节点列表
     */
    private List<ContentNodeDTO> allChapterNodeList;

    /**
     * 章节目录的节点树
     */
    private List<ContentNodeDTO> headerNodeTree;

    /**
     * 章节内容的节点树
     */
    private List<ContentNodeDTO> allNodeTree;

    /**
     * 章节内的题目数据
     */
    private List<QuestionGroupDTO> questionGroupList;
}