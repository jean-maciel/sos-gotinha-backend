package org.infobio.sos_gotinha.service;

import org.infobio.sos_gotinha.model.CalendarioVacina;
import org.infobio.sos_gotinha.repository.CalendarioVacinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CalendarioVacinaService {

    private final CalendarioVacinaRepository calendarioVacinaRepository;

    @Autowired
    public CalendarioVacinaService(CalendarioVacinaRepository calendarioVacinaRepository) {
        this.calendarioVacinaRepository = calendarioVacinaRepository;
    }

    public List<CalendarioVacina> findAll() {
        return calendarioVacinaRepository.findAll();
    }

    public CalendarioVacina findById(UUID id) {
        return calendarioVacinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vacina do calendário não encontrada com o id: " + id));
    }

    public CalendarioVacina save(CalendarioVacina calendarioVacina) {
        return calendarioVacinaRepository.save(calendarioVacina);
    }

    public void deleteById(UUID id) {
        if (!calendarioVacinaRepository.existsById(id)) {
            throw new RuntimeException("Vacina do calendário não encontrada com o id: " + id);
        }
        calendarioVacinaRepository.deleteById(id);
    }
}
