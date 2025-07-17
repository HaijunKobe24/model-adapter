package cn.unipus.modeladapter.api.mapper.course;

import cn.unipus.modeladapter.api.dto.CopyCourseDataDTO;
import cn.unipus.modeladapter.api.dto.UnitMappingDTO;
import cn.unipus.modeladapter.api.proto.client.model.BlockStructPO;
import cn.unipus.modeladapter.api.proto.client.model.CopyCourseDataPO;
import cn.unipus.modeladapter.api.proto.client.model.CourseStructPO;
import cn.unipus.modeladapter.api.proto.client.model.UnitMappingPO;
import cn.unipus.modeladapter.api.proto.client.model.UnitStructPO;
import cn.unipus.modeladapter.remote.starter.http.course.dto.BlockStructDTO;
import cn.unipus.modeladapter.remote.starter.http.course.dto.CourseStructDTO;
import cn.unipus.modeladapter.remote.starter.http.course.dto.UnitStructDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-16T16:35:32+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_431 (Oracle Corporation)"
)
@Component
public class CourseProtoMapperImpl implements CourseProtoMapper {

    @Override
    public CopyCourseDataPO copyCourseDataDTOToPO(CopyCourseDataDTO dto) {

        CopyCourseDataPO.Builder copyCourseDataPO = CopyCourseDataPO.newBuilder();

        if ( dto != null ) {
            if ( copyCourseDataPO.getUnitMappingsList() != null ) {
                List<UnitMappingPO> list = unitMappingDTOListToPOList( dto.getUnitMappings() );
                if ( list != null ) {
                    copyCourseDataPO.getUnitMappingsList().addAll( list );
                }
            }
            if ( dto.getNewBookId() != null ) {
                copyCourseDataPO.setNewBookId( dto.getNewBookId() );
            }
        }

        return copyCourseDataPO.build();
    }

    @Override
    public UnitMappingPO unitMappingDTOToPO(UnitMappingDTO dto) {

        UnitMappingPO.Builder unitMappingPO = UnitMappingPO.newBuilder();

        if ( dto != null ) {
            if ( dto.getSourceUnitId() != null ) {
                unitMappingPO.setSourceUnitId( dto.getSourceUnitId() );
            }
            if ( dto.getNewUnitId() != null ) {
                unitMappingPO.setNewUnitId( dto.getNewUnitId() );
            }
        }

        return unitMappingPO.build();
    }

    @Override
    public List<UnitMappingPO> unitMappingDTOListToPOList(List<UnitMappingDTO> list) {
        if ( list == null ) {
            return new ArrayList<UnitMappingPO>();
        }

        List<UnitMappingPO> list1 = new ArrayList<UnitMappingPO>( list.size() );
        for ( UnitMappingDTO unitMappingDTO : list ) {
            list1.add( unitMappingDTOToPO( unitMappingDTO ) );
        }

        return list1;
    }

    @Override
    public List<CourseStructPO> courseStructDTOListToPOList(List<CourseStructDTO> list) {
        if ( list == null ) {
            return new ArrayList<CourseStructPO>();
        }

        List<CourseStructPO> list1 = new ArrayList<CourseStructPO>( list.size() );
        for ( CourseStructDTO courseStructDTO : list ) {
            list1.add( courseStructDTOToCourseStructPO( courseStructDTO ) );
        }

        return list1;
    }

    @Override
    public List<UnitStructPO> unitStructDTOListToPOList(List<UnitStructDTO> list) {
        if ( list == null ) {
            return new ArrayList<UnitStructPO>();
        }

        List<UnitStructPO> list1 = new ArrayList<UnitStructPO>( list.size() );
        for ( UnitStructDTO unitStructDTO : list ) {
            list1.add( unitStructDTOToPO( unitStructDTO ) );
        }

        return list1;
    }

    @Override
    public UnitStructPO unitStructDTOToPO(UnitStructDTO dto) {

        UnitStructPO.Builder unitStructPO = UnitStructPO.newBuilder();

        if ( dto != null ) {
            if ( unitStructPO.getBlockStructList() != null ) {
                List<BlockStructPO> list = blockStructDTOListToPOList( dto.getChildren() );
                if ( list != null ) {
                    unitStructPO.getBlockStructList().addAll( list );
                }
            }
            if ( dto.getId() != null ) {
                unitStructPO.setId( dto.getId() );
            }
            if ( dto.getText() != null ) {
                unitStructPO.setText( dto.getText() );
            }
            if ( dto.getNumber() != null ) {
                unitStructPO.setNumber( dto.getNumber() );
            }
            if ( dto.getType() != null ) {
                unitStructPO.setType( dto.getType() );
            }
            if ( dto.getCreatedType() != null ) {
                unitStructPO.setCreatedType( dto.getCreatedType() );
            }
        }

        return unitStructPO.build();
    }

    @Override
    public BlockStructPO blockStructDTOToPO(BlockStructDTO dto) {

        BlockStructPO.Builder blockStructPO = BlockStructPO.newBuilder();

        if ( dto != null ) {
            if ( blockStructPO.getChildrenList() != null ) {
                List<BlockStructPO> list = blockStructDTOListToPOList( dto.getChildren() );
                if ( list != null ) {
                    blockStructPO.getChildrenList().addAll( list );
                }
            }
            if ( dto.getId() != null ) {
                blockStructPO.setId( dto.getId() );
            }
            if ( dto.getText() != null ) {
                blockStructPO.setText( dto.getText() );
            }
            if ( dto.getType() != null ) {
                blockStructPO.setType( dto.getType() );
            }
            if ( dto.getCreatedType() != null ) {
                blockStructPO.setCreatedType( dto.getCreatedType() );
            }
            if ( dto.getWordCount() != null ) {
                blockStructPO.setWordCount( dto.getWordCount() );
            }
            if ( dto.getQuestId() != null ) {
                blockStructPO.setQuestId( dto.getQuestId() );
            }
            if ( dto.getQuestVersion() != null ) {
                blockStructPO.setQuestVersion( dto.getQuestVersion() );
            }
            if ( dto.getSubQuestionCount() != null ) {
                blockStructPO.setSubQuestionCount( dto.getSubQuestionCount() );
            }
            if ( dto.getQuestionType() != null ) {
                blockStructPO.setQuestionType( dto.getQuestionType() );
            }
        }

        return blockStructPO.build();
    }

    @Override
    public List<BlockStructPO> blockStructDTOListToPOList(List<BlockStructDTO> list) {
        if ( list == null ) {
            return new ArrayList<BlockStructPO>();
        }

        List<BlockStructPO> list1 = new ArrayList<BlockStructPO>( list.size() );
        for ( BlockStructDTO blockStructDTO : list ) {
            list1.add( blockStructDTOToPO( blockStructDTO ) );
        }

        return list1;
    }

    protected CourseStructPO courseStructDTOToCourseStructPO(CourseStructDTO courseStructDTO) {

        CourseStructPO.Builder courseStructPO = CourseStructPO.newBuilder();

        if ( courseStructDTO != null ) {
            if ( courseStructDTO.getBookId() != null ) {
                courseStructPO.setBookId( courseStructDTO.getBookId() );
            }
            if ( courseStructDTO.getUnitStruct() != null ) {
                courseStructPO.setUnitStruct( courseStructDTO.getUnitStruct() );
            }
        }

        return courseStructPO.build();
    }
}
