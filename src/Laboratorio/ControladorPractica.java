package Laboratorio;

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
    public Practica createPractica(String valorCritico, Float lLimitCritico,Float hLCritico, String valorReservado, Float lLimitReservado, Float hLReservado) {
        Practica practica = new Practica(nextCodigoPractica++, valorCritico, lLimitCritico, hLCritico, valorReservado, lLimitReservado, hLReservado);
        practicas.add(practica);
        return practica;
    }

    // Método para agregar un paciente existente (si se requiere)
    public void addPractica(Practica practica) {
        practicas.add(practica);
    }

    // Método para obtener la lista de practicas (opcional)
    public List<Practica> getPracticas() {
        return practicas;
    }
}


