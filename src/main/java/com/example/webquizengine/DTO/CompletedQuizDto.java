package com.example.webquizengine.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class CompletedQuizDto {
    private long id;
    private OffsetDateTime completedAt;
}
