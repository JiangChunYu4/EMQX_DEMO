package com.hyperfix.producer.util;

import com.hyperfix.models.common.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Random;

/**
 * @author JiangChunYu
 * 2025/4/4
 */
@Slf4j
@Service
public class Producer {
    @Resource
    private MQTTClientUtil mqttClientUtil;

    /**
     * 发送消息
     * @param num 消息条数
     */
    public void send(int num) {
        Random random = new Random();
        for (int i = 0; i < num; i++) {
            // 生成timestamp（发送时刻前后30秒的随机时间）
            long currentEpochSecond = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
            long randomOffset = random.nextInt(61) - 30; // -30到+30秒的随机偏移
            long timestamp = currentEpochSecond + randomOffset;

            // 随机选择type（A、B、C、D）
            String[] types = {"A", "B", "C", "D"};
            String type = types[random.nextInt(types.length)];

            Message message = new Message(timestamp, type);

            // 发送消息
            mqttClientUtil.publish(message);
            try {
                Thread.sleep(10);  // 防止消息发送过快
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
        }
    }
}
