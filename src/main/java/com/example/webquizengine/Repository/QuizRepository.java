package com.example.webquizengine.Repository;

import com.example.webquizengine.Entities.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long>, PagingAndSortingRepository<Quiz, Long> {
    Page<Quiz> findAllBy(Pageable pageable);
}
