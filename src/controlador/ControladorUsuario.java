package controlador;

import DTOs.UserDTO;
import Laboratorio.User;

import java.util.ArrayList;
import java.util.List;

public class ControladorUsuario {
    private static ControladorUsuario instance;
    private  List<User> usuarios;
    private int nextUserID;

    private ControladorUsuario(){
        nextUserID = 1;
        this.usuarios = new ArrayList<>();
    }

    public static ControladorUsuario getInstance(){
        if (instance == null){
            instance = new ControladorUsuario();
        }
        return instance;
    }

    public UserDTO crearUsuario(String nombreApellido, String DNI, String email, String username, String password, String rol){
        User user = new User(nextUserID++, nombreApellido, DNI, email, username, password, rol);
        usuarios.add(user);
        return user.toDTO();
    } // tal vez no haria falta un metodo para obtener el usuario se supone que una vez que se creo no se toca mas.

    public void eliminarUsuario(int userID){
        boolean usuarioEncontrado = false;
        for (User usuario : usuarios){
            if (usuario.getUserID() == userID){
                usuarioEncontrado = true;
                usuarios.remove(usuario);
                break;
            }
        }
        if (usuarioEncontrado == true){
            System.out.println(String.format("UsuarioID: %d Eliminado", userID));
        } else {
            System.out.println(String.format("UsuarioID: %d No Encontrado", userID));
        }
    }
}


