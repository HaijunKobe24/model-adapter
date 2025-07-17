package cn.unipus.modeladapter.remote.starter.http.course.dto;

import lombok.Data;

import java.util.List;


/**
 * 结构化节点实体类，对应树形结构内容。
 */
@Data
public class UnitStructDTO {
    /**
     * 节点唯一ID
     */
    private String id;
    /**
     * 节点文本内容
     */
    private String text;
    /**
     * 单元排序（章节序号）
     */
    private Integer number;
    /**
     * 节点类型，如 h1、h2、insert-video、question-block 等
     */
    private String type;
    /**
     * 节点创建类型，如 customized
     */
    private String createdType;

    private List<BlockStructDTO> children;
}