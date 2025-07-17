package cn.unipus.modeladapter.remote.starter.http.ipublish.dto;

import lombok.Data;

import java.util.List;

/**
 * 自建内容简化信息列表DTO
 *
 * @author haijun.gao
 * @date 2025/7/2
 */
@Data
public class CustomContentSimpleListDTO {

    /**
     * 自建内容列表
     */
    private List<CustomContentSimpleDTO> customContentList;
}