package controlador;

import DAO.DAOObras;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.VistaMenúPrincipal;
import controlador.ControlMenúPrincipal;
import vista.VistaAdministrarObras;
import vista.VistaAñadirModificarObras;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class ControlAdministrarObras implements ActionListener{
    private VistaAdministrarObras vistaAdministrarObras;
    private VistaAñadirModificarObras vistaAñadirModificarObras;
    boolean añadir; //Verdadero si se va a añadir una obra, falso si se va a moficicar
    int index;
    byte mode; //0 para vista administrar, 1 para vista añadir/modificar

    public ControlAdministrarObras(VistaAdministrarObras vistaAdministrarObras) {
        mode = 0;
        this.vistaAdministrarObras = vistaAdministrarObras;
        mostrarLista();

        this.vistaAdministrarObras.getjButton1().addActionListener(this);
        this.vistaAdministrarObras.getjButton2().addActionListener(this); 
        this.vistaAdministrarObras.getjButton3().addActionListener(this); 
        this.vistaAdministrarObras.getjButton4().addActionListener(this); 
    }
    public ControlAdministrarObras(VistaAñadirModificarObras vistaAñadirModificarObras, boolean añadir, int index) {
        mode = 1;
        this.vistaAñadirModificarObras = vistaAñadirModificarObras;
        this.añadir = añadir;
        this.index = index;
        
        if(!añadir){
            mostrarDatosPrevios(); //Si se está modificando una obra
        }
        
        if(añadir){
            this.vistaAñadirModificarObras.getjTitle().setText("Nueva obra");
            this.vistaAñadirModificarObras.getjButton2().setText("Añadir obra");
        }else{
            this.vistaAñadirModificarObras.getjTitle().setText("Nuevos datos");
            this.vistaAñadirModificarObras.getjButton2().setText("Modificar obra");
        }

        this.vistaAñadirModificarObras.getjButton1().addActionListener(this);
        this.vistaAñadirModificarObras.getjButton2().addActionListener(this); 
    }

    public void actionPerformed(ActionEvent evento) {
        if(mode == 0){ //Funciones vista administrar
            //Localizar usuario
            if(vistaAdministrarObras.getjButton1() == evento.getSource()) { //Volver
                VistaMenúPrincipal vistaMenúPrincipal = new VistaMenúPrincipal();
                ControlMenúPrincipal controlMenúPrincipal = new ControlMenúPrincipal(vistaMenúPrincipal);
                vistaMenúPrincipal.setLocationRelativeTo( null );
                vistaMenúPrincipal.setVisible(true);
                vistaAdministrarObras.dispose();

            }else if(vistaAdministrarObras.getjButton2() == evento.getSource()){ //Añadir
                VistaAñadirModificarObras vistaAñadirModificarObras = new VistaAñadirModificarObras();
                ControlAdministrarObras controlAñadirModificarObras = new ControlAdministrarObras(vistaAñadirModificarObras, true, 0);
                vistaAñadirModificarObras.setLocationRelativeTo( null );
                vistaAñadirModificarObras.setVisible(true);
                vistaAdministrarObras.dispose();

            }else if(vistaAdministrarObras.getjButton3() == evento.getSource()){ //Modificar
                try{
                    int index = Integer.parseInt(vistaAdministrarObras.getjTextField1().getText()); //Si no es numero se va al catch
                    System.out.print(index);
                    try{
                        DAOObras daoObras = new DAOObras();
                        String aux = daoObras.getListaObras().get(index).getNombreDeLaObra(); //Si no existe esa ID se va al catch
                        VistaAñadirModificarObras vistaAñadirModificarObras = new VistaAñadirModificarObras();
                        ControlAdministrarObras controlAñadirModificarObras = new ControlAdministrarObras(vistaAñadirModificarObras, false, index);
                        vistaAñadirModificarObras.setLocationRelativeTo( null );
                        vistaAñadirModificarObras.setVisible(true);
                        vistaAdministrarObras.dispose();
                    }catch(Exception e){
                        JOptionPane.showMessageDialog(vistaAdministrarObras.getJOptionPane(), "El ID no corresponde a ninguna obra");
                    }
                }catch(Exception e){
                    JOptionPane.showMessageDialog(vistaAdministrarObras.getJOptionPane(), "El ID solo admite valores enteros");
                }


            }else if(vistaAdministrarObras.getjButton4() == evento.getSource()){ //Eliminar
                try{
                    int index = Integer.parseInt(vistaAdministrarObras.getjTextField1().getText());
                    DAOObras daoObras = new DAOObras();
                    if(daoObras.eliminarObra(index)){
                        vistaAdministrarObras.getjTextField1().setText("");
                        JOptionPane.showMessageDialog(vistaAdministrarObras.getJOptionPane(), "Obra eliminada exitosamente");
                        mostrarLista();
                    }else{
                        JOptionPane.showMessageDialog(vistaAdministrarObras.getJOptionPane(), "El ID no corresponde a ninguna obra");
                    }
                }catch(Exception e){
                    JOptionPane.showMessageDialog(vistaAdministrarObras.getJOptionPane(), "El ID solo admite valores enteros");
                }
            }
        }else{
            if(vistaAñadirModificarObras.getjButton1() == evento.getSource()) { //botón volver
                VistaAdministrarObras vistaAdministrarObras = new VistaAdministrarObras();
                ControlAdministrarObras controlAdministrarObras = new ControlAdministrarObras(vistaAdministrarObras);
                vistaAdministrarObras.setLocationRelativeTo( null );
                vistaAdministrarObras.setVisible(true);
                vistaAñadirModificarObras.dispose();
            }else if(vistaAñadirModificarObras.getjButton2() == evento.getSource()){ //Botón añadir / modificar obra
                boolean noErrores = true;

                String nombreDeLaObra;
                String género;
                String resumenTemático;
                short duración = 0;
                String primerActorPrincipal;
                String segundoActorPrincipal;
                float precioDelBoleto = 0;

                nombreDeLaObra= vistaAñadirModificarObras.getjTextField1().getText();
                género = vistaAñadirModificarObras.getjTextField2().getText();
                resumenTemático = vistaAñadirModificarObras.getjTextField3().getText();     
                primerActorPrincipal = vistaAñadirModificarObras.getjTextField5().getText();
                segundoActorPrincipal = vistaAñadirModificarObras.getjTextField6().getText();
                try{
                    duración = Short.parseShort(vistaAñadirModificarObras.getjTextField4().getText());
                }catch(Exception e){
                    JOptionPane.showMessageDialog(vistaAñadirModificarObras.getJOptionPane(), 
                        "La duración se representa solo con enteros y en minutos");
                    noErrores = false;
                }
                try{
                    precioDelBoleto = Float.parseFloat(vistaAñadirModificarObras.getjTextField7().getText());
                }catch(Exception e){
                    JOptionPane.showMessageDialog(vistaAñadirModificarObras.getJOptionPane(), 
                        "El precio se debe representar solo con enteros y decimales");
                    noErrores = false;
                }
                DAOObras daoObras = new DAOObras();
                if(noErrores){
                    try{
                        if(añadir){
                            switch(daoObras.añadirObra(nombreDeLaObra, género, resumenTemático, duración, primerActorPrincipal, segundoActorPrincipal, precioDelBoleto)){
                                case 0:
                                    vistaAñadirModificarObras.getjTextField1().setText("");
                                    vistaAñadirModificarObras.getjTextField2().setText("");
                                    vistaAñadirModificarObras.getjTextField3().setText("");
                                    vistaAñadirModificarObras.getjTextField4().setText("");
                                    vistaAñadirModificarObras.getjTextField5().setText("");
                                    vistaAñadirModificarObras.getjTextField6().setText("");
                                    vistaAñadirModificarObras.getjTextField7().setText("");
                                    JOptionPane.showMessageDialog(vistaAñadirModificarObras.getJOptionPane(), 
                                        "Obra añadida exitosamente");
                                    break;
                                case 1:
                                    JOptionPane.showMessageDialog(vistaAñadirModificarObras.getJOptionPane(), 
                                        "Faltan datos por ingresar");
                                    break;
                                case 2:
                                    JOptionPane.showMessageDialog(vistaAñadirModificarObras.getJOptionPane(), 
                                        "Ya existe una obra con esos datos");
                                    break;
                            }
                        }else{
                            switch(daoObras.modificarObra(this.index, nombreDeLaObra, género, resumenTemático, duración, primerActorPrincipal, segundoActorPrincipal, precioDelBoleto)){
                                case 0:
                                    JOptionPane.showMessageDialog(vistaAñadirModificarObras.getJOptionPane(), 
                                        "Obra modificada exitosamente");
                                    break;
                                case 1:
                                    JOptionPane.showMessageDialog(vistaAñadirModificarObras.getJOptionPane(), 
                                        "Faltan datos por ingresar");
                                    break;
                                case 2:
                                    JOptionPane.showMessageDialog(vistaAñadirModificarObras.getJOptionPane(), 
                                        "Ya existe una obra con esos datos");
                                    break;
                            }
                        }
                    }catch(Exception e){
                        System.out.printf("Archivo Usuarios.txt no se encuentra.");
                    }
                }
            }
        }
    }
    
    public void mostrarLista(){
        DAOObras daoObras = new DAOObras();
        int numFilas = daoObras.getListaObras().size();
        JTable Tabla = vistaAdministrarObras.getJtable();
        DefaultTableModel model =  (DefaultTableModel) Tabla.getModel();
        model.setRowCount(0); //reiniciamos
        model.setRowCount(numFilas); 

        for (int i = 0; i <= numFilas; i++){
            try{
                //Para cada fila
                Tabla.setValueAt(i,i, 0);
                Tabla.setValueAt(daoObras.getListaObras().get(i).getNombreDeLaObra(),i, 1);
                Tabla.setValueAt(daoObras.getListaObras().get(i).getGénero(),i, 2);
                Tabla.setValueAt(daoObras.getListaObras().get(i).getDuración(),i, 3);
                Tabla.setValueAt(daoObras.getListaObras().get(i).getPrimerActorPrincipal(),i, 4);
                Tabla.setValueAt(daoObras.getListaObras().get(i).getSegundoActorPrincipal(),i, 5);
                Tabla.setValueAt(daoObras.getListaObras().get(i).getPrecioDelBoleto(),i, 6);
            }catch (Exception e){
                
            }
        }
    }
    
    public void mostrarDatosPrevios(){
        DAOObras daoObras = new DAOObras();
        try{
        vistaAñadirModificarObras.getjTextField1().setText(daoObras.getListaObras().get(index).getNombreDeLaObra());
        vistaAñadirModificarObras.getjTextField2().setText(daoObras.getListaObras().get(index).getGénero());
        vistaAñadirModificarObras.getjTextField3().setText(daoObras.getListaObras().get(index).getResumenTemático());
        vistaAñadirModificarObras.getjTextField4().setText(Short.toString(daoObras.getListaObras().get(index).getDuración()));
        vistaAñadirModificarObras.getjTextField5().setText(daoObras.getListaObras().get(index).getPrimerActorPrincipal());
        vistaAñadirModificarObras.getjTextField6().setText(daoObras.getListaObras().get(index).getSegundoActorPrincipal());
        vistaAñadirModificarObras.getjTextField7().setText(Float.toString(daoObras.getListaObras().get(index).getPrecioDelBoleto()));
        }catch(Exception e){
            System.out.print(index);
        }
    }
}
