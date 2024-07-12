package Laboratorio;

import DTOs.PeticionDTO;
import DTOs.SucursalDTO;

import java.util.ArrayList;
import java.util.List;

public class Sucursal {
    private int sucursalID;
    private String direccion;
    private int responsableMatricula;
    private List<Peticion> peticiones;

    public Sucursal(int sucursalID, String direccion, int responsableMatricula) {
        this.sucursalID = sucursalID;
        this.direccion = direccion;
        this.responsableMatricula = responsableMatricula;
        this.peticiones = new ArrayList<>();

    }

    public int getSucursalID() {
        return sucursalID;
    }

    public String getDireccion() {
        return direccion;
    }

    public int getResponsable() {
        return responsableMatricula;
    }

    public List<Peticion> getPeticiones() {
        return peticiones;
    }

    public void addPeticion(Peticion peticion) {
        peticiones.add(peticion);
    }
    public void removePeticion(Peticion peticion) {
        peticiones.remove(peticion);
    }
    public void setSucursalID(int sucursalID) {
        this.sucursalID = sucursalID;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setResponsableMatricula(int responsableMatricula) {
        this.responsableMatricula = responsableMatricula;
    }

    public boolean tieneResultadosFinalizados() {
        for (Peticion peticion : peticiones) {
            for (Estudio estudio : peticion.getEstudios()) {
                if (estudio.tieneResultado()) {
                    return true;
                }
            }
        }
        return false;
    }

    public SucursalDTO toDTO(){
        List<PeticionDTO> peticionesDTO = new ArrayList<>();

        for(Peticion peticion : this.peticiones){
            peticionesDTO.add(peticion.toDTO());
        }

        SucursalDTO sucursalDTO = new SucursalDTO(this.sucursalID, this.direccion, this.responsableMatricula, peticionesDTO);
        return sucursalDTO;
    }
}

