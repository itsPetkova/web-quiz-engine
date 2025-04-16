package com.example.webquizengine.Service.Impl;

import com.example.webquizengine.DTO.CompletedQuizDto;
import com.example.webquizengine.DTO.QuizDto;
import com.example.webquizengine.DTO.QuizResponseDto;
import com.example.webquizengine.Entities.CompletedQuiz;
import com.example.webquizengine.Entities.Quiz;
import com.example.webquizengine.Entities.User;
import com.example.webquizengine.Exceptions.QuizNotCreatedByUserException;
import com.example.webquizengine.Repository.QuizRepository;
import com.example.webquizengine.Repository.QuizSolutionRepository;
import com.example.webquizengine.Repository.UserRepository;
import com.example.webquizengine.Service.QuizService;
import com.example.webquizengine.Utils.SecurityUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final QuizSolutionRepository quizSolutionRepository;
    private final ModelMapper modelMapper;

    public QuizServiceImpl(QuizRepository quizRepository,
                           ModelMapper modelMapper,
                           UserRepository userRepository,
                           QuizSolutionRepository quizSolutionRepository) {

        this.quizRepository = quizRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.quizSolutionRepository = quizSolutionRepository;
    }

    @Override
    public Page<QuizDto> getQuizzes(int page) {
        Page<Quiz> quizzes = quizRepository.findAll(PageRequest.of(page, 10));
        return quizzes.map(quiz -> modelMapper.map(quiz, QuizDto.class));
    }

    @Override
    public Page<CompletedQuizDto> getCompletedQuizzes(int page) {
        User currentUser = getCurrentUser();

        Page<CompletedQuiz> userSolutions = quizSolutionRepository
                .findBySolvedBy(currentUser, (PageRequest.of(page, 10, Sort.by("completedAt").descending())));
        return userSolutions.map(solution -> modelMapper.map(solution, CompletedQuizDto.class));
    }

    @Override
    public QuizDto createQuiz(QuizDto quizDto) {
        User currentUser = getCurrentUser();

        Quiz quiz = modelMapper.map(quizDto, Quiz.class);
        quiz.setCreator(currentUser);
        Quiz savedQuiz = quizRepository.save(quiz);

        return modelMapper.map(savedQuiz, QuizDto.class);
    }

    @Override
    public QuizDto getQuizWithId(Long id) {
        Quiz found = getQuizById(id);
        return modelMapper.map(found, QuizDto.class);
    }

    @Override
    public QuizResponseDto solveQuiz(Long id, Integer[] answer) {
        Quiz quiz = getQuizById(id);
        User user = getCurrentUser();

        List<Integer> correctAnswers = quiz.getAnswer();

        CompletedQuiz completedQuiz = new CompletedQuiz();
        completedQuiz.setQuiz(quiz);
        completedQuiz.setSolvedBy(user);

        if (correctAnswers == null || correctAnswers.isEmpty() && answer.length == 0) {
            if (answer.length == 0) {
                quizSolutionRepository.save(completedQuiz);
                return new QuizResponseDto(true, "Congratulations, you're right!");
            }
        }

        Set<Integer> correctAnswerSet = new HashSet<>(correctAnswers);
        Set<Integer> userAnswerSet = new HashSet<>(Arrays.asList(answer));

        if (correctAnswerSet.equals(userAnswerSet)) {
            quizSolutionRepository.save(completedQuiz);
            return new QuizResponseDto(true, "Congratulations, you're right!");
        } else {
            return new QuizResponseDto(false, "Wrong answer! Please, try again.");
        }
    }

    @Override
    public void deleteQuizWithId(long Id) {
        Quiz quiz = getQuizById(Id);

        if (Objects.equals(quiz.getCreator().getId(), SecurityUtils.getCurrentUserId())) {
            quizRepository.delete(quiz);
        } else {
            throw new QuizNotCreatedByUserException("You do not have permission to delete this quiz");
        }
    }

    private Quiz getQuizById(long id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Quiz with id " + id + " not found"));
    }

    private User getCurrentUser() {
        return userRepository.findById(Objects.requireNonNull(SecurityUtils.getCurrentUserId()))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
