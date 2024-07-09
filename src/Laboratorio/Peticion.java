package Laboratorio;

import DTOs.EstudioDTO;
import DTOs.PeticionDTO;
import DTOs.PracticaDTO;
import DTOs.ResultadoDTO;

import java.util.ArrayList;
import java.util.List;

public class Peticion {
    private int peticionID;
    //private List<Resultado> resultados;
    //private List<Practica> practicas;
    private List<Estudio> estudios;

    public Peticion(int peticionID) {
        this.peticionID = peticionID;
        this.estudios = new ArrayList<>();
    }

    public int getPeticionID() {
        return peticionID;
    }
    public void getPeticionID(int peticionID) { this.peticionID= peticionID; }

    public List<Estudio> getEstudios() {
        return estudios;
    }

    public void addEstudio(Estudio estudio) {
        this.estudios.add(estudio);
    }

//    public List<Resultado> getResultados() {
//        return resultados;
//    }
//
//    public void addResultado(float valorResultado, String descripcionResultado, Practica practica) {
//        this.resultados.add(new Resultado(valorResultado, descripcionResultado, practica));
//    }

//    public List<Practica> getPracticas() {
//        return practicas;
//    }
//
//    public void addPractica(Practica practica) {
//        practicas.add(practica);
//    }
//
//    public boolean tieneResultados() {
//        return !resultados.isEmpty();
//    }

    public PeticionDTO toDTO(){
        List<EstudioDTO> estudioDTOS = new ArrayList<>();

        for(Estudio estudio : this.estudios){
            estudioDTOS.add(estudio.toDTO());
        }

        PeticionDTO peticionDTO = new PeticionDTO(this.peticionID, estudioDTOS);
        return peticionDTO;
    }



}


