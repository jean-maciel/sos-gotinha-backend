package org.infobio.sos_gotinha.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "criancas")
public class Crianca {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "nome_completo", nullable = false)
    private String nomeCompleto;

    @Column(unique = true)
    private String cpf;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "numero_sus", unique = true)
    private String numeroSus;

    @ManyToMany
    @JoinTable(
            name = "crianca_responsavel_link",
            joinColumns = @JoinColumn(name = "crianca_id"),
            inverseJoinColumns = @JoinColumn(name = "responsavel_id")
    )
    private Set<Responsavel> responsaveis;

    @OneToMany(mappedBy = "crianca", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RegistroVacina> historicoVacinal;
}