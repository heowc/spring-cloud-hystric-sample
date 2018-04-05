package com.heowc;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@RestController
@SpringBootApplication
@EnableCircuitBreaker
public class ApiProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiProductApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Autowired
    private RestTemplate restTemplate;

    private static final String USER_API = "http://localhost:8082/user";

    @HystrixCommand(fallbackMethod = "findProductFallback")
    @GetMapping("/product")
    public Stream<Product> findProductAll() {
        try {
            List<User> list = Arrays.asList(
                    restTemplate.getForObject(USER_API + "/heowc", User.class),
                    restTemplate.getForObject(USER_API + "/naeun", User.class)
            );

            return list.stream().map(u -> new Product(Math.random(), "product", 0, u.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return findProductFallback();
    }

    public Stream<Product> findProductFallback() {
        return Stream.of(new Product(Math.random(), "hystrix", 0, ""));
    }

    public static class User {

        private String name;
        private Double uid;

        protected User () { }

        public User(String name, Double uid) {
            this.name = name;
            this.uid = uid;
        }

        public String getName() {
            return name;
        }

        public Double getUid() {
            return uid;
        }
    }

    public static class Product {

        private Double id;
        private String name;
        private Integer remainCount;
        private String userName;

        protected Product() { }

        public Product(Double id, String name, Integer remainCount, String userName) {
            this.id = id;
            this.name = name;
            this.remainCount = remainCount;
            this.userName = userName;
        }

        public Double getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Integer getRemainCount() {
            return remainCount;
        }

        public String getUserName() {
            return userName;
        }
    }

}
