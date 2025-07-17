package cn.unipus.modeladapter.remote.starter.common.utils;

import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

/**
 * Http工具类
 *
 * @author haijun.gao
 * @date 2025/7/1
 */
public class HttpUtils {

    /**
     * 创建WebClient
     *
     * @param baseUrl         请求地址
     * @param timeout         超时时间，单位s
     * @param maxInMemorySize 最大内存大小，单位字节
     * @return WebClient
     */
    public static WebClient createWebClient(String baseUrl, int timeout, int maxInMemorySize) {
        ReactorClientHttpConnector connector = new ReactorClientHttpConnector(
                HttpClient.create().responseTimeout(Duration.ofSeconds(timeout)));
        return WebClient.builder().exchangeStrategies(ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(maxInMemorySize))
                .build()).baseUrl(baseUrl).clientConnector(connector).build();
    }
}
