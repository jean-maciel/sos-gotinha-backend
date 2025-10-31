package org.infobio.sos_gotinha.repository;

import org.infobio.sos_gotinha.model.RegistroVacina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RegistroVacinaRepository extends JpaRepository<RegistroVacina, UUID> {
    // Busca todos os registros associados a um crianca_id
    List<RegistroVacina> findByCrianca_IdOrderByDataAplicacaoDesc(UUID criancaId);
}