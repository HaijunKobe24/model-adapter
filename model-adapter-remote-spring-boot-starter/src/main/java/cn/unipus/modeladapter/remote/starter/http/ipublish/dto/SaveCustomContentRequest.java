package cn.unipus.modeladapter.remote.starter.http.ipublish.dto;

import lombok.Data;

import java.util.List;

/**
 * 保存自建内容请求参数
 *
 * @author haijun.gao
 * @date 2025/7/2
 */
@Data
public class SaveCustomContentRequest {

    /**
     * 内容业务ID
     */
    private String bizId;

    /**
     * 内容类型：1-自定义章节，2-自定义段落
     */
    private Integer type;

    /**
     * 内容名称
     */
    private String name;

    /**
     * 内容
     */
    private String content;

    /**
     * 学生内容
     */
    private String studentContent;

    /**
     * 头图地址
     */
    private String headerImg;

    /**
     * 目录结构
     */
    private String catalog;

    /**
     * 资源信息
     */
    private String resource;

    /**
     * 整体结构节点列表
     */
    private List<ContentNodeDTO> totalStructNodeList;

    /**
     * 内容的题
     */
    private List<QuestionGroupDTO> questionList;
}