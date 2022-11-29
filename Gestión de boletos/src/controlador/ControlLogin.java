package controlador;

import DAO.DAOUsuarios;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.Usuario;
import vista.VistaLogin;
import vista.VistaMenúPrincipal;;
import controlador.ControlMenúPrincipal;

public class ControlLogin implements ActionListener{
    private Usuario modeloUsuario;
    private VistaLogin vistaLogin;
    private DAOUsuarios DAOUsuarios;

    public ControlLogin(VistaLogin vistaLogin) {
        this.modeloUsuario = new Usuario();
        this.vistaLogin = vistaLogin;

        this.vistaLogin.getjButton1().addActionListener(this);
    }

    public void actionPerformed(ActionEvent evento) {
        //Localizar usuario
        if(vistaLogin.getjButton1() == evento.getSource()) {
            String nombreDeUsuario;
            String contraseña;

            nombreDeUsuario = vistaLogin.getjTextField1().getText();
            contraseña = vistaLogin.getjTextField2().getText();
            DAOUsuarios daoUsuarios = new DAOUsuarios();
            try{
                switch(daoUsuarios.buscarExistencia(nombreDeUsuario, contraseña)){
                    case 0:
                        VistaMenúPrincipal vistaMenúPrincipal = new VistaMenúPrincipal();
                        ControlMenúPrincipal controlMenúPrincipal = new ControlMenúPrincipal(vistaMenúPrincipal);
                        vistaMenúPrincipal.setLocationRelativeTo( null );
                        vistaMenúPrincipal.setVisible(true);
                        vistaLogin.dispose();
                        break;
                    case 1:
                        vistaLogin.getjLabel().setText("*Usuario no existente");
                        break;
                    case 2:
                        vistaLogin.getjLabel().setText("*Contraseña Incorrecta");
                        break;
                }
            }catch(Exception e){
                System.out.printf("Archivo Usuarios.txt no se encuentra.");
            }
        }
    }
}
