package DTOs;

import Laboratorio.Peticion;

import java.util.List;

public class SucursalDTO {
    private int sucursalID;
    private String direccion;
    private int responsableMatricula;
    private List<Peticion> peticiones;

    public SucursalDTO(int sucursalID, String direccion, int responsableMatricula, List<Peticion> peticiones){
        this.sucursalID = sucursalID;
        this.direccion = direccion;
        this.responsableMatricula = responsableMatricula;
        this.peticiones = peticiones;
    }
}
