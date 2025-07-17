package cn.unipus.modeladapter.remote.starter.http.ipublish.dto;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

/**
 * 复制内容请求
 *
 * @author haijun.gao
 * @date 2025/7/1
 */
@Data(staticConstructor = "of")
public class CopyContentRequest {

    @NonNull
    private List<String> bizIds;

    @NonNull
    private Integer status;
}
