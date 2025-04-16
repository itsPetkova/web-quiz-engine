package com.example.webquizengine.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Quiz must have a title!")
    private String title;

    @NotBlank(message = "Quiz must have text!")
    private String text;

    @NotNull(message = "Quiz must have options!")
    @Size(min = 2)
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> options;

    @ElementCollection
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Integer> answer;

    @ManyToOne(fetch = FetchType.EAGER)
    private User creator;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<CompletedQuiz> completedAttempts;
}
