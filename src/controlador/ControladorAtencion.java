package controlador;

import Laboratorio.Peticion;
import Laboratorio.Resultado;
import Laboratorio.Sucursal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ControladorAtencion {
    private static ControladorAtencion instance;
    private List<Peticion> peticiones;
    private List<Sucursal> sucursales;
    private int nextPeticionID;
    private int nextSucursalID;

    // Constructor privado para implementar el patrón Singleton
    private ControladorAtencion() {
        this.peticiones = new ArrayList<>();
        this.sucursales = new ArrayList<>();
        this.nextPeticionID = 1;
        this.nextSucursalID = 1;
    }

    // Método para obtener la única instancia de ControladorAtencion
    public static ControladorAtencion getInstance() {
        if (instance == null) {
            instance = new ControladorAtencion();
        }
        return instance;
    }

    // Método para crear una nueva Peticion
    public Peticion createPeticion() {
        Peticion peticion = new Peticion(nextPeticionID++);
        peticiones.add(peticion);
        return peticion;
    }

    // Método para crear una nueva Sucursal
    public Sucursal createSucursal(String direccion, int responsable) {
        Sucursal sucursal = new Sucursal(nextSucursalID++, direccion, responsable);
        sucursales.add(sucursal);
        return sucursal;
    }

    // Método para eliminar una sucursal por su ID
    public void deleteSucursal(int sucursalID) {
        Sucursal sucursalAEliminar = null;
        for (Sucursal sucursal : sucursales) {
            if (sucursal.getSucursalID() == sucursalID) {
                sucursalAEliminar = sucursal;
                break;
            }
        }

        if (sucursalAEliminar != null) {
            System.out.println("Sucursal encontrada: "+sucursalAEliminar.getDireccion());
            List<Peticion> peticionesActivas = getPeticionesActivas(sucursalAEliminar);

            if (peticionesActivas.isEmpty()) {
                // No tiene peticiones activas, eliminar sucursal directamente
                sucursales.remove(sucursalAEliminar);
                System.out.println("*** Sucursal eliminada. ***");
            } else if (sucursales.size() > 1) {
                // Tiene peticiones activas y hay otras sucursales disponibles
                transferirPeticionesActivas(sucursalAEliminar, peticionesActivas);
                sucursales.remove(sucursalAEliminar);
                System.out.println("*** Sucursal eliminada. ***");
            } else {
                System.out.println("No se puede eliminar la sucursal. No hay otras sucursales disponibles para transferir las peticiones activas.");
            }
        } else {
            System.out.println("Sucursal no encontrada.");
        }
    }

    // Método para obtener las peticiones activas de una sucursal
    private List<Peticion> getPeticionesActivas(Sucursal sucursal) {
        List<Peticion> peticionesActivas = new ArrayList<>();
        for (Peticion peticion : sucursal.getPeticiones()) {
            if (peticion.tieneResultados()) {
                peticionesActivas.add(peticion);
            }
        }
        return peticionesActivas;
    }

    // Método para transferir peticiones activas a otra sucursal aleatoria
    // Método para transferir peticiones activas a otra sucursal aleatoria
    private void transferirPeticionesActivas(Sucursal sucursalAEliminar, List<Peticion> peticionesActivas) {
        Sucursal sucursalDestino = getRandomSucursal(sucursalAEliminar);
        for (Peticion peticion : peticionesActivas) {
            sucursalAEliminar.getPeticiones().remove(peticion);
            sucursalDestino.addPeticion(peticion);
            System.out.println(">> Peticion " + peticion.getPeticionID() + " transferida a " + sucursalDestino.getDireccion());
        }
    }

    // Método para obtener una sucursal aleatoria diferente de la que se va a eliminar
    private Sucursal getRandomSucursal(Sucursal excludeSucursal) {
        Random random = new Random();
        Sucursal sucursalRandom;
        do {
            sucursalRandom = sucursales.get(random.nextInt(sucursales.size()));
        } while (sucursalRandom.equals(excludeSucursal));
        return sucursalRandom;
    }

    // Método para listar las peticiones con resultados críticos
    public List<Integer> listarPeticionesConResultadoCritico() {
        List<Integer> peticionesCriticas = new ArrayList<>();
        for (Peticion peticion : peticiones) {
            for (Resultado resultado : peticion.getResultados()) {
                if (resultado.isResultadoCritico()){
                    peticionesCriticas.add(peticion.getPeticionID());
                }
            }
        }
        return peticionesCriticas;
    }

    public void showResultados(int peticionID) {
        Peticion peticionBuscada = null;
        for (Peticion peticion : peticiones) {
            if (peticion.getPeticionID() == peticionID) {
                peticionBuscada = peticion;
                for (Resultado resultado: peticion.getResultados()){
                    System.out.println("RESULTADOS PETICION #"+ peticionID);
                    if(resultado.isResultadoReservado()){
                        System.out.println("Practica #" +resultado.getPractica().getCodigoPractica() + "= RETIRAR POR SUCURSAL");
                    } else {
                        System.out.println("Practica #" +resultado.getPractica().getCodigoPractica()+ "=" + resultado.getDescripcionResultado() + resultado.getValorResultado());
                    }
                    break;
                }
            }
        }
        if (peticionBuscada == null) {
            System.out.println("La peticion solicitada no existe.");
        }

    }

    // Método para obtener la lista de sucursales (opcional)
    public List<Sucursal> getSucursales() {
        return sucursales;
    }

    // Método para obtener la lista de peticiones (opcional)
    public List<Peticion> getPeticiones() {
        return peticiones;
    }
}
