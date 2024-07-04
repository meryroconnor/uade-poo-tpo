package Laboratorio;

import DTOs.UserDTO;

public class User {
    private int userID;
    private String nombreApellido;
    private String email;
    private String username;
    private String password;
    private String DNI;
    private String rol;

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getNombreApellido() {
        return nombreApellido;
    }

    public void setNombreApellido(String nombreApellido) {
        this.nombreApellido = nombreApellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getRol() {
        return this.rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public User(int userID, String nombreApellido, String DNI, String email, String username, String password, String rol) {
        this.userID = userID;
        this.email = email;
        this.username = username;
        this.password = password;
        this.nombreApellido = nombreApellido;
        this.DNI = DNI;
        this.rol = rol;
    }

    public int getUserID() {
        return userID;
    }

    public UserDTO toDTO(){
        UserDTO userDTO = new UserDTO(this.userID, this.nombreApellido, this.email, this.username, this.password, this.DNI, this.rol);
        return  userDTO;
    }
}
