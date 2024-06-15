package Laboratorio;

import java.util.ArrayList;
import java.util.List;

public class Paciente {
    private int pacienteID;
    private String nombreApellido;
    private List<Peticion> peticiones;

    // Constructor con modificador de acceso 'protected' para que solo el controlador pueda instanciarlo
    protected Paciente(int pacienteID, String nombreApellido) {
        this.pacienteID = pacienteID;
        this.nombreApellido = nombreApellido;
        this.peticiones = new ArrayList<>();
    }

    public int getPacienteID() {
        return pacienteID;
    }

    public String getNombreApellido() {
        return nombreApellido;
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


