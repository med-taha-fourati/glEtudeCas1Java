package com.fsegs.genie_logiciel_etude_cas_1.Services;

import com.fsegs.genie_logiciel_etude_cas_1.Metier.Enseignant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class EnseignantService {
    private Enseignant enseignant;

    @Bean
    public int calculerM() {
        return enseignant.calculerM();
    }
}
