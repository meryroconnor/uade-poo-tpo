package vista;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame{
    private JPanel pnlPrincipal;
    private JTextField inputUsername;
    private JButton ingresarButton;

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
        self = this;
    }
    private void validarInputs(){
        ingresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String username = inputUsername.getText();
                System.out.println("El nombre de usuario es: " + username);
                if(username.isEmpty()){

                }
                else if(username.length() > 5){ // ADD,aca va validacion para saber si es usuario o admin
                    CambiarPantallaUsuario(); //si apretas ingresar te lleva a la pantalla de usuario(FrmUsuario)
                }
                else{
                    CambiarPantallaAdmin(); //si apretas ingresar te lleva a la pantalla de admin(FrmPrincipal)
                }

            }
        });
    }


    private void CambiarPantallaUsuario(){
        MainFrame pantallaUsuario = new MainFrame();
        pantallaUsuario.setVisible(true);
        self.setVisible(false);

    }
    private void CambiarPantallaAdmin(){
        MainFrame pantallaAdmin = new MainFrame();
        pantallaAdmin.setVisible(true);
        self.setVisible(false);

    }

    public static void main(String[] args) {
        LoginFrame frame= new LoginFrame();
        frame.setVisible(true);
    }
}



