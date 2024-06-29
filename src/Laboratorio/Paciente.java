package Laboratorio;

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

    // Constructor con modificador de acceso 'protected' para que solo el controlador pueda instanciarlo
    public Paciente(int pacienteID, String nombreApellido, String sexo, String DNI, String email) {
        this.pacienteID = pacienteID;
        this.nombreApellido = nombreApellido;
        this.sexo = sexo;
        this.DNI = DNI;
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
}


