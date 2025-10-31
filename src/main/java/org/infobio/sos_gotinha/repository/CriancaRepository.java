package org.infobio.sos_gotinha.repository;

import org.infobio.sos_gotinha.model.Crianca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CriancaRepository extends JpaRepository<Crianca, UUID> {
    List<Crianca> findByResponsaveis_User_Id(UUID userId);
}