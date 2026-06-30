package org.javanibal.quiz.controller;


import jakarta.validation.Valid;
import org.javanibal.quiz.model.Pregunta;
import org.javanibal.quiz.service.PreguntaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/preguntas")
@CrossOrigin(origins = "*")

public class PreguntaController {

    private final PreguntaService preguntaService;

    public PreguntaController(PreguntaService preguntaService) {
        this.preguntaService = preguntaService;
    }

    @GetMapping
    public List<Pregunta> getAll() {
        return preguntaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pregunta> getById(@PathVariable Integer id) {
        return preguntaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Pregunta create(@Valid @RequestBody Pregunta pregunta) {
        return preguntaService.save(pregunta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pregunta> update(@PathVariable Integer id, @Valid @RequestBody Pregunta pregunta) {
        return preguntaService.findById(id)
                .map(existing -> {
                    pregunta.setId(id);
                    return ResponseEntity.ok(preguntaService.save(pregunta));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        preguntaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
