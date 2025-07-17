package cn.unipus.modeladapter.remote.starter.http.ipublish.config;

import lombok.Data;

/**
 * iPublish服务 uri配置
 *
 * @author haijun.gao
 * @date 2025/7/4
 */
@Data
public class IPublishUriProperties {

    private String copyContentUri;

    private String accessTokenUri;

    private String getPublishedContentUri;

    private String getEditingContentUri;

    private String publishContentUri;

    private String saveContentUri;

    private String getBookStructUri;

    private String deleteContentUri;

    /**
     * 获取教材结构URI
     *
     * @param bookId 教材ID
     * @return 教材结构URI
     */
    public String getGetBookStructUri(String bookId) {
        return String.format(getBookStructUri, bookId);
    }
}

