package vista;
import DTOs.UserDTO;
import controlador.ControladorUsuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame{
    private JPanel pnlPrincipal;
    private JTextField inputUsername;
    private JButton ingresarButton;
    private JButton registrarmeButton;
    private JTextField inputPassword;

    private LoginFrame self;

    public LoginFrame(){
        try {
            UIManager.setLookAndFeel(UIManager.getLookAndFeel()); // obtengo visuales del SO
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        setTitle("Labs S.A. Login");

        setContentPane(pnlPrincipal);

        setSize(600, 300);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        setLocationRelativeTo(null);
        ingresarButton.setForeground(new Color(0, 141, 213));


        this.validarInputs();
        this.registrarUsuario();
        self = this;
    }
    private void validarInputs(){
        ingresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String username = inputUsername.getText();
                String password = inputPassword.getText();
                ControladorUsuario controladorUsuario = ControladorUsuario.getInstance();

                UserDTO usuario = new UserDTO(0, null, null, username, password, null, null);
                usuario = controladorUsuario.checkCredentials(usuario);
                if (usuario != null){
                    SingletonSistema.getInstance().setUsername(usuario.getUsername());
                    SingletonSistema.getInstance().setRol(usuario.getRol());
                    navegateSistema(usuario.getRol()); //si apretas ingresar te lleva a la pantalla de usuario(FrmUsuario)
                    System.out.println(String.format("Username: %s >>> Rol: %s", usuario.getUsername(), usuario.getRol()));
                } else {
                    System.out.println("Usuario o Contrasenia no valida"); //reemplazar con enie q no tengo :(
                    dispose();
                }
            }
        });
    }

    private void registrarUsuario(){
        registrarmeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegisterUserDialog dialog = new RegisterUserDialog(JOptionPane.getFrameForComponent(self));
                dialog.setVisible(true);
            }
        });
    }


    private void navegateSistema(String rol){
        MainFrame pantallaUsuario = new MainFrame(rol);
        pantallaUsuario.setVisible(true);
        self.setVisible(false);

    }

    public static void main(String[] args) {
        LoginFrame frame= new LoginFrame();
        frame.setVisible(true);
    }
}



