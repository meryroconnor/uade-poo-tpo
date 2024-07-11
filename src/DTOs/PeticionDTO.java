package DTOs;

import java.time.LocalDateTime;
import java.util.List;

public class PeticionDTO {
    private int peticionID;
    private List<EstudioDTO> estudiosDTO;
    private LocalDateTime fechaCarga;
    private LocalDateTime fechaTerminacionEstimada;

    public PeticionDTO(int peticionID, List<EstudioDTO> estudiosDTO, LocalDateTime fechaCarga, LocalDateTime fechaTerminacionEstimada){
        this.peticionID = peticionID;
        this.estudiosDTO = estudiosDTO;
        this.fechaCarga = fechaCarga;
        this.fechaTerminacionEstimada = fechaTerminacionEstimada;
    }

    public int getPeticionID() {
        return peticionID;
    }

    public List<EstudioDTO> getEstudiosDTO() {
        return estudiosDTO;
    }

    public LocalDateTime getFechaCarga() {
        return fechaCarga;
    }

    public LocalDateTime getFechaTerminacionEstimada() {
        return fechaTerminacionEstimada;
    }
}
