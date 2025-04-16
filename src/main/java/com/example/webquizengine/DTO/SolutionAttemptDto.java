package com.example.webquizengine.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public class SolutionAttemptDto {
    @NotNull
    private final Integer[] answer;

    @JsonCreator
    public SolutionAttemptDto(@JsonProperty("answer") Integer[] answer) {
        this.answer = answer;
    }

    public Integer[] getAnswer() {
        return answer;
    }
}
