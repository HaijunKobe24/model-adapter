package cn.unipus.modeladapter.remote.starter.http.ipublish;

import cn.unipus.modeladapter.remote.starter.http.HttpResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * iPublish 接口返回结果
 *
 * @author haijun.gao
 * @date 2025/7/15
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class IPublishResponse<T> extends HttpResponse<T> {

    private Boolean success;

    @Override
    public boolean isSuccess() {
        return Boolean.TRUE.equals(success);
    }
}
