package cn.unipus.modeladapter.remote.starter.http.ipublish.dto;

import lombok.Data;

import java.util.List;

/**
 * 自建内容简化信息DTO
 *
 * @author haijun.gao
 * @date 2025/7/2
 */
@Data
public class CustomContentSimpleDTO {

    /**
     * 内容业务ID
     */
    private String bizId;

    /**
     * 内容类型，1：自定义章节/2：自定义段落
     */
    private Integer type;

    /**
     * 内容名称
     */
    private String name;

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
     * 租户ID
     */
    private Long tenantId;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 更新时间
     */
    private Long updateTime;

    /**
     * 整体结构节点列表
     */
    private List<ContentNodeDTO> totalStructNodeList;

    /**
     * 整体结构节点树
     */
    private List<ContentNodeDTO> totalStructNodeTree;

    /**
     * 内容的题
     */
    private List<QuestionGroupDTO> questionList;
}