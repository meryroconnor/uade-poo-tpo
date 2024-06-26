package Laboratorio;

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
}
