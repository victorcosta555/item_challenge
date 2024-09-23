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
        for (Object param : params) {
            if (param instanceof UUID) {
                return param.toString();
            }
        }

        return method.getName() + Arrays.toString(params);
    }
}
