package cn.unipus.modeladapter.remote.starter.common.exception;

import cn.unipus.modeladapter.remote.starter.common.constant.CodeEnum;
import cn.unipus.modeladapter.remote.starter.http.HttpResponse;
import lombok.Getter;

/**
 * http异常
 *
 * @author haijun.gao
 * @date 2025/7/1
 */
public class HttpException extends RuntimeException {

    @Getter
    private final Integer code;

    public HttpException(CodeEnum codeEnum) {
        super(codeEnum.getMsg());
        this.code = codeEnum.getCode();
    }

    public HttpException(HttpResponse<?> response) {
        super(response.getMessage());
        this.code = response.getCode();
    }
}
