package DTOs;

import Laboratorio.ObraSocial;
import Laboratorio.Peticion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PacienteDTO {
    private int pacienteID;
    private String nombreApellido;
    private String DNI;
    private String sexo;
    private Date fechaNacimiento;
    private String email;
    private ObraSocial obraSocial;
    private List<Peticion> peticiones;


    protected PacienteDTO(int pacienteID, String nombreApellido, String sexo, String DNI, String email) {
        this.pacienteID = pacienteID;
        this.nombreApellido = nombreApellido;
        this.sexo = sexo;
        this.DNI = DNI;
        //this.fechaNacimiento=fechaNacimiento;
        this.email= email;
        this.peticiones = new ArrayList<>();
    }
}
