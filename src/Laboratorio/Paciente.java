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
    private int edad;
    private String domicilio;
    private ObraSocial obraSocial;
    private List<Peticion> peticiones;

    public Paciente(int pacienteID, String nombreApellido, String sexo, String DNI, String email, int edad, String domicilio, String nombreObraSocial, int obraSocialID) {
        this.pacienteID = pacienteID;
        this.nombreApellido = nombreApellido;
        this.sexo = sexo;
        this.DNI = DNI;
        this.email= email;
        this.edad = edad;
        this.domicilio = domicilio;
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
    public int getEdad() {
        return edad;
    }
    public String getDomicilio() {
        return domicilio;
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

    public void setNombreApellido(String nombreApellido) {
        this.nombreApellido = nombreApellido;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public void setObraSocialObject(ObraSocial obraSocial) {
        this.obraSocial = obraSocial;
    }

    public void addPeticion(Peticion peticion) {
        peticiones.add(peticion);
    }

    public void removePeticion(Peticion peticion) {
        peticiones.remove(peticion);
    }
    public boolean tieneResultadosFinalizados() {
        for (Peticion peticion : peticiones) {
            for (Estudio estudio : peticion.getEstudios()){
                if (estudio.tieneResultado()) {
                    return true;
                }
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
        PacienteDTO pacienteDTO = new PacienteDTO(this.pacienteID, this.nombreApellido, this.sexo, this.DNI, this.email, this.edad, this.domicilio, peticionesDTO, obraSocialDTO);
        return pacienteDTO;
    }
}





