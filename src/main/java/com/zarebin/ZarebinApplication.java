package com.zarebin;

import com.zarebin.service.CarInspectionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ZarebinApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZarebinApplication.class, args);
    }

    @Bean
    CommandLineRunner publicIdBackfill(CarInspectionService service) {
        return args -> service.backfillPublicIds();
    }
}
