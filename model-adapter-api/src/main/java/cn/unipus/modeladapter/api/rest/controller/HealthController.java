package cn.unipus.modeladapter.api.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 健康检查控制器
 *
 * @author haijun.gao
 * @date 2025/7/7
 */
@RestController
@RequestMapping("/health")
public class HealthController {

    @GetMapping("/check")
    public Integer checkHealth() {
        return null;
    }
}
