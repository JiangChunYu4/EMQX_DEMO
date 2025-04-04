package com.hyperfix.producer.util;

import com.alibaba.fastjson.JSON;
import com.hyperfix.models.common.Message;
import com.hyperfix.producer.config.ProducerConfig;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

/**
 * @author JiangChunYu
 * 2025/4/4
 */
@Slf4j
@Configuration
public class MQTTClientUtil {

    @Resource
    private ProducerConfig producerConfig;

    private MqttClient mqttClient;


    private void connect() {
        try {
            this.mqttClient.connect();
            log.info("发送方连接成功！");
        } catch (MqttException mqttException) {
            log.error("发送方连接失败！");
        }
    }

    @PostConstruct
    private void init() {
        try {
            this.mqttClient = new MqttClient(producerConfig.getHost(), producerConfig.getClientId());
            log.info("发送方创建成功！");
            this.connect();
        } catch (MqttException exception) {
            log.error("发送方创建失败！");
        }
    }

    public void publish(Message message) {
        log.info("主题名:{}, message:{}", producerConfig.getDefaultTopic(), message);
        MqttMessage mqttMessage = new MqttMessage(JSON.toJSONString(message).getBytes(StandardCharsets.UTF_8));
        try {
            this.mqttClient.publish(producerConfig.getDefaultTopic(), mqttMessage);
        } catch (MqttException e) {
            log.error("发送消息失败！");
        }
    }

    @PreDestroy
    private void destroy() {
        try {
            if (this.mqttClient != null && this.mqttClient.isConnected()) {
                this.mqttClient.disconnect();
                this.mqttClient.close();
            }
        } catch (MqttException e) {
            log.error("发送方关闭失败！");
        }
    }
}


