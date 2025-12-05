package com.fsegs.genie_logiciel_etude_cas_1.Metier.DTO;

public class CalculerMResponse {
    private int enseignantId;
    private int m;
    private int chargeSurveillance;
    
    public CalculerMResponse(int enseignantId, int m, int chargeSurveillance) {
        this.enseignantId = enseignantId;
        this.m = m;
        this.chargeSurveillance = chargeSurveillance;
    }
    
    public int getEnseignantId() {
        return enseignantId;
    }
    
    public void setEnseignantId(int enseignantId) {
        this.enseignantId = enseignantId;
    }
    
    public int getM() {
        return m;
    }
    
    public void setM(int m) {
        this.m = m;
    }

    public int getChargeSurveillance() {
        return chargeSurveillance;
    }

    public void setChargeSurveillance(int chargeSurveillance) {
        this.chargeSurveillance = chargeSurveillance;
    }
}
