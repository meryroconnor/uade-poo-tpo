package Laboratorio;

import DTOs.PacienteDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Paciente {
    private int pacienteID;
    private String nombreApellido;
    private String DNI;
    private String sexo;
    private Date fechaNacimiento;
    private String email;
    private ObraSocial obraSocial;
    private List<Peticion> peticiones;

    // Constructor con modificador de acceso 'protected' para que solo el controlador pueda instanciarlo
    protected Paciente(int pacienteID, String nombreApellido, String sexo, String DNI, String email) {
        this.pacienteID = pacienteID;
        this.nombreApellido = nombreApellido;
        this.sexo = sexo;
        this.DNI = DNI;
        //this.fechaNacimiento=fechaNacimiento;
        this.email= email;
        this.peticiones = new ArrayList<>();
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

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getEmail() {
        return email;
    }

    public void setObraSocial(ObraSocial obraSocial) {
        this.obraSocial = obraSocial;
    }

    public ObraSocial getObraSocial() {
        return obraSocial;
    }

    public List<Peticion> getPeticiones() {
        return peticiones;
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
        PacienteDTO pacienteDTO = new PacienteDTO(this.pacienteID, this.nombreApellido, this.sexo, this.DNI, this.email, this.peticiones);
        return pacienteDTO;
    }
}





