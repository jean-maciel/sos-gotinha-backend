package org.infobio.sos_gotinha.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "registros_vacinas")
public class RegistroVacina {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crianca_id", nullable = false)
    private Crianca crianca;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id") // ON DELETE SET NULL
    private ProfissionalSaude profissional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendario_vacina_id", nullable = false)
    private CalendarioVacina calendarioVacina;

    @Column(name = "data_aplicacao", nullable = false)
    private LocalDate dataAplicacao;

    @Column(name = "local_aplicacao")
    private String localAplicacao;

    @Column(name = "lote_vacina")
    private String loteVacina;

    @Column(name = "data_criacao", insertable = false, updatable = false)
    private OffsetDateTime dataCriacao;
}