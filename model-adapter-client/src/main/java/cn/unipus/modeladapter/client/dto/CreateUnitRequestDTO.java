package cn.unipus.modeladapter.client.dto;

import lombok.Data;

/**
 * 创建单元请求DTO
 *
 * @author Gemini
 * @since 2024-07-22
 */
@Data
public class CreateUnitRequestDTO {
    private String bookId;
    private String name;
}