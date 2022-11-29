package controlador;

import DAO.DAOAsientosFunciones;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.VistaVentaDeBoletos;
import vista.VistaMenúPrincipal;
import controlador.ControlMenúPrincipal;
import DAO.DAOFunciones;
import DAO.DAOTicketBoletos;
import java.awt.Color;
import javax.swing.JOptionPane;

public class ControlVentaDeBoletos implements ActionListener{
    private VistaVentaDeBoletos vistaVentaDeBoletos;
    private boolean[] ocupaciónAsientosActual; //Para los botones / el programa ya bloquea automaticamente los botones de los asientos ya ocupados
    private float precioBoleto;
    int boletosAQuerer;
    private float precioTotal;

    public ControlVentaDeBoletos(VistaVentaDeBoletos vistaVentaDeBoletos) {
        ocupaciónAsientosActual = new boolean[20];
        this.vistaVentaDeBoletos = vistaVentaDeBoletos;

        this.vistaVentaDeBoletos.getjButton1().addActionListener(this);
        this.vistaVentaDeBoletos.getjButton2().addActionListener(this); 
        
        this.vistaVentaDeBoletos.getjComboBox().addActionListener(this);
        
        //Asientos del teatro
        for(int i=0; i<20; i++){
            this.vistaVentaDeBoletos.getPlace(i).addActionListener(this);
            ocupaciónAsientosActual[i] = false;
        }
        
        DAOAsientosFunciones daoAsientosFunciones = new DAOAsientosFunciones();
        for(int i=0; i<daoAsientosFunciones.getListaDeObrasConFunciones().size(); i++){
            vistaVentaDeBoletos.getjComboBox().addItem(daoAsientosFunciones.getListaDeObrasConFunciones().get(i).getDatosFunción());
        }
    }

