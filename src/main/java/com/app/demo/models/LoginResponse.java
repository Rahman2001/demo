package com.app.demo.models;

import lombok.*;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private long expiresIn;

}
