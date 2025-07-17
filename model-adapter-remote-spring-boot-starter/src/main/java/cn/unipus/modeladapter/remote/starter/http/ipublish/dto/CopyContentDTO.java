package cn.unipus.modeladapter.remote.starter.http.ipublish.dto;

import lombok.Data;

import java.util.Map;

/**
 * 批量复制内容DTO
 *
 * @author haijun.gao
 * @date 2025/7/1
 */
@Data
public class CopyContentDTO {

    private Map<String, String> bizIdMapping;
}
