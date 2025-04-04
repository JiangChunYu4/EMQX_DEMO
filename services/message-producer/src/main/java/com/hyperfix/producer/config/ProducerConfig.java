package com.hyperfix.producer.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author JiangChunYu
 * 2025/4/4
 */
@Data
@Configuration
@ConfigurationProperties("spring.mqtt")
public class ProducerConfig {
    /**
     * 服务端地址
     */
    private String host;

    /**
     * 客户端id
     */
    private String clientId;

    /**
     * 默认主题
     */
    private String defaultTopic;
}
