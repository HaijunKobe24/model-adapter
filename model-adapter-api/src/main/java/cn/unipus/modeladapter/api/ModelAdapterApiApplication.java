package cn.unipus.modeladapter.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 模型适配器API启动类
 *
 * @author haijun.gao
 * @date 2025/7/1
 */
@Slf4j
@EnableTransactionManagement(proxyTargetClass = true)
@EnableJpaRepositories(basePackages = "cn.unipus.modeladapter.base.db.repository")
@EntityScan(basePackages = "cn.unipus.modeladapter.base.db.entity")
@SpringBootApplication(scanBasePackages = "cn.unipus")
public class ModelAdapterApiApplication {
    public static void main(String[] args) {
        log.info("ModelAdapterApiApplication 开始启动 ...");
        SpringApplication.run(ModelAdapterApiApplication.class, args);
        log.info("ModelAdapterApiApplication 启动完成!");
    }
}
