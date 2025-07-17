package cn.unipus.modeladapter.remote.starter.http.ipublish.dto;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

/**
 * 发布自建内容请求参数
 *
 * @author haijun.gao
 * @date 2025/7/2
 */
@Data(staticConstructor = "of")
public class PublishCustomContentRequest {

    /**
     * 要发布的自建内容ID列表
     */
    @NonNull
    private List<String> publishContentIds;

    /**
     * 要删除的自建内容ID列表
     */
    private List<String> deleteContentIds;
}