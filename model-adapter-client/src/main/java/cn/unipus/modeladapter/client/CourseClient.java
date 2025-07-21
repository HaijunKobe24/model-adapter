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
 * @since 2024-07-22
 */
@AllArgsConstructor
public class CourseClient {

    private final CourseServiceGrpc.CourseServiceBlockingStub blockingStub;

    /**
     * 新建单元
     *
     * @param requestDTO request DTO
     * @return response DTO
     */
    public CreateUnitResponseDTO createUnit(CreateUnitRequestDTO requestDTO) {
        CreateUnitRequestPO requestProto = ModelConverter.toProto(requestDTO);
        CreateUnitResponsePO responseProto = blockingStub.createUnit(requestProto);
        return ModelConverter.toDTO(responseProto);
    }

    /**
     * 课程发布
     *
     * @param requestDTO request DTO
     * @return StatusDTO
     */
    public StatusDTO publishCourse(PublishCourseRequestDTO requestDTO) {
        PublishCourseRequestPO requestProto = ModelConverter.toProto(requestDTO);
        PublishCourseResponsePO responseProto = blockingStub.publishCourse(requestProto);
        return ModelConverter.toDTO(responseProto.getStatus());
    }

    /**
     * 修改单元名称
     *
     * @param requestDTO request DTO
     * @return StatusDTO
     */
    public StatusDTO updateUnitName(UpdateUnitNameRequestDTO requestDTO) {
        UpdateUnitNameRequestPO requestProto = ModelConverter.toProto(requestDTO);
        UpdateUnitNameResponsePO responseProto = blockingStub.updateUnitName(requestProto);
        return ModelConverter.toDTO(responseProto.getStatus());
    }

    /**
     * 删除单元
     *
     * @param requestDTO request DTO
     * @return StatusDTO
     */
    public StatusDTO deleteUnit(DeleteUnitRequestDTO requestDTO) {
        DeleteUnitRequestPO requestProto = ModelConverter.toProto(requestDTO);
        DeleteUnitResponsePO responseProto = blockingStub.deleteUnit(requestProto);
        return ModelConverter.toDTO(responseProto.getStatus());
    }
}