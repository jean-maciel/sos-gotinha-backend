package org.infobio.sos_gotinha.repository;

import org.infobio.sos_gotinha.model.ProfissionalSaude;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
public interface ProfissionalSaudeRepository extends JpaRepository<ProfissionalSaude, UUID> {}