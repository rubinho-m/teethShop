package com.rubinho.teethshop;

import com.rubinho.teethshop.services.FileService;
import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
public class TeethShopApplication implements CommandLineRunner {
    @Resource
    FileService fileService;

    public static void main(String[] args) {
        SpringApplication.run(TeethShopApplication.class, args);
    }

    @Override
    public void run(String... arg) {
        fileService.init();
    }

}
