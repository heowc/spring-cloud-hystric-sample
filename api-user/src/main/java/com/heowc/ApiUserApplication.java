package com.heowc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class ApiUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiUserApplication.class, args);
    }

    @GetMapping("/user/{name}")
    public User findUser(@PathVariable("name") String name) {
        return new User(name, Math.random());
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
}
