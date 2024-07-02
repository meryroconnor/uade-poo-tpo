package DTOs;

public class ObraSocialDTO {
    private int numeroAfiliado;
    private String obraSocial;


    public ObraSocialDTO(String obraSocial, int numeroAfiliado){
        this.numeroAfiliado = numeroAfiliado;
        this.obraSocial = obraSocial;
    }

    public int getNumeroAfiliado() {
        return numeroAfiliado;
    }
}
