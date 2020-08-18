package com.example.wongnai;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import com.example.wongnai.services.impl.FoodKeyWordIndexService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class WechallengeApplication {

    public static void main(String[] args) {
        SpringApplication.run(WechallengeApplication.class, args);
    }

    @Bean
    public RestOperations restOperations() {
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON));
        restTemplate.getMessageConverters().add(0, converter);
        return restTemplate;
    }

    @Scheduled(fixedRate = 1000)
    private void testScheduled() {
        FoodKeyWordIndexService.getInstance().start();
    }
}
