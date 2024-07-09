package Laboratorio;

import DTOs.EstudioDTO;
import DTOs.ObraSocialDTO;
import DTOs.PracticaDTO;
import DTOs.ResultadoDTO;

import java.util.Date;

public class Estudio {
    private int codigoEstudio;
    private Date fecha;
    private Date fechaEntregaAproximada;
    private Resultado resultado;
    private Practica practica;

    public Estudio(int codigoEstudio, Practica practica, float valorResultado, String descripcionResultado) {
        this.codigoEstudio = codigoEstudio;
        this.practica = practica; // tiene relacion de agregacion

        if (descripcionResultado != null && valorResultado != 0) {
            this.resultado = new Resultado(valorResultado,descripcionResultado);
        } else {
            this.resultado = null;
        }
    }

    public Practica getPractica() {
        return practica;
    }

    public void setPractica(Practica practica) {
        this.practica = practica;
    }

    public int getCodigoEstudio() {
        return codigoEstudio;
    }

    public void setCodigoEstudio(int codigoEstudio) {
        this.codigoEstudio = codigoEstudio;
    }

    public Resultado getResultado(){ return resultado;}

    public void setResultado(float valorResultado, String descripcionResultado) {
        this.resultado = new Resultado(valorResultado, descripcionResultado);
    }

    public boolean tieneResultado() { return resultado!=null; }

    public boolean isResultadoCritico() {
        if ((this.resultado.getValorResultado() >= practica.getIndiceCritico().getLowLimit() &&
                this.resultado.getValorResultado() <= practica.getIndiceCritico().getHighLimit() ) ||
                this.resultado.getDescripcionResultado().equals(practica.getIndiceCritico().getValue())) {
            return true;
        }
        return false;
    }

    public boolean isResultadoReservado() {
        IndiceReservado indiceReservado = practica.getIndiceReservado();

        if ((this.resultado.getValorResultado() >= indiceReservado.getLowLimit() &&
                this.resultado.getValorResultado() <= indiceReservado.getHighLimit()) ||
                this.resultado.getDescripcionResultado().equals(indiceReservado.getValue())) {
            return true;
        }
        return false;
    }


    public EstudioDTO toDTO(){
        PracticaDTO practicaDTO = practica.toDTO();
        ResultadoDTO resultadoDTO;

        if (resultado == null){
            resultadoDTO= new ResultadoDTO(0, null);
        } else {
            resultadoDTO= this.resultado.toDTO();
        }


        EstudioDTO estudioDTO = new EstudioDTO(this.codigoEstudio, practicaDTO, resultadoDTO);
        return estudioDTO;
    }


}
