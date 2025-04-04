package com.hyperfix.producer;

import com.hyperfix.producer.util.Producer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

/**
 * @author JiangChunYu
 * 2025/4/4
 */
@SpringBootApplication
public class ProducerApplication implements CommandLineRunner {
    @Resource
    private Producer producer;

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        producer.send(100);
    }
}
