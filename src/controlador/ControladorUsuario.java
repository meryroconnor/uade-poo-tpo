package controlador;

import DAOs.UserDAO;
import DTOs.UserDTO;
import Laboratorio.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ControladorUsuario {
    private static ControladorUsuario instance;
    private  List<User> usuarios;
    private int nextUserID;

    private ControladorUsuario(){
        nextUserID = 1;
        this.usuarios = new ArrayList<>();

        this.loadUsuariosToModelFromDAO();
    }

    public static ControladorUsuario getInstance(){
        if (instance == null){
            instance = new ControladorUsuario();
        }
        return instance;
    }

    private void loadUsuariosToModelFromDAO(){
        List<UserDTO> usuariosDTO = new ArrayList<>();
        usuariosDTO = getUsuariosFromDAO();
        for(UserDTO usuario : usuariosDTO){
            crearUsuario(usuario); // se mantiene el orden de los parametros ID porque tienen el orden en el que aparecen en el JSON
        }
    }

    private void saveUsuarioToDAO(UserDTO userParam){
        try{
            UserDAO userDAO = new UserDAO();
            userDAO.crearUser(userParam);
            System.out.println(String.format("Usuario Creado: %s", userParam.getUsername()));
        } catch (Exception e){
            System.out.println("Usuario Existente: " + e);
        }
    }

    private UserDTO getUsuarioFromDAO(UserDTO userParam){
        UserDTO userEncontrado = null;
        try{
            UserDAO userDAO = new UserDAO();
            userEncontrado = userDAO.obtenerUser(userParam);
            System.out.println(String.format("Usuario Encontrado: %s", userParam.getUsername()));

        } catch (Exception e){
            System.out.println("Usuario No Existente: " + e);
        }
        return userEncontrado;
    }

    private List<UserDTO> getUsuariosFromDAO(){
        List<UserDTO> usuarios = new ArrayList<>();
        try{
            UserDAO userDAO = new UserDAO();
            usuarios = userDAO.obtenerUsuarios();
            System.out.println(String.format("Usuarios Encontrados"));

        } catch (Exception e){
            System.out.println("Usuarios No Existentes: " + e);
        }
        return usuarios;
    }

    public UserDTO getUsuarios(UserDTO userParam){
        UserDTO usuarioEncontrado = null;
        for (User usuario : usuarios){
            if (Objects.equals(usuario.getUsername(), userParam.getUsername())){
                usuarioEncontrado = usuario.toDTO();
                break;
            }
        }
        return usuarioEncontrado;
    }

    public UserDTO checkCredentials(UserDTO userParam){
        UserDTO usuario = getUsuarios(userParam);
        if (usuario != null){
            if (Objects.equals(userParam.getPassword(), usuario.getPassword())){
                return usuario;
            }
        }
        return null;
    }

    public void crearUsuario(UserDTO userParam){
        User user = new User(nextUserID++, userParam.getNombreApellido(), userParam.getDNI(), userParam.getEmail(), userParam.getUsername(), userParam.getPassword(), userParam.getRol());
        if (getUsuarios(user.toDTO()) == null){
            usuarios.add(user);
            if (getUsuarioFromDAO(user.toDTO()) == null){ //significa que no existe en el DAO
                saveUsuarioToDAO(user.toDTO());
            }
        } else {
            user = null;
            System.out.println("Usuario existente cancelando operacion");
        }
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


