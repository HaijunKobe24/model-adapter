package cn.unipus.modeladapter.base.common.constant;

public interface CourseConstants {

    String CREATED_TYPE = "customized";

    String QUERY_TYPE = "question-block";

    String BOOK_ID_FMT = "%s_%s";

    String SOURCE_COURSE = "UCOURSE";

    /**
     * 是否官方教材
     *
     * @author haijun.gao
     * @date 2025/7/17
     */
    interface OfficialFlag {
        int YES = 1;
        int NO = 0;
    }
}
