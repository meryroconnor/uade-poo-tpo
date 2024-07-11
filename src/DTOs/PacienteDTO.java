package DTOs;

import java.util.List;

public class PacienteDTO {
    private int pacienteID;
    private String nombreApellido;
    private String DNI;
    private String sexo;
    private String email;
    private int edad;
    private String domicilio;
    private ObraSocialDTO obraSocialDTO;
    private List<PeticionDTO> peticionesDTO;


    public PacienteDTO(int pacienteID, String nombreApellido, String sexo, String DNI, String email, int edad, String domicilio, List<PeticionDTO> peticionesDTO, ObraSocialDTO obraSocialDTO) {
        this.pacienteID = pacienteID;
        this.nombreApellido = nombreApellido;
        this.sexo = sexo;
        this.DNI = DNI;
        //this.fechaNacimiento=fechaNacimiento;
        this.email= email;
        this.edad = edad;
        this.domicilio = domicilio;
        this.peticionesDTO = peticionesDTO;
        if(obraSocialDTO != null){
            this.obraSocialDTO = obraSocialDTO;
        }
    }

    public int getPacienteID() {
        return pacienteID;
    }

    public String getNombreApellido() {
        return nombreApellido;
    }

    public String getDNI() {
        return DNI;
    }

    public String getSexo() {
        return sexo;
    }

    public String getEmail() {
        return email;
    }

    public int getEdad() {
        return edad;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public ObraSocialDTO getObraSocialDTO() {
        return obraSocialDTO;
    }

    public List<PeticionDTO> getPeticionesDTO() {
        return peticionesDTO;
    }
}
