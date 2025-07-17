package cn.unipus.modeladapter.remote.starter.http.ipublish.dto;

import lombok.Data;

/**
 * 关键字参数
 *
 * @author haijun.gao
 * @date 2025/7/2
 */
@Data
public class KeywordDTO {

    /**
     * 关键字id
     */
    private String keywordId;

    /**
     * 关键字值
     */
    private String keywordValue;
}