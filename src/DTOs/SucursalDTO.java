package DTOs;

import java.util.List;

public class SucursalDTO {
    private int sucursalID;
    private String direccion;
    private int responsableMatricula;
    private List<PeticionDTO> peticionesDTO;

    public SucursalDTO(int sucursalID, String direccion, int responsableMatricula, List<PeticionDTO> peticionesDTO){
        this.sucursalID = sucursalID;
        this.direccion = direccion;
        this.responsableMatricula = responsableMatricula;
        this.peticionesDTO = peticionesDTO;
    }

    public int getSucursalID() {
        return sucursalID;
    }

    public String getDireccion() {
        return direccion;
    }

    public int getResponsableMatricula() {
        return responsableMatricula;
    }

    public List<PeticionDTO> getPeticionesDTO() {
        return peticionesDTO;
    }
}
