package cn.unipus.modeladapter.client.converter;

import cn.unipus.modeladapter.api.proto.client.model.*;
import cn.unipus.modeladapter.client.dto.*;

/**
 * DTO与Protobuf模型转换器
 *
 * @author Gemini
 * @since 2024-07-22
 */
public class ModelConverter {

    public static StatusDTO toDTO(Status proto) {
        if (proto == null) {
            return null;
        }
        StatusDTO dto = new StatusDTO();
        dto.setCode(proto.getCode());
        dto.setMsg(proto.getMsg());
        return dto;
    }

    public static CreateUnitRequestPO toProto(CreateUnitRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        return CreateUnitRequestPO.newBuilder()
                .setBookId(dto.getBookId())
                .setName(dto.getName())
                .build();
    }

    public static CreateUnitResponseDTO toDTO(CreateUnitResponsePO proto) {
        if (proto == null) {
            return null;
        }
        CreateUnitResponseDTO dto = new CreateUnitResponseDTO();
        dto.setStatus(toDTO(proto.getStatus()));
        if (proto.hasData()) {
            dto.setBookId(proto.getData().getBookId());
            dto.setNewUnitId(proto.getData().getNewUnitId());
        }
        return dto;
    }

    public static PublishCourseRequestPO toProto(PublishCourseRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        return PublishCourseRequestPO.newBuilder()
                .setBookId(dto.getBookId())
                .build();
    }


    public static UpdateUnitNameRequestPO toProto(UpdateUnitNameRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        return UpdateUnitNameRequestPO.newBuilder()
                .setBookId(dto.getBookId())
                .setUnitId(dto.getUnitId())
                .setName(dto.getName())
                .build();
    }

    public static DeleteUnitRequestPO toProto(DeleteUnitRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        return DeleteUnitRequestPO.newBuilder()
                .setBookId(dto.getBookId())
                .setUnitId(dto.getUnitId())
                .build();
    }
}
