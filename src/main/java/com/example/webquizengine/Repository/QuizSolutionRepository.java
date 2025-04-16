package com.example.webquizengine.Repository;

import com.example.webquizengine.Entities.CompletedQuiz;
import com.example.webquizengine.Entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface QuizSolutionRepository extends JpaRepository<CompletedQuiz, Long>, PagingAndSortingRepository<CompletedQuiz, Long> {
    Page<CompletedQuiz> findBySolvedBy(User solvedBy, Pageable pageable);
}
