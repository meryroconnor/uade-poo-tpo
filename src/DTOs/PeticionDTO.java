package DTOs;

import java.util.List;

public class PeticionDTO {
    private int peticionID;
    private List<ResultadoDTO> resultadosDTO;
    private List<PracticaDTO> practicasDTO;

    public PeticionDTO(int peticionID, List<ResultadoDTO> resultadosDTO, List<PracticaDTO> practicasDTOs){
        this.peticionID = peticionID;
        this.resultadosDTO = resultadosDTO;
        this.practicasDTO = practicasDTOs;
    }

    public int getPeticionID() {
        return peticionID;
    }

    public List<PracticaDTO> getPracticasDTO() {
        return practicasDTO;
    }

    public List<ResultadoDTO> getResultadosDTO() {
        return resultadosDTO;
    }


}
