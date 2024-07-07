package Laboratorio;

import DTOs.ObraSocialDTO;
import DTOs.PacienteDTO;
import DTOs.PeticionDTO;

import java.util.ArrayList;
import java.util.List;

public class Paciente {
    private int pacienteID;
    private String nombreApellido;
    private String DNI;
    private String sexo;
    private String email;
    private ObraSocial obraSocial;
    private List<Peticion> peticiones;

    public Paciente(int pacienteID, String nombreApellido, String sexo, String DNI, String email, String nombreObraSocial, int obraSocialID) {
        this.pacienteID = pacienteID;
        this.nombreApellido = nombreApellido;
        this.sexo = sexo;
        this.DNI = DNI;
        this.email= email;
        this.peticiones = new ArrayList<>();

        // Permitir que obraSocial sea nulo
        if (nombreObraSocial != null && obraSocialID != 0) {
            this.obraSocial = new ObraSocial(nombreObraSocial, obraSocialID);
        } else {
            this.obraSocial = null;
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
    public ObraSocial getObraSocial() {
        return obraSocial;
    }
    public List<Peticion> getPeticiones() {
        return peticiones;
    }

    //public void setObraSocial(ObraSocial obraSocial) { this.obraSocial = obraSocial;}
    public void setObraSocial(String nombreObraSocial, int obraSocialID) {
        if (nombreObraSocial != null && obraSocialID != 0) {
            this.obraSocial = new ObraSocial(nombreObraSocial, obraSocialID);
        } else {
            this.obraSocial = null;
        }
    }



    public void addPeticion(Peticion peticion) {
        peticiones.add(peticion);
    }

    public boolean tieneResultadosFinalizados() {
        for (Peticion peticion : peticiones) {
            if (peticion.tieneResultados()) {
                return true;
            }
        }
        return false;
    }

    public PacienteDTO toDTO() {
        // creo coleccion local de peticionesDTO
        List<PeticionDTO> peticionesDTO = new ArrayList<>();

        // por cada peticion dentro del propio array de peticiones (propias)
        //a la coleccion de peticionesDTO agregarle los DTO de cada peticion
        for(Peticion peticion : this.peticiones){
            peticionesDTO.add(peticion.toDTO());
        }

        // crear el DTO del paciente propio con los DTOs de cada peticion propia y el DTO de la obra social relacionada
        ObraSocialDTO obraSocialDTO;
        if (obraSocial == null){
            obraSocialDTO= new ObraSocialDTO(null, 0);
        } else {
            obraSocialDTO= this.obraSocial.toDTO();
        }
        PacienteDTO pacienteDTO = new PacienteDTO(this.pacienteID, this.nombreApellido, this.sexo, this.DNI, this.email, peticionesDTO, obraSocialDTO);
        return pacienteDTO;
    }
}





