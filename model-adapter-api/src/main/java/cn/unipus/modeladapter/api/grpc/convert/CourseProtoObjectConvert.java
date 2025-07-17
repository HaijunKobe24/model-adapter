package cn.unipus.modeladapter.api.grpc.convert;

import cn.unipus.modeladapter.api.proto.client.model.*;
import cn.unipus.modeladapter.api.dto.UnitStatusInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class CourseProtoObjectConvert {

    /**
     * 构建 QueryUnitStatusResponsePO 响应
     */
    public static QueryUnitStatusResponsePO buildQueryUnitStatusResponse(List<UnitStatusInfo> unitStatusList) {
        QueryUnitStatusResponsePO.Builder responseBuilder = QueryUnitStatusResponsePO.newBuilder();
        for (UnitStatusInfo unitStatus : unitStatusList) {
            UnitStatusPO unitStatusPO = UnitStatusPO.newBuilder()
                    .setUnitId(unitStatus.getUnitId())
                    .setStatus(unitStatus.getStatus())
                    .build();
            responseBuilder.addUnitStatusList(unitStatusPO);
        }
        Status successStatus = buildSuccessStatus();
        return responseBuilder.setStatus(successStatus).build();
    }

    /**
     * 构建 QueryUnitStatusResponsePO 响应
     */
    public static PublishCourseResponsePO buildPublishCourseResponse() {
        Status successStatus = buildSuccessStatus();

        return PublishCourseResponsePO.newBuilder().setStatus(successStatus).build();
    }
  /**
     * 获取通用的成功状态
     */
    public static Status buildSuccessStatus() {
        return Status.newBuilder().setCode(200).setMsg("success").build();
    }

    /**
     * 构建复制课程响应对象
     *
     * @param newBookId 新书籍的ID
     * @return 构建完成的复制课程响应对象
     */
    public static CopyCourseResponsePO buildCopyCourseResponse(String newBookId) {
        Status successStatus = buildSuccessStatus();
        CopyCourseDataPO data = CopyCourseDataPO.newBuilder()
                .setNewBookId(newBookId).build();
        return CopyCourseResponsePO.newBuilder().setStatus(successStatus).setData(data).build();
    }

    /**
     * 构建创建单元响应对象
     *
     * @param bookId     书籍ID
     * @param newUnitId  新单元ID
     * @return 构建创建单元响应对象
     */
    public static CreateUnitResponsePO buildCreateUnitResponse(String bookId, String newUnitId) {
        Status successStatus = buildSuccessStatus();
        CreateUnitDataPO data = CreateUnitDataPO.newBuilder()
                .setBookId(bookId)
                .setNewUnitId(newUnitId).build();
        return CreateUnitResponsePO.newBuilder().setStatus(successStatus).setData(data).build();
    }

    /**
     * 构建复制课程响应对象（DTO版本）
     * @param dataDTO 复制课程数据DTO
     * @return 构建完成的复制课程响应对象
     */
    public static CopyCourseResponsePO buildCopyCourseResponse(CopyCourseDataPO dataDTO) {
        Status successStatus = buildSuccessStatus();
        return CopyCourseResponsePO.newBuilder().setStatus(successStatus).setData(dataDTO).build();
    }

    /**
     * 构建删除单元响应对象
     * @return DeleteUnitResponsePO
     */
    public static DeleteUnitResponsePO buildDeleteUnitResponse() {
        Status successStatus = buildSuccessStatus();
        return DeleteUnitResponsePO.newBuilder().setStatus(successStatus).build();
    }

    /**
     * 构建修改单元名称响应对象
     * @return UpdateUnitNameResponsePO
     */
    public static UpdateUnitNameResponsePO buildUpdateUnitNameResponse() {
        Status successStatus = buildSuccessStatus();
        return UpdateUnitNameResponsePO.newBuilder().setStatus(successStatus).build();
    }

    /**
     * 根据 CourseStructDTO 列表构建 CustomCourseStructResponsePO（使用 MapStruct 转换）
     * @param courseStructList List<CourseStructDTO>
     * @return CustomCourseStructResponsePO
     */
    public static CustomCourseStructResponsePO buildCustomCourseStructResponsePO(
            List<CourseStructPO> courseStructList) {
        CustomCourseStructDataPO data = CustomCourseStructDataPO.newBuilder()
                .addAllCourseStruct(courseStructList)
                .build();
        return CustomCourseStructResponsePO.newBuilder()
                .setStatus(buildSuccessStatus())
                .setData(data)
                .build();
    }
}
