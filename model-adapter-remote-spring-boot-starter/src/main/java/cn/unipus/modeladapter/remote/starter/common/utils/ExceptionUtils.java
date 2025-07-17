package cn.unipus.modeladapter.remote.starter.common.utils;

import cn.unipus.modeladapter.remote.starter.common.constant.CodeEnum;
import cn.unipus.modeladapter.remote.starter.common.exception.HttpException;
import cn.unipus.modeladapter.remote.starter.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 异常处理工具类
 *
 * @author haijun.gao
 * @date 2025/7/1
 */
@Slf4j
public class ExceptionUtils {

    /**
     * 检查并抛出异常
     *
     * @param response  响应对象
     * @param validData 数据是否校验
     */
    public static void checkAndThrow(HttpResponse<?> response, boolean validData) {
        if (response == null) {
            log.error("HttpResponse is null");
            throw new HttpException(CodeEnum.SERVER_ERROR);
        }
        if (!response.isSuccess()) {
            log.error("HttpResponse is not success, response: {}", response);
            throw new HttpException(response);
        }
        if (validData && response.getData() == null) {
            log.error("HttpResponse data is null, response: {}", response);
            throw new HttpException(CodeEnum.SERVER_ERROR);
        }
    }
}
