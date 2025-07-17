package cn.unipus.modeladapter.remote.starter.http.course.dto;

import lombok.Data;
import lombok.NonNull;

/**
 * 结构同步请求
 *
 * @author haijun.gao
 * @date 2025/7/1
 */
@Data(staticConstructor = "of")
public class StructSyncRequest {

    @NonNull
    private String struct;

    @NonNull
    private String bookId;
}
