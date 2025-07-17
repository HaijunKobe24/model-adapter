package cn.unipus.modeladapter.remote.starter.http.ipublish.dto;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

/**
 * 自建内容ID请求参数
 *
 * @author haijun.gao
 * @date 2025/7/2
 */
@Data(staticConstructor = "of")
public class CustomContentIdRequest {

    /**
     * 自建内容业务id列表
     */
    @NonNull
    private List<String> bizIds;
}