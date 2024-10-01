package com.example.app.config;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.UUID;

@Component
public class CustomKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        return Arrays.stream(params)
                .filter(obj -> obj instanceof UUID)
                .map(obj -> method.getName() + obj.toString())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No UUID parameter found"));
    }
}
