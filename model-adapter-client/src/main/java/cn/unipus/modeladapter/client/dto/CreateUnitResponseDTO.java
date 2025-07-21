package cn.unipus.modeladapter.client.dto;

import lombok.Data;

/**
 * 创建单元响应DTO
 *
 * @author Gemini
 * @since 2024-07-22
 */
@Data
public class CreateUnitResponseDTO {
    private StatusDTO status;
    private String bookId;
    private String newUnitId;
}