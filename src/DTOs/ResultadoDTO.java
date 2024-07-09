package DTOs;

import Laboratorio.Practica;

public class ResultadoDTO {
    private float valorResultado;
    private String descripcionResultado;

    public ResultadoDTO(float valorResultado, String descripcionResultado){
        this.valorResultado = valorResultado;
        this.descripcionResultado = descripcionResultado;
    }

    public float getValorResultado() {
        return valorResultado;
    }

    public String getDescripcionResultado() {
        return descripcionResultado;
    }
}
