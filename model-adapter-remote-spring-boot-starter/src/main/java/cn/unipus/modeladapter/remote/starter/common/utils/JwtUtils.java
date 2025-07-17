package cn.unipus.modeladapter.remote.starter.common.utils;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;

import java.util.Map;

/**
 * JWT帮助类
 *
 * @author haijun.gao
 * @date 2025/4/15
 */
@Slf4j
public class JwtUtils {

    /**
     * 生成token
     *
     * @param dataMap 明文体
     * @param secret  密钥
     * @return token
     */
    public static String genToken(Map<String, Object> dataMap, String secret) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.putAll(dataMap);
        Payload payload = new Payload(jsonObj);
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            // 创建HMAC的签名
            JWSSigner signer = new MACSigner(secret.getBytes());
            jwsObject.sign(signer);
            return jwsObject.serialize();
        } catch (Exception e) {
            log.error("jwt error, json: {}", dataMap, e);
            throw new RuntimeException(e);
        }
    }
}
