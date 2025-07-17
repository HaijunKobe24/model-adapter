package cn.unipus.modeladapter.api.grpc.convert;

import cn.unipus.modeladapter.api.proto.client.model.*;
import cn.unipus.modeladapter.remote.starter.common.constant.CodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 课程协议对象转换类
 *
 * @author haijun.gao
 * @date 2025/7/17
 */
@Slf4j
@Component
public class CourseProtoObjectConvert {

    /**
     * 构建发布课程响应对象
     *
     * @return 发布课程响应对象
     */
    public static PublishCourseResponsePO buildPublishCourseResponse() {
        Status successStatus = buildSuccessStatus();
        return PublishCourseResponsePO.newBuilder().setStatus(successStatus).build();
    }

    /**
     * 构建创建单元响应对象
     *
     * @param bookId    书籍ID
     * @param newUnitId 新单元ID
     * @return 构建创建单元响应对象
     */
    public static CreateUnitResponsePO buildCreateUnitResponse(String bookId, String newUnitId) {
        Status successStatus = buildSuccessStatus();
        CreateUnitDataPO data = CreateUnitDataPO.newBuilder().setBookId(bookId)
                .setNewUnitId(newUnitId).build();
        return CreateUnitResponsePO.newBuilder().setStatus(successStatus).setData(data).build();
    }

    /**
     * 构建删除单元响应对象
     *
     * @return 单元删除响应
     */
    public static DeleteUnitResponsePO buildDeleteUnitResponse() {
        Status successStatus = buildSuccessStatus();
        return DeleteUnitResponsePO.newBuilder().setStatus(successStatus).build();
    }

    /**
     * 构建修改单元名称响应对象
     *
     * @return 修改单元名称响应对象
     */
    public static UpdateUnitNameResponsePO buildUpdateUnitNameResponse() {
        Status successStatus = buildSuccessStatus();
        return UpdateUnitNameResponsePO.newBuilder().setStatus(successStatus).build();
    }

    /**
     * 获取通用的成功状态
     */
    private static Status buildSuccessStatus() {
        return Status.newBuilder().setCode(CodeEnum.SUCCESS.getCode())
                .setMsg(CodeEnum.SUCCESS.getMsg()).build();
    }
}
