package DTOs;

import Laboratorio.IndiceReservado;

public class IndiceReservadoDTO {
    private String value;
    private Float lowLimit;
    private Float highLimit;

    public IndiceReservadoDTO(String value, Float lowLimit, Float highLimit){
        this.value = value;
        this.lowLimit = lowLimit;
        this.highLimit = highLimit;
    }
}
