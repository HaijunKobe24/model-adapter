package cn.unipus.modeladapter.remote.starter.http.course;

import cn.unipus.modeladapter.remote.starter.common.utils.ExceptionUtils;
import cn.unipus.modeladapter.remote.starter.common.utils.JwtUtils;
import cn.unipus.modeladapter.remote.starter.http.HttpResponse;
import cn.unipus.modeladapter.remote.starter.http.course.config.CourseRemoteProperties;
import cn.unipus.modeladapter.remote.starter.http.course.config.TokenConfig;
import cn.unipus.modeladapter.remote.starter.http.course.dto.StructSyncRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

/**
 * 课程接口模板
 *
 * @author haijun.gao
 * @date 2025/7/1
 */
@AllArgsConstructor
@Slf4j
public class CourseTemplate {

    private CourseRemoteProperties properties;

    private WebClient webClient;

    /**
     * 同步教程结构
     *
     * @param request 结构请求
     * @param creator
     */
    public void structSync(StructSyncRequest request, String creator) {
        log.info("StructSyncRequest request: {}", request);
        Pair<String, String> pair = this.getToken(creator);
        log.info("StructSyncRequest token: {}", pair);
        HttpResponse<Boolean> response = webClient.post()
                .uri(properties.getStructSyncUri())
                .header(pair.getLeft(), pair.getRight())
                .bodyValue(request)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<HttpResponse<Boolean>>() {
                }).block();
        log.info("Struct sync response: {}", response);
        ExceptionUtils.checkAndThrow(response, false);
    }

    /**
     * 生成token
     *
     * @param openId 用户id
     * @return token，格式：<请求头名称, token>
     */
    public Pair<String, String> getToken(String openId) {
        Map<String, Object> dataMap = new HashMap<>();
        TokenConfig tokenConfig = properties.getToken();
        dataMap.put("open_id", openId);
        dataMap.put("openid", openId);
        dataMap.put("iss", tokenConfig.getIss());
        dataMap.put("aud", tokenConfig.getAud());
        dataMap.put("audience", tokenConfig.getAud());
        return Pair.of(tokenConfig.getHeaderName(),
                JwtUtils.genToken(dataMap, tokenConfig.getSecret()));
    }


}
