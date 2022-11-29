package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.VistaAñadirUsuario;
import vista.VistaMenúPrincipal;
import controlador.ControlMenúPrincipal;
import DAO.DAOUsuarios;
import javax.swing.JOptionPane;

public class ControlAñadirUsuario implements ActionListener{
    private VistaAñadirUsuario vistaAñadirUsuario;

    public ControlAñadirUsuario(VistaAñadirUsuario vistaAñadirUsuario) {
        this.vistaAñadirUsuario = vistaAñadirUsuario;

        this.vistaAñadirUsuario.getjButton1().addActionListener(this);
        this.vistaAñadirUsuario.getjButton2().addActionListener(this); 
    }

    public void actionPerformed(ActionEvent evento) {
        if(vistaAñadirUsuario.getjButton1() == evento.getSource()) { //botón volver
            VistaMenúPrincipal vistaMenúPrincipal = new VistaMenúPrincipal();
            ControlMenúPrincipal controlMenúPrincipal = new ControlMenúPrincipal(vistaMenúPrincipal);
            vistaMenúPrincipal.setLocationRelativeTo( null );
            vistaMenúPrincipal.setVisible(true);
            vistaAñadirUsuario.dispose();
        }else if(vistaAñadirUsuario.getjButton2() == evento.getSource()){ //Botón añadir cuenta
            String nombre;
            String apellidos;
            String curp;
            String usuario;
            String contraseña;

            nombre = vistaAñadirUsuario.getjTextField1().getText();
            apellidos = vistaAñadirUsuario.getjTextField2().getText();
            curp = vistaAñadirUsuario.getjTextField3().getText();              
            usuario = vistaAñadirUsuario.getjTextField4().getText();
            contraseña = vistaAñadirUsuario.getjTextField5().getText();
            DAOUsuarios daoUsuarios = new DAOUsuarios();
            try{
                switch(daoUsuarios.añadirUsuario(nombre, apellidos, curp, usuario, contraseña)){
                    case 0:
                        vistaAñadirUsuario.getjTextField1().setText("");
                        vistaAñadirUsuario.getjTextField2().setText("");
                        vistaAñadirUsuario.getjTextField3().setText("");
                        vistaAñadirUsuario.getjTextField4().setText("");
                        vistaAñadirUsuario.getjTextField5().setText("");
                        JOptionPane.showMessageDialog(vistaAñadirUsuario.getJOptionPane(), 
                            "Usuario añadido exitosamente");
                        break;
                    case 1:
                        JOptionPane.showMessageDialog(vistaAñadirUsuario.getJOptionPane(), 
                            "Faltan datos por ingresar");
                        break;
                    case 2:
                        JOptionPane.showMessageDialog(vistaAñadirUsuario.getJOptionPane(), 
                            "Ya existe una cuenta con ese nombre de usuario");
                        break;
                    case 3:
                        JOptionPane.showMessageDialog(vistaAñadirUsuario.getJOptionPane(), 
                            "Ya existe una cuenta con ese curp");
                        break;
                }
            }catch(Exception e){
                System.out.printf("Archivo Usuarios.txt no se encuentra.");
            }
        }
    }
}
