package cn.unipus.modeladapter.base.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 内容类型枚举
 *
 * @author haijun.gao
 * @date 2025/7/2
 */
@Getter
@AllArgsConstructor
public enum ContentTypeEnum {

    /**
     * 章节
     */
    CHAPTER(1),

    /**
     * 段落
     */
    PARAGRAPH(2);


    private final int type;
}
