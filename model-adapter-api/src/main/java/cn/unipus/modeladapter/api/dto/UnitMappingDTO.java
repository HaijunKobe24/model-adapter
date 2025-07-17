package cn.unipus.modeladapter.api.dto;

import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;

@Data(staticConstructor = "of")
public class UnitMappingDTO implements Serializable {
    @NonNull
    private String sourceUnitId;
    @NonNull
    private String newUnitId;
}