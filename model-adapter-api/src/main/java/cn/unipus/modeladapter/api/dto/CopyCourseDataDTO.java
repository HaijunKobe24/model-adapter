package cn.unipus.modeladapter.api.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CopyCourseDataDTO implements Serializable {
    private String newBookId;
    private List<UnitMappingDTO> unitMappings;
}