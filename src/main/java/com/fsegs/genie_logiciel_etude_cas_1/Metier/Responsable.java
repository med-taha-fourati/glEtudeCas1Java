package com.fsegs.genie_logiciel_etude_cas_1.Metier;

import com.fsegs.genie_logiciel_etude_cas_1.Metier.Enumerations.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Responsable extends Utilisateur {
    //TODO
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;
    private String prenom;

    public Responsable() {
        this.setRole(Role.ADMIN);
    }

    //TODO: add setResponsable in responsable service
}
