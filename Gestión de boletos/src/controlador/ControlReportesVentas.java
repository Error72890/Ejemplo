package controlador;

import DAO.DAOVentas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.VistaMenúPrincipal;
import vista.VistaReportesVentas;



public class ControlReportesVentas implements ActionListener{
    private VistaReportesVentas vistaReportesVentas;

    public ControlReportesVentas(VistaReportesVentas  vistaReportesVentas ) {
        this.vistaReportesVentas  = vistaReportesVentas ;

        this.vistaReportesVentas.getjButton1().addActionListener(this);
        this.vistaReportesVentas.getjButton2().addActionListener(this); 
        this.vistaReportesVentas.getjButton3().addActionListener(this); 
        
        DAOVentas daoVentas = new DAOVentas();
        for(int i=0; i<daoVentas.getListaDias().size(); i++){
            vistaReportesVentas.getjComboBox1().addItem(daoVentas.getListaDias().get(i).getFecha());
        }
        String divisor = "-";
        for(int i=0; i<daoVentas.getListaMeses().size(); i++){
            String[] partesFecha = daoVentas.getListaMeses().get(i).getFecha().split(divisor);
            String fechaSinDia = partesFecha[0] + "-" + partesFecha[1];
            vistaReportesVentas.getjComboBox2().addItem(fechaSinDia);
        }
    }
    
    public void actionPerformed(ActionEvent evento) {
        if(vistaReportesVentas.getjButton1() == evento.getSource()) { //botón volver
            VistaMenúPrincipal vistaMenúPrincipal = new VistaMenúPrincipal();
            ControlMenúPrincipal controlMenúPrincipal = new ControlMenúPrincipal(vistaMenúPrincipal);
            vistaMenúPrincipal.setLocationRelativeTo( null );
            vistaMenúPrincipal.setVisible(true);
            vistaReportesVentas.dispose();
        }else if(vistaReportesVentas.getjButton2() == evento.getSource()){
            if(vistaReportesVentas.getjComboBox1().getSelectedIndex() != 0){
                DAOVentas daoVentas = new DAOVentas();
                daoVentas.imprimirReporteDia(vistaReportesVentas.getjComboBox1().getSelectedItem().toString());
            }
        }else if(vistaReportesVentas.getjButton3() == evento.getSource()){
            if(vistaReportesVentas.getjComboBox2().getSelectedIndex() != 0){
                DAOVentas daoVentas = new DAOVentas();
                daoVentas.imprimirReporteMes(vistaReportesVentas.getjComboBox2().getSelectedItem().toString());
            }
        }
    }
}
