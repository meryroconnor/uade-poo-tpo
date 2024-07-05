package DTOs;

public class ObraSocialDTO {
    private int obraSocialID;
    private String obraSocial;


    public ObraSocialDTO(String obraSocial, int obraSocialID){
        this.obraSocialID = obraSocialID;
        this.obraSocial = obraSocial;
    }

    public int getObraSocialID() {
        return obraSocialID;
    }

    public String getObraSocial() {
        return obraSocial;
    }
}

