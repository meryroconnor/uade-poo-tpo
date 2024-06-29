package Laboratorio;

import DTOs.PeticionDTO;

import java.util.ArrayList;
import java.util.List;

public class Peticion {
    private int peticionID;
    private List<Resultado> resultados;
    private List<Practica> practicas;

    protected Peticion(int peticionID) {
        this.peticionID = peticionID;
        this.resultados = new ArrayList<>();
        this.practicas = new ArrayList<>();
    }

    public int getPeticionID() {
        return peticionID;
    }

    public List<Resultado> getResultados() {
        return resultados;
    }

    public void addResultado(float valorResultado, String descripcionResultado, Practica practica) {
        this.resultados.add(new Resultado(valorResultado, descripcionResultado, practica));
    }

    public List<Practica> getPracticas() {
        return practicas;
    }

    public void addPractica(Practica practica) {
        practicas.add(practica);
    }

    public boolean tieneResultados() {
        return !resultados.isEmpty();
    }

    public PeticionDTO toDTO(){
        PeticionDTO peticionDTO = new PeticionDTO(this.peticionID, this.resultados, this.practicas);
        return peticionDTO;
    }

}


