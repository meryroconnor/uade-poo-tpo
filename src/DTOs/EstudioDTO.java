package DTOs;

import java.util.Date;


public class EstudioDTO {
    private int codigoEstudio;
    private Date fecha;
    private Date fechaEntregaAproximada;
    private ResultadoDTO resultadoDTO;
    private PracticaDTO practicaDTO;


    public EstudioDTO(int codigoEstudio, PracticaDTO practicaDTO, ResultadoDTO resultadoDTO){
        this.codigoEstudio = codigoEstudio;
        this.practicaDTO = practicaDTO;
        this.resultadoDTO = resultadoDTO;
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