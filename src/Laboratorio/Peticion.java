package Laboratorio;

import DTOs.EstudioDTO;
import DTOs.PeticionDTO;
import DTOs.PracticaDTO;
import DTOs.ResultadoDTO;

import java.util.ArrayList;
import java.util.List;

public class Peticion {
    private int peticionID;
    private int nextCodigoEstudio;
    private List<Estudio> estudios;

    public Peticion(int peticionID) {
        this.peticionID = peticionID;
        this.estudios = new ArrayList<>();
        this.nextCodigoEstudio=1;
    }

    public int getPeticionID() {
        return peticionID;
    }
    public void getPeticionID(int peticionID) { this.peticionID= peticionID; }

    public List<Estudio> getEstudios() {
        return estudios;
    }

    public void addEstudio(Practica practica, float valorResultado, String descripcionResultado) {
        this.estudios.add(new Estudio(nextCodigoEstudio++, practica, valorResultado, descripcionResultado));
    }

    public PeticionDTO toDTO(){
        List<EstudioDTO> estudioDTOS = new ArrayList<>();

        for(Estudio estudio : this.estudios){
            estudioDTOS.add(estudio.toDTO());
        }

        PeticionDTO peticionDTO = new PeticionDTO(this.peticionID, estudioDTOS);
        return peticionDTO;
    }
}


