package com.fsegs.genie_logiciel_etude_cas_1.Services;

import com.fsegs.genie_logiciel_etude_cas_1.Metier.Enseignant;
import com.fsegs.genie_logiciel_etude_cas_1.Repertoires.EnseignantRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class EnseignantService {
    private final EnseignantRep enseignantRep;
    public EnseignantService(EnseignantRep enseignantRep) {
        this.enseignantRep = enseignantRep;
    }

    @Bean
    public int calculerM() {
        Enseignant enseignant = new Enseignant();
        return enseignant.calculerM();
    }
}
