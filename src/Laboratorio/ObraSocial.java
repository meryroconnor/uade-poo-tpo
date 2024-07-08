package Laboratorio;

import DTOs.ObraSocialDTO;

public class ObraSocial {
    private int obraSocialID;
    private String obraSocial;


    public ObraSocial(String obraSocial, int obraSocialID) {
        this.obraSocial = obraSocial;
        this.obraSocialID = obraSocialID;
    }

    public String getObraSocial() {
        return obraSocial;
    }

    public int getObraSocialID() {
        return obraSocialID;
    }

    public ObraSocialDTO toDTO(){
        ObraSocialDTO obraSocialDTO = new ObraSocialDTO(this.obraSocial, this.obraSocialID);
        return obraSocialDTO;
    }
}
