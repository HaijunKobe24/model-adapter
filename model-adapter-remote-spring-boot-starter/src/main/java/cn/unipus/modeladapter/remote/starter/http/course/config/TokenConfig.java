package cn.unipus.modeladapter.remote.starter.http.course.config;

import lombok.Data;

@Data
public class TokenConfig {

    private String headerName;

    private String secret;

    private String iss;

    private String aud;
}