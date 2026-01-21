package com.weixiao;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TempPasswordGen {

    @Test
    public void generate() {
        // 生成 123456 的 BCrypt Hash
        String encoded = new BCryptPasswordEncoder().encode("123456");
        System.out.println("Encoded password: " + encoded);
    }
}