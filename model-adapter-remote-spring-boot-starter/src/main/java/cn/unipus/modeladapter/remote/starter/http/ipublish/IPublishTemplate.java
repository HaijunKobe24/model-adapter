package cn.unipus.modeladapter.remote.starter.http.ipublish;

import cn.unipus.modeladapter.remote.starter.common.constant.CodeEnum;
import cn.unipus.modeladapter.remote.starter.common.exception.HttpException;
import cn.unipus.modeladapter.remote.starter.common.utils.ExceptionUtils;
import cn.unipus.modeladapter.remote.starter.http.ipublish.config.IPublishRemoteProperties;
import cn.unipus.modeladapter.remote.starter.http.ipublish.dto.*;
import com.google.common.cache.Cache;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

/**
 * iPublish接口模板
 *
 * @author haijun.gao
 * @date 2025/7/1
 */
@Slf4j
@AllArgsConstructor
public class IPublishTemplate {

    private static final String TOKEN_FMT = "Bearer %s";

    private IPublishRemoteProperties properties;

    private WebClient webClient;

    private Cache<String, String> cache;

    /**
     * 获取iPublish的访问令牌
     *
     * @param request 访问令牌请求参数
     * @return 访问令牌
     */
    public String accessToken(AccessTokenRequest request) {
        request.setAppId(properties.getAppId());
        request.setReaderType(properties.getReaderType());
        log.info("accessToken request：{}", request);
        IPublishResponse<AccessTokenDTO> response = webClient.post()
                .uri(properties.getUri().getAccessTokenUri()).bodyValue(request).retrieve()
                .bodyToMono(new ParameterizedTypeReference<IPublishResponse<AccessTokenDTO>>() {
                }).block();
        log.info("accessToken response：{}", response);
        ExceptionUtils.checkAndThrow(response, true);
        return String.format(TOKEN_FMT, Objects.requireNonNull(response).getData().getToken());
    }

    /**
     * 复制iPublish内容
     *
     * @param request 复制内容请求参数
     * @return 复制内容结果
     */
    public CopyContentDTO copyContent(CopyContentRequest request, AccessTokenRequest tokenRequest) {
        log.info("copyContent request：{}", request);
        IPublishResponse<CopyContentDTO> response = webClient.post()
                .uri(properties.getUri().getCopyContentUri())
                .header(properties.getTokenHeader(), this.loadAccessToken(tokenRequest))
                .bodyValue(request).retrieve()
                .bodyToMono(new ParameterizedTypeReference<IPublishResponse<CopyContentDTO>>() {
                }).block();
        log.info("copyContent response：{}", response);
        ExceptionUtils.checkAndThrow(response, true);
        return Objects.requireNonNull(response).getData();
    }

    /**
     * 获取已发布的自建内容简化信息
     *
     * @param request      自建内容ID请求参数
     * @param tokenRequest 访问令牌请求参数
     * @return 已发布的自建内容简化信息列表
     */
    public CustomContentSimpleListDTO getPublishedCustomContentSimple(
            CustomContentIdRequest request, AccessTokenRequest tokenRequest) {
        log.info("getPublishedCustomContentSimple request：{}", request);
        String accessToken = this.loadAccessToken(tokenRequest);
        IPublishResponse<CustomContentSimpleListDTO> response = webClient.post()
                .uri(properties.getUri().getGetPublishedContentUri())
                .header(properties.getTokenHeader(), accessToken).bodyValue(request).retrieve()
                .bodyToMono(
                        new ParameterizedTypeReference<IPublishResponse<CustomContentSimpleListDTO>>() {
                        }).block();
        log.info("getPublishedCustomContentSimple response：{}", response);
        ExceptionUtils.checkAndThrow(response, true);
        return Objects.requireNonNull(response).getData();
    }

    /**
     * 获取编写中的自建内容简化信息
     *
     * @param request      自建内容ID请求参数
     * @param tokenRequest 访问令牌请求参数
     * @return 编写中的自建内容简化信息列表
     */
    public CustomContentSimpleListDTO getEditingCustomContentSimple(CustomContentIdRequest request,
            AccessTokenRequest tokenRequest) {
        log.info("getEditingCustomContentSimple request：{}", request);
        String accessToken = this.loadAccessToken(tokenRequest);
        IPublishResponse<CustomContentSimpleListDTO> response = webClient.post()
                .uri(properties.getUri().getGetEditingContentUri())
                .header(properties.getTokenHeader(), accessToken).bodyValue(request).retrieve()
                .bodyToMono(
                        new ParameterizedTypeReference<IPublishResponse<CustomContentSimpleListDTO>>() {
                        }).block();
        log.info("getEditingCustomContentSimple response：{}", response);
        ExceptionUtils.checkAndThrow(response, true);
        return Objects.requireNonNull(response).getData();
    }

