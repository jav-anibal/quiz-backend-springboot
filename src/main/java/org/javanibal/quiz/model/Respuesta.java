package org.javanibal.quiz.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.javanibal.quiz.enums.Opcion;


@Entity
@Table(name = "respuesta")

//Lombok
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "El texto de la respuesta es obligatorio")
    private String texto;

    @NotNull(message = "La opción es obligatoria")
    @Enumerated(EnumType.STRING)
    private Opcion opcion;
    private boolean esCorrecta;

    @ManyToOne
    @JoinColumn(name = "pregunta_id", nullable = false)
    @JsonBackReference
    private Pregunta pregunta;

}
