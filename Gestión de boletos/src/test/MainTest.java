package test;

import controlador.ControlLogin;
import vista.VistaLogin;

public class MainTest {

    public static void main(String[] args) {
        VistaLogin vistaLogin = new VistaLogin();
        ControlLogin controlCliente = new ControlLogin(vistaLogin);
        vistaLogin.setLocationRelativeTo( null );
        vistaLogin.setVisible(true);
    }
}
