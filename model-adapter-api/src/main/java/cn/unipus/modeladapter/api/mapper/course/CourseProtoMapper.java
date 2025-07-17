package cn.unipus.modeladapter.api.mapper.course;

import cn.unipus.modeladapter.api.proto.client.model.*;
import cn.unipus.modeladapter.api.dto.CopyCourseDataDTO;
import cn.unipus.modeladapter.api.dto.UnitMappingDTO;
import cn.unipus.modeladapter.remote.starter.http.course.dto.BlockStructDTO;
import cn.unipus.modeladapter.remote.starter.http.course.dto.CourseStructDTO;
import cn.unipus.modeladapter.remote.starter.http.course.dto.UnitStructDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CourseProtoMapper {
    CourseProtoMapper INSTANCE = Mappers.getMapper(CourseProtoMapper.class);

    @Mapping(source = "unitMappings", target = "unitMappingsList")
    CopyCourseDataPO copyCourseDataDTOToPO(CopyCourseDataDTO dto);

    UnitMappingPO unitMappingDTOToPO(UnitMappingDTO dto);

    List<UnitMappingPO> unitMappingDTOListToPOList(List<UnitMappingDTO> list);

//    @Mapping(source = "unitStructDTOList", target = "unitStructList")
//    CourseStructPO courseStructDTOToPO(CourseStructDTO dto);
    List<CourseStructPO> courseStructDTOListToPOList(List<CourseStructDTO> list);

    List<UnitStructPO> unitStructDTOListToPOList(List<UnitStructDTO> list);

    @Mapping(source = "children", target = "blockStructList")
    UnitStructPO unitStructDTOToPO(UnitStructDTO dto);

    @Mapping(source = "children", target = "childrenList")
    BlockStructPO blockStructDTOToPO(BlockStructDTO dto);
    List<BlockStructPO> blockStructDTOListToPOList(List<BlockStructDTO> list);

    // 新增 default 方法
    default CopyCourseDataPO copyCourseDataDTOToPOWithCheck(CopyCourseDataDTO dto) {
        if (dto == null) return null;
        CopyCourseDataPO.Builder builder = CopyCourseDataPO.newBuilder();
        if (dto.getUnitMappings() != null && !dto.getUnitMappings().isEmpty()) {
            List<UnitMappingPO> list = unitMappingDTOListToPOList(dto.getUnitMappings());
            if (list != null) {
                builder.addAllUnitMappings(list);
            }
        }
        if (dto.getNewBookId() != null) {
            builder.setNewBookId(dto.getNewBookId());
        }
        return builder.build();
    }
}

