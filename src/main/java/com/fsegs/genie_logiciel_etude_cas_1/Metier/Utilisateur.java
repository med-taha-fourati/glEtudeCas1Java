package com.fsegs.genie_logiciel_etude_cas_1.Metier;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@MappedSuperclass
@Data
public abstract class Utilisateur {
    @Column(name="user_name")
    private String username;
    @Column(name="user_password")
    private String password;
}
