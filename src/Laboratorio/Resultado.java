package Laboratorio;

import DTOs.ResultadoDTO;

public class Resultado {
    private float valorResultado;
    private String descripcionResultado;
    private Practica practica;

    protected Resultado(float valorResultado, String descripcionResultado, Practica practica) {
        this.valorResultado = valorResultado;
        this.descripcionResultado = descripcionResultado;
        this.practica= practica;
    }

    public float getValorResultado() {
        return valorResultado;
    }

    public String getDescripcionResultado() {
        return descripcionResultado;
    }

    public Practica getPractica() {
        return practica;
    }

    public boolean isResultadoCritico() {
        Practica practica = this.getPractica();
        if (this.getValorResultado() < practica.getIndiceCritico().getLowLimit() ||
                this.getValorResultado() > practica.getIndiceCritico().getHighLimit() ||
                this.getDescripcionResultado().equals(practica.getIndiceCritico().getValue())) {
            return true;
        }
        return false;
    }
    /*public boolean isResultadoReservado() {
        Practica practica = this.getPractica();
        if (this.getValorResultado() < practica.getIndiceReservado().getLowLimit() ||
                this.getValorResultado() > practica.getIndiceReservado().getHighLimit() ||
                this.getDescripcionResultado().equals(practica.getIndiceReservado().getValue())) {
            return true;
        }
        return false;
    }*/

    public boolean isResultadoReservado() {
        IndiceReservado indiceReservado = practica.getIndiceReservado();

        boolean isValorInRango = (indiceReservado.getLowLimit() != null && this.getValorResultado() >= indiceReservado.getLowLimit()) &&
                (indiceReservado.getHighLimit() != null && this.getValorResultado() <= indiceReservado.getHighLimit());

        boolean isValorReservadoIgual = indiceReservado.getValue() != null && this.getDescripcionResultado().equals(indiceReservado.getValue());

        return isValorInRango || isValorReservadoIgual;
    }

    public ResultadoDTO toDTO(){
        ResultadoDTO resultadoDTO = new ResultadoDTO(this.valorResultado, this.descripcionResultado, this.practica);
        return resultadoDTO;
    }
}


