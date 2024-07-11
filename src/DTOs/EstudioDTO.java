package DTOs;

import java.time.LocalDateTime;


public class EstudioDTO {
    private int codigoEstudio;
    private LocalDateTime fechaCarga;
    private LocalDateTime fechaEntregaEstimada;
    private ResultadoDTO resultadoDTO;
    private PracticaDTO practicaDTO;


    public EstudioDTO(int codigoEstudio, PracticaDTO practicaDTO, ResultadoDTO resultadoDTO, LocalDateTime fechaCarga, LocalDateTime fechaEntregaEstimada){
        this.codigoEstudio = codigoEstudio;
        this.practicaDTO = practicaDTO;
        this.resultadoDTO = resultadoDTO;
        this.fechaCarga = fechaCarga;
        this.fechaEntregaEstimada = fechaEntregaEstimada;
    }

    public int getCodigoEstudio() {
        return codigoEstudio;
    }

    public ResultadoDTO getResultadoDTO() {
        return resultadoDTO;
    }

    public PracticaDTO getPracticaDTO() {
        return practicaDTO;
    }
}