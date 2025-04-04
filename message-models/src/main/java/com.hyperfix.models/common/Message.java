package com.hyperfix.models.common;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author JiangChunYu
 * 2025/4/4
 */
@Data
@AllArgsConstructor
public class Message {
    private Long timestamp;
    private String type;
}
