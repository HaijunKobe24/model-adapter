package cn.unipus.modeladapter.remote.starter.http.ipublish.dto;

import lombok.Data;
import lombok.NonNull;

/**
 * 访问令牌请求
 *
 * @author haijun.gao
 * @date 2025/7/1
 */
@Data(staticConstructor = "of")
public class AccessTokenRequest {
    @NonNull
    private String openId;
    private Integer readerType;
    private String dataPackage = "{}";
    private Long appId;
    private String envPartition;
}
