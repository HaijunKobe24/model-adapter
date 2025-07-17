package cn.unipus.modeladapter.base.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 内容状态枚举
 *
 * @author haijun.gao
 * @date 2025/7/2
 */
@Getter
@AllArgsConstructor
public enum ContentStatusEnum {

    /**
     * 草稿
     */
    DRAFT((byte) 0),

    /**
     * 待发布
     */
    WAITING((byte) 1),

    /**
     * 已发布
     */
    PUBLISHED((byte) 2);


    private final Byte status;
}
