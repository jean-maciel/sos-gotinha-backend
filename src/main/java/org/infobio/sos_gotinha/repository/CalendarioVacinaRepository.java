package org.infobio.sos_gotinha.repository;

import org.infobio.sos_gotinha.model.CalendarioVacina;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CalendarioVacinaRepository extends JpaRepository<CalendarioVacina, UUID> {}