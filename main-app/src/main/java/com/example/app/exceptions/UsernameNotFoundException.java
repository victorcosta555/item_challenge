package com.example.app.exceptions;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class UsernameNotFoundException extends RuntimeException{

    private String message;
}
