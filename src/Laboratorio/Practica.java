package Laboratorio;

import DTOs.PracticaDTO;


public class Practica {
    private int codigoPractica;
    private String nombrePractica;
    private IndiceReservado indiceReservado;
    private IndiceCritico indiceCritico;

    public Practica(int codigoPractica,  String nombrePractica, String valorCritico, Float lLimitCritico,Float hLCritico, String valorReservado, Float lLimitReservado, Float hLReservado) {
        this.codigoPractica = codigoPractica;
        this.nombrePractica= nombrePractica;
        this.indiceCritico = new IndiceCritico(valorCritico, lLimitCritico, hLCritico);
        this.indiceReservado = new IndiceReservado(valorReservado, lLimitReservado, hLReservado);
    }

    public int getCodigoPractica() {
        return codigoPractica;
    }
    public String getNombrePractica() {
        return nombrePractica;
    }

    public IndiceReservado getIndiceReservado() {
        return indiceReservado;
    }

    public IndiceCritico getIndiceCritico() {
        return indiceCritico;
    }

    public PracticaDTO toDTO(){
        PracticaDTO practicaDTO = new PracticaDTO(this.codigoPractica, this.nombrePractica ,this.indiceReservado.toDTO(), this.indiceCritico.toDTO());
        return practicaDTO;
    }
}

