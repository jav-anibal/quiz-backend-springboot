package org.javanibal.quiz.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.javanibal.quiz.enums.Categoria;

import java.util.ArrayList;
import java.util.List;



@Entity
@Table(name="quiz")

//Lombok
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    @NotNull(message = "La categoría es obligatoria")
    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Pregunta> preguntaList = new ArrayList<>();




}
