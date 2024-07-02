package DTOs;

public class UserDTO {
    private int userID;
    private String nombreApellido;
    private String email;
    private String username;
    private String password;
    private String DNI;

    public UserDTO(int userID, String nombreApellido, String email, String username, String password, String DNI){
        this.userID = userID;
        this.nombreApellido = nombreApellido;
        this.email = email;
        this.username = username;
        this.password = password;
        this.DNI = DNI;
    }

    public int getUserID() {
        return userID;
    }

}
