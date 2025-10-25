package com.fsegs.genie_logiciel_etude_cas_1.Services;

import com.fsegs.genie_logiciel_etude_cas_1.Exceptions.Seance.SeancePasTrouveeException;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.DTO.SeanceDTO;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.Embeddables.EmbHoraire;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.Horaire;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.Seance;
import com.fsegs.genie_logiciel_etude_cas_1.Repertoires.HoraireRep;
import com.fsegs.genie_logiciel_etude_cas_1.Repertoires.SeanceRep;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SeanceService {
    private final SeanceRep seanceRep;
    private final HoraireRep horaireRep;

    public SeanceService(SeanceRep seanceRep, HoraireRep horaireRep) {
        this.seanceRep = seanceRep;
        this.horaireRep = horaireRep;
    }

    public int calculerN(Seance seance) {
        return seance.calculerN();
    }

    public List<Seance> getAllSeances() {
        return seanceRep.findAll();
    }

    public Optional<Seance> getSeanceById(int id) {
        return seanceRep.findById(id);
    }

    public Seance createSeance(SeanceDTO seanceDTO) {
        EmbHoraire embHoraire = new EmbHoraire(seanceDTO.horaireHDebut, seanceDTO.horaireHFin);

        Horaire horaire = horaireRep.findById(embHoraire)
                .orElseThrow(() -> new SeancePasTrouveeException("Horaire pas trouve avec hDebut: "
                        + seanceDTO.horaireHDebut + " et hFin: " + seanceDTO.horaireHFin));
        
        Seance seance = new Seance();
        seance.setHoraire(horaire);
        seance.setSeanceDate(LocalDate.of(seanceDTO.annee, seanceDTO.mois, seanceDTO.jour));

         
        seance.setN(calculerN(seance));

        return seanceRep.save(seance);
    }

    public Seance updateSeance(int id, SeanceDTO seanceDTO) {
         
        Seance seance = seanceRep.findById(id)
                .orElseThrow(() -> new SeancePasTrouveeException("Seance pas trouvee avec id: " + id));

         
        EmbHoraire embHoraire = new EmbHoraire(seanceDTO.horaireHDebut, seanceDTO.horaireHFin);

         
        Horaire horaire = horaireRep.findById(embHoraire)
                .orElseThrow(() -> new SeancePasTrouveeException("Horaire pas trouve avec hDebut: "
                        + seanceDTO.horaireHDebut + " et hFin: " + seanceDTO.horaireHFin));
        seance.setHoraire(horaire);
        seance.setSeanceDate(LocalDate.of(seanceDTO.annee, seanceDTO.mois, seanceDTO.jour));
         
        seance.setN(calculerN(seance));

        return seanceRep.save(seance);
    }

    public void deleteSeance(int id) {
        Seance seance = seanceRep.findById(id)
                .orElseThrow(() -> new SeancePasTrouveeException("Seance pas trouvee avec id: " + id));
        seanceRep.delete(seance);
    }

    public boolean existsById(int id) {
        return seanceRep.existsById(id);
    }
}