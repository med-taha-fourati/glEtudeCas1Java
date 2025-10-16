package com.fsegs.genie_logiciel_etude_cas_1.Services;

import com.fsegs.genie_logiciel_etude_cas_1.Metier.Seance;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class SeanceService {
    private Seance seance;

    @Bean
    public int calculerN() {
        return seance.calculerN();
    }
}
