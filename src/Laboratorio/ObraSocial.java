package Laboratorio;

import DTOs.ObraSocialDTO;

public class ObraSocial {
    private String obraSocial;
    private int numeroAfilicion;

    public ObraSocial(String obraSocial, int numeroAfilicion) {
        this.obraSocial = obraSocial;
        this.numeroAfilicion = numeroAfilicion;
    }

    public String getObraSocial() {
        return obraSocial;
    }

    public int getNumeroAfilicion() {
        return numeroAfilicion;
    }

    public ObraSocialDTO toDTO(){
        ObraSocialDTO obraSocialDTO = new ObraSocialDTO(this.obraSocial, this.numeroAfilicion);
        return obraSocialDTO;
    }
}
