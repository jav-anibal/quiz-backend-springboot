package org.javanibal.quiz.controller;


import jakarta.validation.Valid;
import org.javanibal.quiz.model.Quiz;
import org.javanibal.quiz.service.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
@CrossOrigin(origins = "*")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping
    public List<Quiz>getAll(){
        return quizService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quiz> getById(@PathVariable Integer id){
        return quizService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @PostMapping
    public Quiz create(@Valid @RequestBody Quiz quiz){
        return quizService.save(quiz);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Quiz> update(@PathVariable Integer id, @Valid @RequestBody Quiz quiz){
        return quizService.findById(id)
                .map(existing -> {
                    quiz.setId(id);
                return ResponseEntity.ok(quizService.save(quiz));
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        quizService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
