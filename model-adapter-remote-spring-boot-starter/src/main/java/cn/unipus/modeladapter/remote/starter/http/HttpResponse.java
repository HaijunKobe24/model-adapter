package cn.unipus.modeladapter.remote.starter.http;

import cn.unipus.modeladapter.remote.starter.common.constant.CodeEnum;
import lombok.Data;

import java.util.Objects;

/**
 * 标准相应
 *
 * @author haijun.gao
 * @date 2025/7/1
 */
@Data
public class HttpResponse<T> {

    private Integer code;

    private String message;

    private T data;

    public boolean isSuccess() {
        return Objects.equals(code, CodeEnum.SUCCESS.getCode());
    }
}
