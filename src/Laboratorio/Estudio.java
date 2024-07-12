package Laboratorio;

import DTOs.EstudioDTO;
import DTOs.PracticaDTO;
import DTOs.ResultadoDTO;

import java.time.LocalDateTime;

public class Estudio {
    private int codigoEstudio;
    private LocalDateTime fechaCarga;
    private LocalDateTime fechaEntregaEstimada;
    private Resultado resultado;
    private Practica practica;

    public Estudio(int codigoEstudio, Practica practica, float valorResultado, String descripcionResultado) {
        this.codigoEstudio = codigoEstudio;
        this.practica = practica; // tiene relacion de agregacion
        this.fechaCarga = LocalDateTime.now();
        this.fechaEntregaEstimada = calcularFechaEntregaAprox(practica);

        if (descripcionResultado != null || valorResultado != 0.0) {
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

    public LocalDateTime getFechaCarga() {
        return fechaCarga;
    }

    public LocalDateTime getFechaEntregaEstimada() {
        return fechaEntregaEstimada;
    }

    public void setResultado(float valorResultado, String descripcionResultado) {
        this.resultado = new Resultado(valorResultado, descripcionResultado);
    }

    public boolean tieneResultado() { return resultado!=null; }

    public boolean isResultadoCritico() {
        if ((this.resultado.getValorResultado() !=0 &&  this.resultado.getValorResultado() >= practica.getIndiceCritico().getLowLimit() &&
                this.resultado.getValorResultado() <= practica.getIndiceCritico().getHighLimit() ) ||
                (this.resultado.getDescripcionResultado() !=null &&
                this.resultado.getDescripcionResultado().equals(practica.getIndiceCritico().getValue()))) {
            return true;
        }
        return false;
    }

    public boolean isResultadoReservado() {
        IndiceReservado indiceReservado = practica.getIndiceReservado();

        if ((this.resultado.getValorResultado() !=0 && this.resultado.getValorResultado() >= indiceReservado.getLowLimit() &&
                this.resultado.getValorResultado() <= indiceReservado.getHighLimit()) ||
                (this.resultado.getDescripcionResultado() != null &&
                this.resultado.getDescripcionResultado().equals(indiceReservado.getValue()))) {
            return true;
        }
        return false;
    }

    private LocalDateTime calcularFechaEntregaAprox(Practica practica){
        int cantidadHorasDemora = practica.getCantidadHorasDemora();
        LocalDateTime fechaEntregaAprox = LocalDateTime.now().plusHours(cantidadHorasDemora);
        return fechaEntregaAprox;
    }

    public EstudioDTO toDTO(){
        PracticaDTO practicaDTO = practica.toDTO();
        ResultadoDTO resultadoDTO;

        if (resultado == null){
            resultadoDTO= new ResultadoDTO(0, null);
        } else {
            resultadoDTO= this.resultado.toDTO();
        }


        EstudioDTO estudioDTO = new EstudioDTO(this.codigoEstudio, practicaDTO, resultadoDTO, this.fechaCarga, this.fechaEntregaEstimada);
        return estudioDTO;
    }


}
