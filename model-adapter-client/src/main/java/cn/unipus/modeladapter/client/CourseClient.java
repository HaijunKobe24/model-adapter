package cn.unipus.modeladapter.client;

import cn.unipus.modeladapter.api.proto.client.model.*;
import cn.unipus.modeladapter.api.proto.client.service.CourseServiceGrpc;
import cn.unipus.modeladapter.client.converter.ModelConverter;
import cn.unipus.modeladapter.client.dto.*;
import lombok.AllArgsConstructor;

/**
 * 课程服务客户端
 *
 * @author haijun.gao
 * @date 2024/7/22
 */
@AllArgsConstructor
public class CourseClient {

    private final CourseServiceGrpc.CourseServiceBlockingStub blockingStub;

    /**
     * 新建单元
     *
     * @param requestDTO 新建单元请求参数
     * @return 新建单元响应结果
     */
    public CreateUnitResponseDTO createUnit(CreateUnitRequestDTO requestDTO) {
        CreateUnitRequestPO requestProto = ModelConverter.toProto(requestDTO);
        CreateUnitResponsePO responseProto = blockingStub.createUnit(requestProto);
        return ModelConverter.toDTO(responseProto);
    }

    /**
     * 课程发布
     *
     * @param requestDTO 课程发布请求参数
     * @return 状态响应结果
     */
    public StatusDTO publishCourse(PublishCourseRequestDTO requestDTO) {
        PublishCourseRequestPO requestProto = ModelConverter.toProto(requestDTO);
        PublishCourseResponsePO responseProto = blockingStub.publishCourse(requestProto);
        return ModelConverter.toDTO(responseProto.getStatus());
    }

    /**
     * 修改单元名称
     *
     * @param requestDTO 修改单元名称请求参数
     * @return 状态响应结果
     */
    public StatusDTO updateUnitName(UpdateUnitNameRequestDTO requestDTO) {
        UpdateUnitNameRequestPO requestProto = ModelConverter.toProto(requestDTO);
        UpdateUnitNameResponsePO responseProto = blockingStub.updateUnitName(requestProto);
        return ModelConverter.toDTO(responseProto.getStatus());
    }

    /**
     * 删除单元
     *
     * @param requestDTO 删除单元请求参数
     * @return 状态响应结果
     */
    public StatusDTO deleteUnit(DeleteUnitRequestDTO requestDTO) {
        DeleteUnitRequestPO requestProto = ModelConverter.toProto(requestDTO);
        DeleteUnitResponsePO responseProto = blockingStub.deleteUnit(requestProto);
        return ModelConverter.toDTO(responseProto.getStatus());
    }
}