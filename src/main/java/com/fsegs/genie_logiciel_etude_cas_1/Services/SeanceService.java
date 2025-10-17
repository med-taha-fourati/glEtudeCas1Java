package com.fsegs.genie_logiciel_etude_cas_1.Services;

import com.fsegs.genie_logiciel_etude_cas_1.Metier.Seance;
import com.fsegs.genie_logiciel_etude_cas_1.Repertoires.SeanceRep;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class SeanceService {
    private SeanceRep seanceRep;
    public SeanceService(SeanceRep seanceRep) {
        this.seanceRep = seanceRep;
    }

    @Bean
    public int calculerN() {
        Seance seance = new Seance();
        return seance.calculerN();
    }
}
