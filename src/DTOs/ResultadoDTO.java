package DTOs;

import Laboratorio.Practica;

public class ResultadoDTO {
    private float valorResultado;
    private String descripcionResultado;
    private Practica practica;

    public ResultadoDTO(float valorResultado, String descripcionResultado, Practica practica){
        this.valorResultado = valorResultado;
        this.descripcionResultado = descripcionResultado;
        this.practica = practica;
    }
}
