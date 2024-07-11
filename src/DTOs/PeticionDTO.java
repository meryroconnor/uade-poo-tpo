package DTOs;

import java.time.LocalDate;
import java.util.List;

public class PeticionDTO {
    private int peticionID;
    private List<EstudioDTO> estudiosDTO;
    private LocalDate fechaCarga;

    public PeticionDTO(int peticionID, List<EstudioDTO> estudiosDTO, LocalDate fechaCarga){
        this.peticionID = peticionID;
        this.estudiosDTO = estudiosDTO;
        this.fechaCarga = fechaCarga;
    }

    public int getPeticionID() {
        return peticionID;
    }

    public List<EstudioDTO> getEstudiosDTO() {
        return estudiosDTO;
    }

    public LocalDate getFechaCarga() {
        return fechaCarga;
    }
}
