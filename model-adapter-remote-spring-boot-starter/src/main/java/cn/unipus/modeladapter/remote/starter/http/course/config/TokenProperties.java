package cn.unipus.modeladapter.remote.starter.http.course.config;

import cn.unipus.modeladapter.remote.starter.common.utils.JwtUtils;
import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

/**
 * 课程token配置
 *
 * @author haijun.gao
 * @date 2025/7/17
 */
@Data
public class TokenProperties {

    private String headerName;

    private String secret;

    private String iss;

    private String aud;

    /**
     * 生成token
     *
     * @param openId 用户id
     * @return token，格式：<请求头名称, token>
     */
    public Pair<String, String> getToken(String openId) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("open_id", openId);
        dataMap.put("openid", openId);
        dataMap.put("iss", this.getIss());
        dataMap.put("aud", this.getAud());
        dataMap.put("audience", this.getAud());
        return Pair.of(this.getHeaderName(), JwtUtils.genToken(dataMap, this.getSecret()));
    }
}