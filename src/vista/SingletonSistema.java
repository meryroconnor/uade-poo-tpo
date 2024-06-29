package vista;

import Laboratorio.Peticion;
import Laboratorio.Sucursal;
import controlador.ControladorAtencion;

import java.util.ArrayList;
import java.util.List;

public class SingletonSistema {
    private static SingletonSistema instance;
    private String username;
    private String rol;

    // Constructor privado para implementar el patrón Singleton
    private SingletonSistema() {
    }

    // Método para obtener la única instancia de ControladorAtencion
    public static SingletonSistema getInstance() {
        if (instance == null) {
            instance = new SingletonSistema();
        }
        return instance;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
