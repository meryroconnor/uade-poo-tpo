package Laboratorio;

import DTOs.EstudioDTO;
import DTOs.PeticionDTO;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;


public class Peticion {
    private int peticionID;
    private int nextCodigoEstudio;
    private List<Estudio> estudios;
    private LocalDate fechaCarga;
    public Peticion(int peticionID) {
        this.peticionID = peticionID;
        this.estudios = new ArrayList<>();
        this.nextCodigoEstudio=1;
        this.fechaCarga = LocalDate.now(); //metodo estatico
    }

    public int getPeticionID() {
        return peticionID;
    }
    public void getPeticionID(int peticionID) { this.peticionID= peticionID; }

    public List<Estudio> getEstudios() {
        return estudios;
    }

    public LocalDate getFechaCarga() {
        return fechaCarga;
    }

    public void addEstudio(Practica practica, float valorResultado, String descripcionResultado) {
        this.estudios.add(new Estudio(nextCodigoEstudio++, practica, valorResultado, descripcionResultado));
    }

    public PeticionDTO toDTO(){
        List<EstudioDTO> estudioDTOS = new ArrayList<>();

        for(Estudio estudio : this.estudios){
            estudioDTOS.add(estudio.toDTO());
        }

        PeticionDTO peticionDTO = new PeticionDTO(this.peticionID, estudioDTOS, this.fechaCarga);
        return peticionDTO;
    }
}


