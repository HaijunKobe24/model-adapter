package cn.unipus.modeladapter.remote.starter.http.course;

import cn.unipus.modeladapter.remote.starter.common.utils.ExceptionUtils;
import cn.unipus.modeladapter.remote.starter.http.HttpResponse;
import cn.unipus.modeladapter.remote.starter.http.course.config.CourseRemoteProperties;
import cn.unipus.modeladapter.remote.starter.http.course.dto.StructSyncRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;

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
     * 课程结构同步
     *
     * @param request 课程结构同步请求
     * @param creator 创建人
     */
    public void structSync(StructSyncRequest request, String creator) {
        log.info("StructSyncRequest request: {}", request);
        Pair<String, String> pair = properties.getToken().getToken(creator);
        log.info("StructSyncRequest token: {}", pair);
        HttpResponse<Boolean> response = webClient.post().uri(properties.getStructSyncUri())
                .header(pair.getLeft(), pair.getRight()).bodyValue(request).retrieve()
                .bodyToMono(new ParameterizedTypeReference<HttpResponse<Boolean>>() {
                }).block();
        log.info("Struct sync response: {}", response);
        ExceptionUtils.checkAndThrow(response, false);
    }

}
