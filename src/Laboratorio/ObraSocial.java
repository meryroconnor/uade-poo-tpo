package Laboratorio;

import DTOs.ObraSocialDTO;

public class ObraSocial {
    private String obraSocial;
    private int numeroAfiliado;

    public ObraSocial(String obraSocial, int numeroAfiliado) {
        this.obraSocial = obraSocial;
        this.numeroAfiliado = numeroAfiliado;
    }

    public String getObraSocial() {
        return obraSocial;
    }

    public int getNumeroAfiliado() {
        return numeroAfiliado;
    }

    public ObraSocialDTO toDTO(){
        ObraSocialDTO obraSocialDTO = new ObraSocialDTO(this.obraSocial, this.numeroAfiliado);
        return obraSocialDTO;
    }
}
