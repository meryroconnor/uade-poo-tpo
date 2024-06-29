package DTOs;

import Laboratorio.Practica;

public class ResultadoDTO {
    private float valorResultado;
    private String descripcionResultado;
    private PracticaDTO practicaDTO;

    public ResultadoDTO(float valorResultado, String descripcionResultado, PracticaDTO practicaDTO){
        this.valorResultado = valorResultado;
        this.descripcionResultado = descripcionResultado;
        this.practicaDTO = practicaDTO;
    }
}
