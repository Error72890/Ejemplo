package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.VistaMenúPrincipal;
import vista.VistaLogin;
import controlador.ControlLogin;
import vista.VistaModificarUsuario;
import controlador.ControlModificarUsuario;
import vista.VistaAñadirUsuario;
import controlador.ControlAñadirUsuario;
import vista.VistaAdministrarObras;
import controlador.ControlAdministrarObras;
import vista.VistaAdministrarFunciones;
import controlador.ControlAdministrarFunciones;
import vista.VistaVentaDeBoletos;
import controlador.ControlVentaDeBoletos;
import vista.VistaReportesVentas;
import controlador.ControlReportesVentas;



public class ControlMenúPrincipal implements ActionListener{
    private VistaMenúPrincipal vistaMenúPrincipal;

    public ControlMenúPrincipal(VistaMenúPrincipal vistaMenúPrincipal) {
        this.vistaMenúPrincipal = vistaMenúPrincipal;

        this.vistaMenúPrincipal.getjButton1().addActionListener(this);
        this.vistaMenúPrincipal.getjButton2().addActionListener(this); 
        this.vistaMenúPrincipal.getjButton3().addActionListener(this); 
        this.vistaMenúPrincipal.getjButton4().addActionListener(this); 
        this.vistaMenúPrincipal.getjButton5().addActionListener(this); 
        this.vistaMenúPrincipal.getjButton6().addActionListener(this); 
        this.vistaMenúPrincipal.getjButton7().addActionListener(this); 
    }

    public void actionPerformed(ActionEvent evento) {
        //Localizar usuario
        if(vistaMenúPrincipal.getjButton1() == evento.getSource()) {
            VistaLogin vistaLogin = new VistaLogin();
            ControlLogin controlLogin = new ControlLogin(vistaLogin);
            vistaLogin.setLocationRelativeTo( null );
            vistaLogin.setVisible(true);
            vistaMenúPrincipal.dispose();
        }else if(vistaMenúPrincipal.getjButton2() == evento.getSource()){
            VistaModificarUsuario vistaModificarUsuario = new VistaModificarUsuario();
            ControlModificarUsuario controlModificarUsuario = new ControlModificarUsuario(vistaModificarUsuario);
            vistaModificarUsuario.setLocationRelativeTo( null );
            vistaModificarUsuario.setVisible(true);
            vistaMenúPrincipal.dispose();
        }else if(vistaMenúPrincipal.getjButton3() == evento.getSource()){
            VistaAñadirUsuario vistaAñadirUsuario = new VistaAñadirUsuario();
            ControlAñadirUsuario controlAñadirUsuario = new ControlAñadirUsuario(vistaAñadirUsuario);
            vistaAñadirUsuario.setLocationRelativeTo( null );
            vistaAñadirUsuario.setVisible(true);
            vistaMenúPrincipal.dispose();
        }else if(vistaMenúPrincipal.getjButton4() == evento.getSource()){
            VistaAdministrarObras vistaAdministrarObras = new VistaAdministrarObras();
            ControlAdministrarObras controlAdministrarObras = new ControlAdministrarObras(vistaAdministrarObras);
            vistaAdministrarObras.setLocationRelativeTo( null );
            vistaAdministrarObras.setVisible(true);
            vistaMenúPrincipal.dispose();
        }else if(vistaMenúPrincipal.getjButton5() == evento.getSource()){
            VistaAdministrarFunciones vistaAdministrarFunciones = new VistaAdministrarFunciones();
            ControlAdministrarFunciones controlAdministrarFunciones = new ControlAdministrarFunciones(vistaAdministrarFunciones);
            vistaAdministrarFunciones.setLocationRelativeTo( null );
            vistaAdministrarFunciones.setVisible(true);
            vistaMenúPrincipal.dispose();
        }else if(vistaMenúPrincipal.getjButton6() == evento.getSource()){
            VistaVentaDeBoletos vistaVentaDeBoletos = new VistaVentaDeBoletos();
            ControlVentaDeBoletos controlVentaDeBoletos = new ControlVentaDeBoletos(vistaVentaDeBoletos);
            vistaVentaDeBoletos.setLocationRelativeTo( null );
            vistaVentaDeBoletos.setVisible(true);
            vistaMenúPrincipal.dispose();
        }else if(vistaMenúPrincipal.getjButton7() == evento.getSource()){
            VistaReportesVentas vistaReportesVentas = new VistaReportesVentas();
            ControlReportesVentas controlReportesVentas = new ControlReportesVentas(vistaReportesVentas);
            vistaReportesVentas.setLocationRelativeTo( null );
            vistaReportesVentas.setVisible(true);
            vistaMenúPrincipal.dispose();
        }
    }
}
