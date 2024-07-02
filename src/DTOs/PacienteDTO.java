package DTOs;

import java.util.Date;
import java.util.List;

public class PacienteDTO {
    private int pacienteID;
    private String nombreApellido;
    private String DNI;
    private String sexo;
    private Date fechaNacimiento;
    private String email;
    private ObraSocialDTO obraSocialDTO;
    private List<PeticionDTO> peticionesDTO;


    public PacienteDTO(int pacienteID, String nombreApellido, String sexo, String DNI, String email, List<PeticionDTO> peticionesDTO, ObraSocialDTO obraSocialDTO) {
        this.pacienteID = pacienteID;
        this.nombreApellido = nombreApellido;
        this.sexo = sexo;
        this.DNI = DNI;
        //this.fechaNacimiento=fechaNacimiento;
        this.email= email;
        this.peticionesDTO = peticionesDTO;
        if(obraSocialDTO != null){
            this.obraSocialDTO = obraSocialDTO;
        }
    }

    public int getPacienteID() {
        return pacienteID;
    }
}
