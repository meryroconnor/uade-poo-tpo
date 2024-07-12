package Laboratorio;

import DTOs.EstudioDTO;
import DTOs.PeticionDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Peticion {
    private int peticionID;
    private int nextCodigoEstudio;
    private List<Estudio> estudios;
    private LocalDateTime fechaCarga;
    private LocalDateTime fechaAproxTerminacion;
    public Peticion(int peticionID) {
        this.peticionID = peticionID;
        this.estudios = new ArrayList<>();
        this.nextCodigoEstudio=1;
        this.fechaCarga = LocalDateTime.now(); //metodo estatico
    }

    public int getPeticionID() {
        return peticionID;
    }
    public void getPeticionID(int peticionID) { this.peticionID= peticionID; }

    public List<Estudio> getEstudios() {
        return estudios;
    }

    public LocalDateTime getFechaCarga() {
        return fechaCarga;
    }

    public void addEstudio(Practica practica, float valorResultado, String descripcionResultado) {
        this.estudios.add(new Estudio(nextCodigoEstudio++, practica, valorResultado, descripcionResultado));
        this.setFechaAproxTerminacion();
    }

    public void removeEstudio(int codigoEstudio){
        for (Estudio estudio : estudios){
            if (estudio.getCodigoEstudio() == codigoEstudio){
                this.estudios.remove(estudio);
                break;
            }
        }
    }

    public LocalDateTime getFechaAproxTerminacion() {
        return fechaAproxTerminacion;
    }

    private void setFechaAproxTerminacion() {
        LocalDateTime fechaTerminacion = estudios.get(0).getFechaEntregaEstimada(); //agarro la primera asi no queda null
        for (Estudio estudio : estudios){
            LocalDateTime fechaTerminacionSingular = estudio.getFechaEntregaEstimada(); //busco la que mas tarde
            if (fechaTerminacionSingular.isAfter(fechaTerminacion)){
                fechaTerminacion = fechaTerminacionSingular; // si encuentro una que sea mas tarde, reasigno y vuelvo a comparar
            }
        }
        this.fechaAproxTerminacion = fechaTerminacion; // la ultima fecha que quede sera el estudio que mas tiempo tarde, por lo que sera cuando la peticion finalice
    }

    public PeticionDTO toDTO(){
        List<EstudioDTO> estudioDTOS = new ArrayList<>();

        for(Estudio estudio : this.estudios){
            estudioDTOS.add(estudio.toDTO());
        }

        PeticionDTO peticionDTO = new PeticionDTO(this.peticionID, estudioDTOS, this.fechaCarga, this.fechaAproxTerminacion);
        return peticionDTO;
    }
}


