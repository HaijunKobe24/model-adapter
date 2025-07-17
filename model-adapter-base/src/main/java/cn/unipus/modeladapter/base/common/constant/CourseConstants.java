package cn.unipus.modeladapter.base.common.constant;

public interface CourseConstants {

    String CREATED_TYPE = "customized";

    String QUERY_TYPE = "question-block";

    String BOOK_ID_FMT = "%s_%s";

    /**
     * 是否官方教材
     **/
    interface OFFICIAL_FlAG {
        int YES = 1;
        int NO = 0;
    }
}
