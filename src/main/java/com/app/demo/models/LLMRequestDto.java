package com.app.demo.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LLMRequestDto {
    private String system;
    private String prompt;
    private String model;
    private boolean stream;
}
