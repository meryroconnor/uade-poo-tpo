package DTOs;

import java.util.List;

public class PeticionDTO {
    private int peticionID;
    private List<EstudioDTO> estudiosDTO;

    public PeticionDTO(int peticionID, List<EstudioDTO> estudiosDTO){
        this.peticionID = peticionID;
        this.estudiosDTO = estudiosDTO;
    }

    public int getPeticionID() {
        return peticionID;
    }

    public List<EstudioDTO> getEstudiosDTO() {
        return estudiosDTO;
    }



}
