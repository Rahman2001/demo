package com.app.demo.models;

import lombok.*;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LLMResponseDto {
    private String response;
    private Timestamp createdAt;
}
