package com.fsegs.genie_logiciel_etude_cas_1.Metier.DTO;

public class CalculerNResponse {
    private int seanceId;
    private int n;
    
    public CalculerNResponse(int seanceId, int n) {
        this.seanceId = seanceId;
        this.n = n;
    }
    
    public int getSeanceId() {
        return seanceId;
    }
    
    public void setSeanceId(int seanceId) {
        this.seanceId = seanceId;
    }
    
    public int getN() {
        return n;
    }
    
    public void setN(int n) {
        this.n = n;
    }
}
