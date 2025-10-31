package org.infobio.sos_gotinha.repository;

import org.infobio.sos_gotinha.model.Responsavel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;
public interface ResponsavelRepository extends JpaRepository<Responsavel, UUID> {
    Optional<Responsavel> findByUser_Id(UUID userId);
}