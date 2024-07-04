package DTOs;

public class UserDTO {
    private int userID;
    private String nombreApellido;
    private String email;
    private String username;
    private String password;
    private String DNI;
    private String rol;

    public UserDTO(int userID, String nombreApellido, String email, String username, String password, String DNI, String rol){
        this.userID = userID;
        this.nombreApellido = nombreApellido;
        this.email = email;
        this.username = username;
        this.password = password;
        this.DNI = DNI;
        this.rol = rol;
    }

    public int getUserID() {
        return userID;
    }

    public String getNombreApellido() {
        return nombreApellido;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDNI() {
        return DNI;
    }

    public String getRol() {
        return rol;
    }
}
