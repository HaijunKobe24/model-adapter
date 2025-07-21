package cn.unipus.modeladapter.client.dto;

import lombok.Data;

/**
 * 删除单元请求DTO
 *
 * @author Gemini
 * @since 2024-07-22
 */
@Data
public class DeleteUnitRequestDTO {
    private String bookId;
    private String unitId;
}