package org.infobio.sos_gotinha.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "calendario_vacinas")
public class CalendarioVacina {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "nome_vacina", nullable = false)
    private String nomeVacina;

    @Column(nullable = false)
    private String dose;

    @Column(name = "idade_recomendada_meses", nullable = false)
    private Integer idadeRecomendadaMeses;

    @Column
    private String descricao;
}