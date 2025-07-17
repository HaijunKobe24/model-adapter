package cn.unipus.modeladapter.base.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 编码枚举
 *
 * @author haijun.gao
 * @date 2025/7/15
 */
@Getter
@AllArgsConstructor
public enum CodeEnum {

    SUCCESS(1, "success"),

    PARAM_ERROR(199, "param check error"),

    SERVER_ERROR(500, "server error");

    private final Integer code;

    private final String msg;
}
