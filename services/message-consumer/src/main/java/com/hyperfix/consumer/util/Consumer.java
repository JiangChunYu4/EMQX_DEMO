package com.hyperfix.consumer.util;

import com.hyperfix.consumer.config.ConsumerConfig;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * @author JiangChunYu
 * 2025/4/4
 */
@Slf4j
@Service
public class Consumer {
    @Resource
    private ConsumerConfig consumerConfig;
    @Resource
    private MessageCallbackListener messageCallbackListener;

    private MqttClient mqttClient;


    private void connect() {
        try {
            this.mqttClient.connect();
            this.subscribe(consumerConfig.getDefaultTopic(), 2, messageCallbackListener);
            log.info("接收方连接成功！");
        } catch (MqttException mqttException) {
            log.error("接收方连接失败！");
        }
    }

    @PostConstruct
    private void init() {
        try {
            this.mqttClient = new MqttClient(consumerConfig.getHost(), consumerConfig.getClientId());
            log.info("接收方创建成功！");
            this.connect();
        } catch (MqttException exception) {
            log.error("接收方创建失败！");
        }
    }

    public void subscribe(String topicName, int qos, IMqttMessageListener messageListener) {
        try {
            this.mqttClient.subscribe(topicName, qos, messageListener);
            log.error("订阅主题: {}", topicName);
        } catch (MqttException e) {
            log.error("订阅主题失败！{}", topicName);
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