    /**
     * 发布自建内容
     *
     * @param request      发布自建内容请求参数
     * @param tokenRequest 访问令牌请求参数
     * @return 发布操作结果
     */
    public Boolean publishCustomContent(PublishCustomContentRequest request,
            AccessTokenRequest tokenRequest) {
        log.info("publishCustomContent request：{}", request);
        String accessToken = this.loadAccessToken(tokenRequest);
        IPublishResponse<Boolean> response = webClient.post()
                .uri(properties.getUri().getPublishContentUri())
                .header(properties.getTokenHeader(), accessToken).bodyValue(request).retrieve()
                .bodyToMono(new ParameterizedTypeReference<IPublishResponse<Boolean>>() {
                }).block();
        log.info("publishCustomContent response：{}", response);
        ExceptionUtils.checkAndThrow(response, true);
        return Objects.requireNonNull(response).getData();
    }

    /**
     * 保存编写中的自建内容信息
     *
     * @param request      保存自建内容请求参数
     * @param tokenRequest 访问令牌请求参数
     */
    public void saveCustomContent(SaveCustomContentRequest request,
            AccessTokenRequest tokenRequest) {
        log.info("saveCustomContent request：{}", request);
        String accessToken = this.loadAccessToken(tokenRequest);
        IPublishResponse<Boolean> response = webClient.post()
                .uri(properties.getUri().getSaveContentUri())
                .header(properties.getTokenHeader(), accessToken).bodyValue(request).retrieve()
                .bodyToMono(new ParameterizedTypeReference<IPublishResponse<Boolean>>() {
                }).block();
        log.info("saveCustomContent response：{}", response);
        ExceptionUtils.checkAndThrow(response, true);
        if (!Objects.requireNonNull(response).getData()) {
            throw new HttpException(CodeEnum.SERVER_ERROR);
        }
    }

    /**
     * 删除自建内容
     *
     * @param request      删除自建内容请求参数
     * @param tokenRequest 访问令牌请求参数
     */
    public void deleteCustomContentByBizIds(CustomContentIdRequest request,
            AccessTokenRequest tokenRequest) {
        log.info("deleteCustomContentByBizIds request：{}", request);
        String accessToken = this.loadAccessToken(tokenRequest);
        IPublishResponse<Boolean> response = webClient.post()
                .uri(properties.getUri().getDeleteContentUri())
                .header(properties.getTokenHeader(), accessToken).bodyValue(request).retrieve()
                .bodyToMono(new ParameterizedTypeReference<IPublishResponse<Boolean>>() {
                }).block();
        log.info("deleteCustomContentByBizIds response：{}", response);
        ExceptionUtils.checkAndThrow(response, true);
        if (!Objects.requireNonNull(response).getData()) {
            throw new HttpException(CodeEnum.SERVER_ERROR);
        }
    }

    /**
     * 获取教材结构
     *
     * @param bookId       教材唯一标识符
     * @param tokenRequest 访问令牌请求参数
     * @return 教材结构信息
     */
    public BookStructDTO getBookStruct(String bookId, AccessTokenRequest tokenRequest) {
        log.info("getBookStruct request bookId: {}", bookId);
        IPublishResponse<BookStructDTO> response = webClient.get()
                .uri(properties.getUri().getGetBookStructUri(bookId))
                .header(properties.getTokenHeader(), this.loadAccessToken(tokenRequest)).retrieve()
                .bodyToMono(new ParameterizedTypeReference<IPublishResponse<BookStructDTO>>() {
                }).block();
        log.info("getBookStruct response: {}", response);
        ExceptionUtils.checkAndThrow(response, true);
        return Objects.requireNonNull(response).getData();
    }

    /**
     * 加载访问令牌
     *
     * @param request 访问令牌请求参数
     * @return 访问令牌
     */
    private String loadAccessToken(AccessTokenRequest request) {
        String cacheKey = StringUtils.joinWith(StringUtils.SPACE, properties.getTokenHeader(),
                properties.getAppId(), request.getOpenId());
        // 先从缓存中获取token
        String cachedToken = cache.getIfPresent(cacheKey);
        if (cachedToken != null) {
            return cachedToken;
        }
        // 缓存中没有，调用API获取token
        String token = this.accessToken(request);
        // 将token写入缓存
        cache.put(cacheKey, token);
        return token;
    }
}
