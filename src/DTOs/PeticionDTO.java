package DTOs;

import Laboratorio.Practica;
import Laboratorio.Resultado;

import java.util.List;

public class PeticionDTO {
    private int peticionID;
    private List<Resultado> resultados;
    private List<Practica> practicas;

    public PeticionDTO(int peticionID, List<Resultado> resultados, List<Practica> practicas){
        this.peticionID = peticionID;
        this.resultados = resultados;
        this.practicas = practicas;
    }
}
