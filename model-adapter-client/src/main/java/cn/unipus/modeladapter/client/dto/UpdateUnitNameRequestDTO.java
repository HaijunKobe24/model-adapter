package cn.unipus.modeladapter.client.dto;

import lombok.Data;

/**
 * 更新单元名称请求DTO
 *
 * @author Gemini
 * @since 2024-07-22
 */
@Data
public class UpdateUnitNameRequestDTO {
    private String bookId;
    private String unitId;
    private String name;
}