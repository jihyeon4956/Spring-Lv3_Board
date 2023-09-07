package com.sparta.lvtwohomework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfig { // passwordConfig 이렇게 바뀌면서 등록됨.

    @Bean
    public PasswordEncoder passwordEncoder() { // passwordEncoder 이렇게 등록됨.
        return new BCryptPasswordEncoder(); // BCrypt 해쉬암호 이다.
    }
}
