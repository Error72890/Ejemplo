package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.VistaModificarUsuario;
import vista.VistaMenúPrincipal;
import controlador.ControlMenúPrincipal;
import DAO.DAOUsuarios;
import javax.swing.JOptionPane;

public class ControlModificarUsuario implements ActionListener{
    private VistaModificarUsuario vistaModificarUsuario;

    public ControlModificarUsuario(VistaModificarUsuario vistaModificarUsuario) {
        this.vistaModificarUsuario = vistaModificarUsuario;

        this.vistaModificarUsuario.getjButton1().addActionListener(this);
        this.vistaModificarUsuario.getjButton2().addActionListener(this); 
        this.vistaModificarUsuario.getjButton3().addActionListener(this);
    }

    public void actionPerformed(ActionEvent evento) {
        //Localizar usuario
        if(vistaModificarUsuario.getjButton1() == evento.getSource()) { //botón volver
            VistaMenúPrincipal vistaMenúPrincipal = new VistaMenúPrincipal();
            ControlMenúPrincipal controlMenúPrincipal = new ControlMenúPrincipal(vistaMenúPrincipal);
            vistaMenúPrincipal.setLocationRelativeTo( null );
            vistaMenúPrincipal.setVisible(true);
            vistaModificarUsuario.dispose();
        }else if(vistaModificarUsuario.getjButton2() == evento.getSource()){ //Botón eliminar cuenta
            String nombre;
            String apellidos;
            String curp;

            nombre = vistaModificarUsuario.getjTextField1().getText();
            apellidos = vistaModificarUsuario.getjTextField2().getText();
            curp = vistaModificarUsuario.getjTextField3().getText();
            DAOUsuarios daoUsuarios = new DAOUsuarios();
            try{
                if(daoUsuarios.eliminarUsuario(nombre, apellidos, curp)){
                    vistaModificarUsuario.getjTextField1().setText("");
                    vistaModificarUsuario.getjTextField2().setText("");
                    vistaModificarUsuario.getjTextField3().setText("");
                    JOptionPane.showMessageDialog(vistaModificarUsuario.getJOptionPane(), 
                        "Cuenta de usuario eliminada exitosamente");
                }else{
                    JOptionPane.showMessageDialog(vistaModificarUsuario.getJOptionPane(), 
                        "No existe una cuenta con esos datos");
                }
            }catch(Exception e){
                System.out.printf("Archivo Usuarios.txt no se encuentra.");
            }
        }else if(vistaModificarUsuario.getjButton3() == evento.getSource()){ //Botón modificar cuenta
            String nombre;
            String apellidos;
            String curp;//Botón eliminar cuenta
            String nombreN;
            String apellidosN;
            String curpN;
            String usuarioN;
            String contraseñaN;

            nombre = vistaModificarUsuario.getjTextField1().getText();
            apellidos = vistaModificarUsuario.getjTextField2().getText();
            curp = vistaModificarUsuario.getjTextField3().getText();            
            nombreN = vistaModificarUsuario.getjTextField4().getText();
            apellidosN = vistaModificarUsuario.getjTextField5().getText();
            curpN = vistaModificarUsuario.getjTextField6().getText();          
            usuarioN = vistaModificarUsuario.getjTextField7().getText();
            contraseñaN = vistaModificarUsuario.getjTextField8().getText();
            DAOUsuarios daoUsuarios = new DAOUsuarios();
            try{
                switch(daoUsuarios.modificarUsuario(nombre, apellidos, curp, nombreN, apellidosN, curpN, usuarioN, contraseñaN)){
                    case 0:
                        vistaModificarUsuario.getjTextField4().setText("");
                        vistaModificarUsuario.getjTextField5().setText("");
                        vistaModificarUsuario.getjTextField6().setText("");
                        vistaModificarUsuario.getjTextField7().setText("");
                        vistaModificarUsuario.getjTextField8().setText("");
                        JOptionPane.showMessageDialog(vistaModificarUsuario.getJOptionPane(), 
                            "Cuenta de usuario modificada exitosamente");
                        break;
                    case 1:
                        JOptionPane.showMessageDialog(vistaModificarUsuario.getJOptionPane(), 
                            "Faltan datos por ingresar");
                        break;
                    case 2:
                        JOptionPane.showMessageDialog(vistaModificarUsuario.getJOptionPane(), 
                            "Ya existe una cuenta con ese nombre de usuario");
                        break;
                    case 3:
                        JOptionPane.showMessageDialog(vistaModificarUsuario.getJOptionPane(), 
                            "Ya existe una cuenta con ese curp");
                        break;
                    case 4:
                        JOptionPane.showMessageDialog(vistaModificarUsuario.getJOptionPane(), 
                            "No existe una cuenta con esos datos");
                        break;
                }
            }catch(Exception e){
                System.out.printf("Archivo Usuarios.txt no se encuentra.");
            }
        }
    }
}
