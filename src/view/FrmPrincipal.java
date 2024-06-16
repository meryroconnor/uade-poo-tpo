package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmPrincipal extends JFrame{
    private JPanel panelPrincipal;
    private JPanel panelLabel;
    private JPanel panelMenu;
    private JButton PacientesButton;
    private JButton UsuariosButton;
    private JButton SucursalesButton;

    public  FrmPrincipal(String titulo) {
        super(titulo);

        try {
            UIManager.setLookAndFeel(UIManager.getLookAndFeel()); // obtengo visuales del SO
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }

        setContentPane(this.panelPrincipal);
        setSize(400, 200); // tamanio pantalla
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // comportamiento al cerrar
        setLocationRelativeTo(null); // pantalla centrada

    }

    private void EventHandler(){
        UsuariosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        PacientesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        SucursalesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public static void main(String[] args) {
        FrmPrincipal frame = new FrmPrincipal("Sistema de Gestion de Laboratorio");
        frame.setVisible(true);
    }
}
