package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.VistaMenúPrincipal;
import controlador.ControlMenúPrincipal;
import vista.VistaAdministrarFunciones;
import vista.VistaAñadirModificarFunciones;
import controlador.ControlAdministrarFunciones;
import DAO.DAOFunciones;
import DAO.DAOObras;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class ControlAdministrarFunciones implements ActionListener{
    private VistaAdministrarFunciones vistaAdministrarFunciones;
    private VistaAñadirModificarFunciones vistaAñadirModificarFunciones;
    boolean añadir; //Verdadero si se va a añadir una obra, falso si se va a moficicar
    int index;
    byte mode; //0 para la vista administrar, 1 para la vista añadir/modificar

    public ControlAdministrarFunciones(VistaAdministrarFunciones vistaAdministrarFunciones) {
        mode = 0;
        this.vistaAdministrarFunciones = vistaAdministrarFunciones;
        mostrarListaFunciones();

        this.vistaAdministrarFunciones.getjButton1().addActionListener(this);
        this.vistaAdministrarFunciones.getjButton2().addActionListener(this); 
        this.vistaAdministrarFunciones.getjButton3().addActionListener(this); 
        this.vistaAdministrarFunciones.getjButton4().addActionListener(this); 
    }
    public ControlAdministrarFunciones(VistaAñadirModificarFunciones vistaAñadirModificarFunciones, boolean añadir, int index) {
        mode = 1;
        this.vistaAñadirModificarFunciones = vistaAñadirModificarFunciones;
        this.añadir = añadir;
        this.index = index;
        
        if(!añadir){
            mostrarDatos(); //Se muestran datos de la función a modificar
        }
        mostrarListaObras();
        
        if(añadir){
            this.vistaAñadirModificarFunciones.getjTitle().setText("Nueva Función");
            this.vistaAñadirModificarFunciones.getjButton2().setText("Añadir Función");
        }else{
            this.vistaAñadirModificarFunciones.getjTitle().setText("Modificar función");
            this.vistaAñadirModificarFunciones.getjButton2().setText("Modificar Función");
        }

        this.vistaAñadirModificarFunciones.getjButton1().addActionListener(this);
        this.vistaAñadirModificarFunciones.getjButton2().addActionListener(this); 
    }

    public void actionPerformed(ActionEvent evento) {
        //Vista Administrar funciones
        if(mode == 0){
            //Localizar usuario
            if(vistaAdministrarFunciones.getjButton1() == evento.getSource()) { //Volver
                VistaMenúPrincipal vistaMenúPrincipal = new VistaMenúPrincipal();
                ControlMenúPrincipal controlMenúPrincipal = new ControlMenúPrincipal(vistaMenúPrincipal);
                vistaMenúPrincipal.setLocationRelativeTo( null );
                vistaMenúPrincipal.setVisible(true);
                vistaAdministrarFunciones.dispose();

            }else if(vistaAdministrarFunciones.getjButton2() == evento.getSource()){ //Añadir
                VistaAñadirModificarFunciones vistaAñadirModificarFunciones = new VistaAñadirModificarFunciones();
                ControlAdministrarFunciones controlAñadirModificarFunciones = new ControlAdministrarFunciones(vistaAñadirModificarFunciones, true, 0);
                vistaAñadirModificarFunciones.setLocationRelativeTo( null );
                vistaAñadirModificarFunciones.setVisible(true);
                vistaAdministrarFunciones.dispose();

            }else if(vistaAdministrarFunciones.getjButton3() == evento.getSource()){ //Modificar
                try{
                    int index = Integer.parseInt(vistaAdministrarFunciones.getjTextField1().getText()); //Si no es numero se va al catch
                    try{
                        DAOFunciones daoFunciones = new DAOFunciones();
                        String aux = daoFunciones.getListaFunciones().get(index).getNombreDeLaObra(); //Si no existe esa ID se va al catch
                        VistaAñadirModificarFunciones vistaAñadirModificarFunciones = new VistaAñadirModificarFunciones();
                        ControlAdministrarFunciones controlAñadirModificarFunciones = new ControlAdministrarFunciones(vistaAñadirModificarFunciones, false, index);
                        vistaAñadirModificarFunciones.setLocationRelativeTo( null );
                        vistaAñadirModificarFunciones.setVisible(true);
                        vistaAdministrarFunciones.dispose();
                    }catch(Exception e){
                        JOptionPane.showMessageDialog(vistaAdministrarFunciones.getJOptionPane(), "El ID no corresponde a ninguna función");
                    }
                }catch(Exception e){
                    JOptionPane.showMessageDialog(vistaAdministrarFunciones.getJOptionPane(), "El ID debe ser un número entero");
                }


            }else if(vistaAdministrarFunciones.getjButton4() == evento.getSource()){ //Eliminar
                try{
                    int index = Integer.parseInt(vistaAdministrarFunciones.getjTextField1().getText());
                    DAOFunciones daoFunciones = new DAOFunciones();
                    if(daoFunciones.eliminarFunción(index)){
                        vistaAdministrarFunciones.getjTextField1().setText("");
                        JOptionPane.showMessageDialog(vistaAdministrarFunciones.getJOptionPane(), "Función eliminada exitosamente");
                        mostrarListaFunciones();
                    }else{
                        JOptionPane.showMessageDialog(vistaAdministrarFunciones.getJOptionPane(), "El ID no corresponde a ninguna función");
                    }
                }catch(Exception e){
                    JOptionPane.showMessageDialog(vistaAdministrarFunciones.getJOptionPane(), "El ID debe ser un número entero");
                }
            }
        }else{
        //Vista Añadir modificar funciones
            if(vistaAñadirModificarFunciones.getjButton1() == evento.getSource()) { //botón volver
                VistaAdministrarFunciones vistaAdministrarFunciones = new VistaAdministrarFunciones();
                ControlAdministrarFunciones controlAdministrarFunciones = new ControlAdministrarFunciones(vistaAdministrarFunciones);
                vistaAdministrarFunciones.setLocationRelativeTo( null );
                vistaAdministrarFunciones.setVisible(true);
                vistaAñadirModificarFunciones.dispose();
            }else if(vistaAñadirModificarFunciones.getjButton2() == evento.getSource()){ //Botón añadir / modificar obra
                String nombreDeLaObra;
                String fecha;
                String hora;

                nombreDeLaObra= vistaAñadirModificarFunciones.getjTextField1().getText();
                fecha = vistaAñadirModificarFunciones.getjTextField2().getText();
                hora = vistaAñadirModificarFunciones.getJComboBox1().getSelectedItem().toString();

                DAOFunciones daoFunciones = new DAOFunciones();
                try{
                    if(añadir){
                        switch(daoFunciones.añadirFunción(nombreDeLaObra, fecha, hora)){
                            case 0:
                                JOptionPane.showMessageDialog(vistaAñadirModificarFunciones.getJOptionPane(), 
                                        "Función añadida exitosamente");
                                break;
                            case 1:
                                JOptionPane.showMessageDialog(vistaAñadirModificarFunciones.getJOptionPane(), 
                                        "Función añadida con exito a un día ya transcurrido");
                                break;
                            case 2:
                                JOptionPane.showMessageDialog(vistaAñadirModificarFunciones.getJOptionPane(), 
                                        "Debe ingresar el nombre de una obra existente en lista");
                                break;
                            case 3:
                                JOptionPane.showMessageDialog(vistaAñadirModificarFunciones.getJOptionPane(), 
                                        "El formato de fecha debe ser numerico y de la forma (yyyy-mm-dd)");
                                break;
                            case 4:
                                JOptionPane.showMessageDialog(vistaAñadirModificarFunciones.getJOptionPane(), 
                                        "Ya existe una función en ese horario");
                                break;
                        }
                    }else{
                        switch(daoFunciones.modificarFunción(index, nombreDeLaObra, fecha, hora)){
                            case 0:
                                vistaAñadirModificarFunciones.getjTextField1().setText(" ");
                                vistaAñadirModificarFunciones.getjTextField2().setText(" ");
                                JOptionPane.showMessageDialog(vistaAñadirModificarFunciones.getJOptionPane(), 
                                        "Función modificada exitosamente");
                                break;
                            case 1:
                                vistaAñadirModificarFunciones.getjTextField1().setText("");
                                vistaAñadirModificarFunciones.getjTextField2().setText(" ");
                                JOptionPane.showMessageDialog(vistaAñadirModificarFunciones.getJOptionPane(), 
                                        "Función modificada con exito a un día ya transcurrido");
                                break;
                            case 2:
                                JOptionPane.showMessageDialog(vistaAñadirModificarFunciones.getJOptionPane(), 
                                        "Debe ingresar el nombre de una obra existente en lista");
                                break;
                            case 3:
                                JOptionPane.showMessageDialog(vistaAñadirModificarFunciones.getJOptionPane(), 
                                        "El formato de fecha debe ser numerico y de la forma (yyyy-mm-dd)");
                                break;
                            case 4:
                                JOptionPane.showMessageDialog(vistaAñadirModificarFunciones.getJOptionPane(), 
                                        "Ya existe una función en ese horario");
                                break;
                        }
                    }
                }catch(Exception e){
                    System.out.printf("Archivo Funciones.txt no se encuentra.");
                }
            }
        }
    }
       
    public void mostrarDatos(){ //Rellena los recuadros con datos de la base de datos
        DAOFunciones daoFunciones = new DAOFunciones();
        vistaAñadirModificarFunciones.getjTextField1().setText(daoFunciones.getListaFunciones().get(index).getNombreDeLaObra());
        vistaAñadirModificarFunciones.getjTextField2().setText(daoFunciones.getListaFunciones().get(index).getFecha());
        if(daoFunciones.getListaFunciones().get(index).getHora().equals("18:00")){
            vistaAñadirModificarFunciones.getJComboBox1().setSelectedIndex(0);
        }else{
            vistaAñadirModificarFunciones.getJComboBox1().setSelectedIndex(1);
        }
    }
    
    public void mostrarListaFunciones(){
        DAOFunciones daoFunciones = new DAOFunciones();
        int numFilas = daoFunciones.getListaFunciones().size();
        JTable Tabla = vistaAdministrarFunciones.getJtable();
        DefaultTableModel model =  (DefaultTableModel) Tabla.getModel();
        model.setRowCount(0); //reiniciamos
        model.setRowCount(numFilas); 

        for (int i = 0; i <= numFilas; i++){
            try{
                //Para cada fila
                Tabla.setValueAt(i,i, 0);
                Tabla.setValueAt(daoFunciones.getListaFunciones().get(i).getNombreDeLaObra(),i, 1);
                Tabla.setValueAt(daoFunciones.getListaFunciones().get(i).getFecha(),i, 2);
                Tabla.setValueAt(daoFunciones.getListaFunciones().get(i).getHora(),i, 3);
            }catch (Exception e){
                
            }
        }
    }
    
    public void mostrarListaObras(){
        DAOObras daoObras = new DAOObras();
        int numFilas = daoObras.getListaObras().size();
        JTable Tabla = vistaAñadirModificarFunciones.getJtable();
        DefaultTableModel model =  (DefaultTableModel) Tabla.getModel();
        model.setRowCount(0); //reiniciamos
        model.setRowCount(numFilas); 

        for (int i = 0; i <= numFilas; i++){
            try{
                Tabla.setValueAt(daoObras.getListaObras().get(i).getNombreDeLaObra(),i, 0);
            }catch (Exception e){
                
            }
        }
    }
}
