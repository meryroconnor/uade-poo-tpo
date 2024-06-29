package Laboratorio;

import DTOs.IndiceReservadoDTO;

public class IndiceReservado {
    private String value;
    private Float lowLimit;
    private Float highLimit;

    protected IndiceReservado(String value, Float lowLimit, Float highLimit) {
        this.value = value;
        this.lowLimit = lowLimit;
        this.highLimit = highLimit;
    }

    public String getValue() {
        return value;
    }

    public Float getLowLimit() {
        return lowLimit;
    }

    public Float getHighLimit() {
        return highLimit;
    }

    public IndiceReservadoDTO toDTO(){
        IndiceReservadoDTO indiceReservadoDTO = new IndiceReservadoDTO(this.value, this.lowLimit, this.highLimit);
        return indiceReservadoDTO;
    }
}
