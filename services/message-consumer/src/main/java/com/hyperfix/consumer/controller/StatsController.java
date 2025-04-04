package com.hyperfix.consumer.controller;

import com.hyperfix.consumer.util.DataStorage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author JiangChunYu
 * 2025/4/4
 */
@RestController
@RequestMapping("/stats")
public class StatsController {
    @Resource
    private DataStorage dataStorage;

    @RequestMapping("/info")
    public Map<String, Integer> getStats(@RequestParam Long start, @RequestParam Long end) {
        return dataStorage.getStatistics(start, end);
    }
}
