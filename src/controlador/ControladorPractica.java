package controlador;

import DTOs.PracticaDTO;
import Laboratorio.Practica;

import java.util.ArrayList;
import java.util.List;

public class ControladorPractica {
    private static ControladorPractica instance;
    private List<Practica> practicas;
    private int nextCodigoPractica;

    // Constructor privado para implementar el patrón Singleton
    private ControladorPractica() {
        this.practicas = new ArrayList<>();
        this.nextCodigoPractica = 1;
    }

    // Método para obtener la única instancia de ControladorPaciente (Singleton)
    public static ControladorPractica getInstance() {
        if (instance == null) {
            instance = new ControladorPractica();
        }
        return instance;
    }

    // Método para crear una nueva Practica
    public PracticaDTO createPractica(String valorCritico, Float lLimitCritico, Float hLCritico, String valorReservado, Float lLimitReservado, Float hLReservado) {
        Practica practica = new Practica(nextCodigoPractica++, valorCritico, lLimitCritico, hLCritico, valorReservado, lLimitReservado, hLReservado);
        practicas.add(practica);
        return practica.toDTO();
    }

    // Método para agregar un paciente existente (si se requiere)
    public void addPractica(Practica practica) {
        practicas.add(practica);
    }

    protected Practica findPractica(int codigoPractica){
        Practica practicaEncontrada = null;
        for (Practica practica : practicas){
            if (practica.getCodigoPractica() == codigoPractica){
                practicaEncontrada = practica;
                break;
            }
        }
        return practicaEncontrada;
    }

    // Método para obtener la lista de practicas (opcional)
    public List<PracticaDTO> getPracticas() {
        List<PracticaDTO> practicaDTOS = new ArrayList<>();
        for (Practica practica : practicas){
            practicaDTOS.add(practica.toDTO());
        }
        return practicaDTOS;
    }
}


