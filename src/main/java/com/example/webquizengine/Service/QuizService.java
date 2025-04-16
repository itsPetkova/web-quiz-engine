package com.example.webquizengine.Service;

import com.example.webquizengine.DTO.CompletedQuizDto;
import com.example.webquizengine.DTO.QuizDto;
import com.example.webquizengine.DTO.QuizResponseDto;
import org.springframework.data.domain.Page;

public interface QuizService {
    public Page<QuizDto> getQuizzes(int page);

    public Page<CompletedQuizDto> getCompletedQuizzes(int page);

    public QuizDto createQuiz(QuizDto quizDto);

    public QuizDto getQuizWithId(Long id);

    public QuizResponseDto solveQuiz(Long id, Integer[] answer);

    void deleteQuizWithId(long Id);
}
