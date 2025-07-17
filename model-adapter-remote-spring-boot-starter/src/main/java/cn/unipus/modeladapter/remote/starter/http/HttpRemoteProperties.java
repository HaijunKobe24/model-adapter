package cn.unipus.modeladapter.remote.starter.http;

import lombok.Data;

/**
 * Http远程服务配置
 *
 * @author haijun.gao
 * @date 2025/7/1
 */
@Data
public class HttpRemoteProperties {

    private String baseUrl;

    private Integer timeout;
}