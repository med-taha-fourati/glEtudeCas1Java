package com.fsegs.genie_logiciel_etude_cas_1.Metier;

import lombok.Data;

@Data
public abstract class Utilisateur {
    private String username;
    private String password;
}
