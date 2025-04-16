package com.example.webquizengine.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public class QuizDto {
    private Long id;

    @NotBlank(message = "Quiz must have a title!")
    private String title;

    @NotBlank(message = "Quiz must have text!")
    private String text;

    @NotNull(message = "Quiz must have options!")
    @Size(min = 2)
    private List<String> options;

    private Integer[] answer;

    public QuizDto() {}

    @JsonCreator
    public QuizDto(
            @JsonProperty("title") String title,
            @JsonProperty("text") String text,
            @JsonProperty("options") List<String> options,
            @JsonProperty("answer") Integer[] answer
    ) {
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public List<String> getOptions() {
        return options;
    }
    public void setOptions(List<String> options) {
        this.options = options;
    }

    @JsonIgnore
    public Integer[] getAnswer() {
        return answer;
    }
    public void setAnswer(Integer[] answer) {
        this.answer = answer;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
