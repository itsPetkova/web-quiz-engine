package com.example.webquizengine.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizResponseDto {
    private final boolean success;
    private final String feedback;

    public QuizResponseDto(boolean success, String feedback) {
        this.success = success;
        this.feedback = feedback;
    }

}
