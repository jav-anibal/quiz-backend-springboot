package org.javanibal.quiz.controller;

import jakarta.validation.Valid;
import org.javanibal.quiz.model.Respuesta;
import org.javanibal.quiz.service.RespuestaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/respuestas")
@CrossOrigin(origins = "*")
public class RespuestaController {


    private final RespuestaService respuestaService;

    public RespuestaController(RespuestaService respuestaService) {
        this.respuestaService = respuestaService;
    }

    @GetMapping
    public List<Respuesta> getAll() {
        return respuestaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Respuesta> getById(@PathVariable Integer id) {
        return respuestaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Respuesta create(@Valid @RequestBody Respuesta respuesta) {
        return respuestaService.save(respuesta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Respuesta> update(@PathVariable Integer id, @Valid @RequestBody Respuesta respuesta) {
        return respuestaService.findById(id)
                .map(existing -> {
                    respuesta.setId(id);
                    return ResponseEntity.ok(respuestaService.save(respuesta));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        respuestaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
