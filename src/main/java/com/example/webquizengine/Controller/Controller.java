package com.example.webquizengine.Controller;

import com.example.webquizengine.DTO.*;
import com.example.webquizengine.Service.QuizService;
import com.example.webquizengine.Service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api")
public class Controller {
    private final QuizService quizService;
    private final UserService userService;

    public Controller(QuizService service, UserService userService) {
        this.quizService = service;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserDto userDto) {
        userService.registerUser(userDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/quizzes")
    public Page<QuizDto> getQuizzes(@RequestParam("page") int page) {
        return quizService.getQuizzes(page);
    }

    @PostMapping("/quizzes")
    public QuizDto createQuiz(@Valid @RequestBody QuizDto quiz) {
        return quizService.createQuiz(quiz);
    }

    @GetMapping("/quizzes/completed")
    public Page<CompletedQuizDto> getCompletedQuizzes(@RequestParam("page") int page) {
        return quizService.getCompletedQuizzes(page);
    }

    @GetMapping("/quizzes/{id}")
    public ResponseEntity<QuizDto> getQuizWithId(@PathVariable("id") long id) {
        QuizDto quiz = quizService.getQuizWithId(id);
        return new ResponseEntity<>(quiz, HttpStatus.OK);
    }

    @DeleteMapping("/quizzes/{id}")
    public ResponseEntity<String> deleteQuizWithId(@Valid @Min(1) @PathVariable("id") long id) {
        quizService.deleteQuizWithId(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/quizzes/{id}/solve")
    public ResponseEntity<QuizResponseDto> solveQuiz(
            @Valid @PathVariable("id") @Min(1) long id,
            @Valid @RequestBody SolutionAttemptDto attempt) {

        QuizResponseDto response = quizService.solveQuiz(id, attempt.getAnswer());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
