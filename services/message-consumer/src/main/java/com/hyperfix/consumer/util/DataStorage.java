package com.hyperfix.consumer.util;

import com.hyperfix.models.common.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author JiangChunYu
 * 2025/4/4
 */
@Slf4j
@Component
public class DataStorage {
    private final CopyOnWriteArrayList<Message> storage = new CopyOnWriteArrayList<>();

    public void addMessage(Message message) {
        log.info("添加了数据: {}", message);
        this.storage.add(message);
    }

    public Map<String, Integer> getStatistics(Long start, Long endTime) {
        // 将分钟单位转换为秒单位
        long startSeconds = start * 60;
        long endSeconds = endTime * 60;

        Map<String, Integer> result = new HashMap<>();
        result.put("A", 0);
        result.put("B", 0);
        result.put("C", 0);
        result.put("D", 0);

        // 根据时间戳过滤数据
        storage.stream()
                .filter(msg -> msg.getTimestamp() >= startSeconds && msg.getTimestamp() <= endSeconds)
                .forEach(msg -> {
                    String type = msg.getType();
                    result.put(type, result.getOrDefault(type, 0) + 1);
                });
        return result;
    }
}