    public void actionPerformed(ActionEvent evento) {
        if(vistaVentaDeBoletos.getjComboBox() == evento.getSource()){ //Cada vez que cambia de función
            DAOAsientosFunciones daoAsientosFunciones = new DAOAsientosFunciones();
            int index = 0;
            if(vistaVentaDeBoletos.getjComboBox().getSelectedIndex() >= 1){
                index = vistaVentaDeBoletos.getjComboBox().getSelectedIndex() - 1;
            }
            for(int i=0; i<20; i++){
                ocupaciónAsientosActual[i] = false; //Reiniciamos las ocupaciones actuales
                if(vistaVentaDeBoletos.getjComboBox().getSelectedIndex() == 0){
                    vistaVentaDeBoletos.getPlace(i).setEnabled(false); //Vista default
                    vistaVentaDeBoletos.getPlace(i).setBackground(Color.GRAY);
                    vistaVentaDeBoletos.getTextField1().setText(" ");
                    vistaVentaDeBoletos.getTextField2().setText(" ");
                    vistaVentaDeBoletos.getTextField3().setText(" ");
                }else{
                    actualizarVistaAsientos(daoAsientosFunciones);
                    precioBoleto = daoAsientosFunciones.getListaDeObrasConFunciones().get(index).getFunción().getObra().getPrecioDelBoleto();
                    vistaVentaDeBoletos.getTextField1().setText("$" + Float.toString(precioBoleto));
                    vistaVentaDeBoletos.getTextField2().setText("0");
                    vistaVentaDeBoletos.getTextField3().setText("$0");
                }
            }
        }
        for(int i=0; i<20; i++){
            if(vistaVentaDeBoletos.getPlace(i) == evento.getSource()){
                if(ocupaciónAsientosActual[i] == true){
                    ocupaciónAsientosActual[i] = false;
                    vistaVentaDeBoletos.getPlace(i).setBackground(Color.white);
                }else{
                    ocupaciónAsientosActual[i] = true;
                    vistaVentaDeBoletos.getPlace(i).setBackground(Color.red);
                }
                actualizarPrecios();
            }
        }
        if(vistaVentaDeBoletos.getjButton1() == evento.getSource()) { //botón volver
            VistaMenúPrincipal vistaMenúPrincipal = new VistaMenúPrincipal();
            ControlMenúPrincipal controlMenúPrincipal = new ControlMenúPrincipal(vistaMenúPrincipal);
            vistaMenúPrincipal.setLocationRelativeTo( null );
            vistaMenúPrincipal.setVisible(true);
            vistaVentaDeBoletos.dispose();
        }else if(vistaVentaDeBoletos.getjButton2() == evento.getSource()){ //Vender boletos
            if(vistaVentaDeBoletos.getjComboBox().getSelectedIndex() == 0){
                JOptionPane.showMessageDialog(vistaVentaDeBoletos.getjOptionPane(), "Debe seleccionar una función válida primero.");
            }else if(boletosAQuerer == 0){
                JOptionPane.showMessageDialog(vistaVentaDeBoletos.getjOptionPane(), "Debe seleccionar asientos disponibles para realizar una venta.");
            }else{
                int g = -1;
                while(g < 0){
                    String input = JOptionPane.showInputDialog(vistaVentaDeBoletos.getjOptionPane(), "El monto es de: $" + precioTotal + ", ingrese la cantidad que pagó el cliente.");
                    try{
                        float pago = Float.parseFloat(input);
                        float cambio = pago - precioTotal;
                        if(cambio < 0){
                            JOptionPane.showMessageDialog(vistaVentaDeBoletos.getjOptionPane2(), "El pago debe ser mayor al precio total de los boletos.");
                        }else{
                            g++;
                            JOptionPane.showMessageDialog(vistaVentaDeBoletos.getjOptionPane(), "El cambio es de: $" + cambio);
                            DAOAsientosFunciones daoAsientosFunciones = new DAOAsientosFunciones();
                            int index = vistaVentaDeBoletos.getjComboBox().getSelectedIndex() - 1;
                            daoAsientosFunciones.venderAsientos(ocupaciónAsientosActual, index);
                            //Imprimimos el boleto
                            DAOTicketBoletos daoTicketsYBoletos = new DAOTicketBoletos(daoAsientosFunciones.getListaDeObrasConFunciones().get(index).getFunción(), boletosAQuerer, precioTotal, pago, cambio, ocupaciónAsientosActual);
                            daoTicketsYBoletos.imprimirTicket();
                            daoTicketsYBoletos.imprimirBoletos();
                            //Actualizamos datos en pantalla
                            actualizarVistaAsientos(daoAsientosFunciones);
                            for(int i=0; i<20; i++){
                                ocupaciónAsientosActual[i] = false; //Reiniciamos las ocupaciones actuales
                            }
                            actualizarPrecios();
                        }
                    }catch(Exception e){
                        JOptionPane.showMessageDialog(vistaVentaDeBoletos.getjOptionPane2(), "Debe ingresar un número.");
                    }
                }
            }
        }
    }
    
    private void actualizarVistaAsientos(DAOAsientosFunciones daoAsientosFunciones){
        for(int i=0; i<20; i++){    
            if(daoAsientosFunciones.getListaDeObrasConFunciones().get(vistaVentaDeBoletos.getjComboBox().getSelectedIndex() - 1).getAsiento(i) == 0){ //Si el asiento está libre
                vistaVentaDeBoletos.getPlace(i).setEnabled(true);
                vistaVentaDeBoletos.getPlace(i).setBackground(Color.WHITE);
            }else{ //Si está ocupado
                vistaVentaDeBoletos.getPlace(i).setEnabled(false);
                vistaVentaDeBoletos.getPlace(i).setBackground(Color.BLUE);
            }
        }
    }
    private void actualizarPrecios(){
        boletosAQuerer = 0;
        for (int i=0; i<20; i++){
            if(ocupaciónAsientosActual[i] == true){
                boletosAQuerer++;
            }
        }
        precioTotal = precioBoleto * boletosAQuerer;
        vistaVentaDeBoletos.getTextField2().setText(Integer.toString(boletosAQuerer));
        vistaVentaDeBoletos.getTextField3().setText("$" + Float.toString(precioTotal));
    }
}
