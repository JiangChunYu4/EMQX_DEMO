package com.hyperfix.consumer.util;

import com.alibaba.fastjson.JSON;
import com.hyperfix.models.common.Message;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;


/**
 * @author JiangChunYu
 * 2025/4/4
 */
@Slf4j
@Component
public class MessageCallbackListener implements IMqttMessageListener {
    @Resource
    private DataStorage dataStorage;

    /**
     * 当订阅主题有消息时触发回调
     * @param topic 消息所属主题
     * @param message 消息
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) {
        String messageBody = new String(message.getPayload(), StandardCharsets.UTF_8);
        Message m = JSON.parseObject(messageBody, Message.class);
        dataStorage.addMessage(m);
    }
}
