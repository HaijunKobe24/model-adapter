package cn.unipus.modeladapter.remote.starter.http.ipublish.dto;

import lombok.Data;

import java.util.List;

/**
 * 教材结构DTO
 *
 * @author haijun.gao
 * @date 2025/7/3
 */
@Data
public class BookStructDTO {
    
    /**
     * 教材唯一标识符
     */
    private String bookId;
    
    /**
     * 版本号
     */
    private String versionNumber;
    
    /**
     * 显示版本号
     */
    private String showVersionNumber;
    
    /**
     * 版本ID
     */
    private Long versionId;
    
    /**
     * 书名
     */
    private String bookName;
    
    /**
     * 基本信息
     */
    private Object bookBasicInfo;
    
    /**
     * 版权信息
     */
    private Object copyright;
    
    /**
     * 简介
     */
    private Object intro;
    
    /**
     * 配套资源列表
     */
    private Object complementResourceList;
    
    /**
     * 试卷同步信息列表
     */
    private Object paperList;
    
    /**
     * 章节列表
     */
    private List<ChapterStructDTO> chapterList;
}