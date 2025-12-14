package com.fsegs.genie_logiciel_etude_cas_1.Services;

import com.fsegs.genie_logiciel_etude_cas_1.Metier.Embeddables.EmbHoraire;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.Horaire;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.DTO.HoraireDTO;
import com.fsegs.genie_logiciel_etude_cas_1.Repertoires.HoraireRep;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HoraireService {
    private final HoraireRep horaireRep;
    public HoraireService(HoraireRep horaireRep) {
        this.horaireRep = horaireRep;
    }

    public ArrayList<Horaire> getAllHoraires() {
        return (ArrayList<Horaire>) horaireRep.findAll();
    }

    public Optional<Horaire> getHoraireById(int hDebut, int hFin) {
        EmbHoraire id = new EmbHoraire(hDebut, hFin);
        return horaireRep.findById(id);
    }

    public Horaire createHoraire(HoraireDTO horaireDTO) {
        Horaire horaire = new Horaire();
        if (!horaire.twoHourDurationCheck(horaireDTO.hDebut, horaireDTO.hFin)) {
            throw new IllegalArgumentException("Horaires doivent avoir une duration de 2 heures");
        }
        EmbHoraire embHoraire = new EmbHoraire(horaireDTO.hDebut, horaireDTO.hFin);
        horaire.setEmbHoraire(embHoraire);
        return horaireRep.save(horaire);
    }

    public Horaire updateHoraire(int oldHDebut, int oldHFin, HoraireDTO horaireDTO) {
        EmbHoraire oldId = new EmbHoraire(oldHDebut, oldHFin);

        Horaire horaire = horaireRep.findById(oldId)
                .orElseThrow(() -> new RuntimeException("Horaire not found"));

        // changement du id
        if (oldHDebut != horaireDTO.hDebut || oldHFin != horaireDTO.hFin) {
            horaireRep.delete(horaire);
            Horaire newHoraire = new Horaire();
            newHoraire.setEmbHoraire(new EmbHoraire(horaireDTO.hDebut, horaireDTO.hFin));
            return horaireRep.save(newHoraire);
        }

        return horaire;
    }

    public void deleteHoraire(int hDebut, int hFin) {
        EmbHoraire id = new EmbHoraire(hDebut, hFin);
        horaireRep.deleteById(id);
    }
}
