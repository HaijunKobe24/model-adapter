package cn.unipus.modeladapter.base.common.constant;

/**
 * 单元状态
 */
public enum UnitStatus {
    PARTIAL("0", "部分发布"),
    UNPUBLISHED("1", "未发布"),
    PUBLISHED("2", "已发布");

    private final String code;
    private final String desc;

    UnitStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() { return code; }
    public String getDesc() { return desc; }
}