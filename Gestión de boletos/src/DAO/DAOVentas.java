package DAO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import modelo.ObraTeatral;
import modelo.Venta;

public class DAOVentas{
    private int contadorNúmeroDeVenta;
    private ArrayList<Venta> listaVentasCompleta;
    private ArrayList<Venta> listaDiasNoRepetidos;
    private ArrayList<Venta> listaMesesNoRepetidos;
    
    public DAOVentas() {        
        listaVentasCompleta = new ArrayList<Venta>();
        listaDiasNoRepetidos = new ArrayList<Venta>();
        listaMesesNoRepetidos = new ArrayList<Venta>();
        
        String SEPARADOR = ",";
        BufferedReader bufferLectura = null;
        try {
            // Abrir el .txt en buffer de lectura
            bufferLectura = new BufferedReader(new FileReader("test/Ventas.txt"));
            // Leer una linea del archivo
            String linea = bufferLectura.readLine();
            int lineaActual = 0;
            while (linea != null) {
                // Sepapar la linea leída con el separador definido previamente
                String[] campos = linea.split(SEPARADOR); 
                if(lineaActual == 0){
                    lineaActual++;
                    contadorNúmeroDeVenta = Integer.parseInt(campos[0]);
                }
                else if(lineaActual == 1){
                    lineaActual++;
                    Venta aux = new Venta(campos[0],campos[1],campos[2],campos[3]);
                    listaVentasCompleta.add(aux);
                    listaDiasNoRepetidos.add(aux);
                    listaMesesNoRepetidos.add(aux);
                }else{
                    Venta aux = new Venta(campos[0],campos[1],campos[2],campos[3]);
                    listaVentasCompleta.add(aux);
                    
                    boolean dayIsFound = false, monthIsFound = false;
                    for(int i=0; i<listaDiasNoRepetidos.size(); i++){ 
                        if(aux.getFecha().equals(listaDiasNoRepetidos.get(i).getFecha())){
                            dayIsFound = true;
                        }
                    }
                    for(int i=0; i<listaMesesNoRepetidos.size(); i++){ 
                        String divisor = "-";
                        String[] partesFecha =  aux.getFecha().split(divisor);
                        String fechaSinDia = partesFecha[0] + "-" + partesFecha[1];
                        String[] partesFecha2 =  listaMesesNoRepetidos.get(i).getFecha().split(divisor);
                        String fechaSinDia2 = partesFecha2[0] + "-" + partesFecha2[1];
                        if(fechaSinDia.equals(fechaSinDia2)){
                           monthIsFound = true;
                        }
                    }
                    if(!dayIsFound){
                        listaDiasNoRepetidos.add(aux);
                    }
                    if(!monthIsFound){
                        listaMesesNoRepetidos.add(aux);
                    }
                }
                // Volver a leer otra línea del fichero
                linea = bufferLectura.readLine();
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (bufferLectura != null) {
                try {
                    bufferLectura.close();
                } 
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void imprimirLista(){
        try{ //Imprimimos la lista actualizada al día
            FileWriter writer = new FileWriter("test/Ventas.txt", false);
            writer.write(contadorNúmeroDeVenta + "\r\n");
            if(!listaVentasCompleta.isEmpty()){
                for(int i=0; i<listaVentasCompleta.size(); i++){
                    writer.write(listaVentasCompleta.get(i).getNombre() + "," + listaVentasCompleta.get(i).getFecha() + "," + listaVentasCompleta.get(i).getboletosVendidos() + "," + listaVentasCompleta.get(i).getingresos());
                    writer.write(contadorNúmeroDeVenta + "\r\n");
                }
            }
            writer.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
   
    public void agregarVenta(String nombre, String fecha, String boletosVendidos, String ingresos){
        Venta nuevaVenta = new Venta(nombre, fecha, boletosVendidos, ingresos);
        listaVentasCompleta.add(nuevaVenta);
        boolean isFound = false, monthIsFound = false;
        for(int i=0; i<listaDiasNoRepetidos.size(); i++){ 
            if(nuevaVenta.getFecha().equals(listaDiasNoRepetidos.get(i).getFecha())){
                isFound = true;
            }
        }
        for(int i=0; i<listaMesesNoRepetidos.size(); i++){ 
            String divisor = "-";
            String[] partesFecha =  nuevaVenta.getFecha().split(divisor);
            String fechaSinDia = partesFecha[0] + "-" + partesFecha[1];
            String[] partesFecha2 =  listaMesesNoRepetidos.get(i).getFecha().split(divisor);
            String fechaSinDia2 = partesFecha2[0] + "-" + partesFecha2[1];
            if(fechaSinDia.equals(fechaSinDia2)){
               monthIsFound = true;
            }
        }
        if(!isFound){
            listaDiasNoRepetidos.add(nuevaVenta);
        }
        if(!monthIsFound){
            listaMesesNoRepetidos.add(nuevaVenta);
        }
        imprimirLista();
    }
    
    public void imprimirReporteDia(String fecha){
        try{ //Imprimimos la lista actualizada al día
            float ingresosTotales = 0;
            FileWriter writer = new FileWriter("test/Reportes/VentasDia " + fecha + ".txt", false);
            writer.write("Nombre de la obra - Fecha de venta - Boletos vendidos - Ingresos de la venta \r\n");
            for(int i=0; i<listaVentasCompleta.size(); i++){
                if(fecha.equals(listaVentasCompleta.get(i).getFecha())){
                    ingresosTotales = ingresosTotales + Float.parseFloat(listaVentasCompleta.get(i).getingresos());
                    writer.write(listaVentasCompleta.get(i).getNombre() + " - " + listaVentasCompleta.get(i).getFecha() + " - " + listaVentasCompleta.get(i).getboletosVendidos() + " - $" + listaVentasCompleta.get(i).getingresos());
                    writer.write(contadorNúmeroDeVenta + "\r\n");
                }
            }
            writer.write("Ingresos totales: $" + ingresosTotales +  "\r\n");
            writer.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void imprimirReporteMes(String fecha){
        try{ //Imprimimos la lista actualizada al día
            float ingresosTotales = 0;
            FileWriter writer = new FileWriter("test/Reportes/VentasMes " + fecha + ".txt", false);
            writer.write("Nombre de la obra - Fecha de venta - Boletos vendidos - Ingresos de la venta \r\n");
            for(int i=0; i<listaVentasCompleta.size(); i++){
                String divisor = "-";
                String[] partesFecha =  listaVentasCompleta.get(i).getFecha().split(divisor);
                String fechaSinDia = partesFecha[0] + "-" + partesFecha[1];
                if(fecha.equals(fechaSinDia)){
                    ingresosTotales = ingresosTotales + Float.parseFloat(listaVentasCompleta.get(i).getingresos());
                    writer.write(listaVentasCompleta.get(i).getNombre() + " - " + listaVentasCompleta.get(i).getFecha() + " - " + listaVentasCompleta.get(i).getboletosVendidos() + " - $" + listaVentasCompleta.get(i).getingresos());
                    writer.write(contadorNúmeroDeVenta + "\r\n");
                }
            }
            writer.write("Ingresos totales: $" + ingresosTotales +  "\r\n");
            writer.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void actualizarNumeroDeVenta(){
        contadorNúmeroDeVenta++;
        imprimirLista();
    }
    public int getNúmeroDeVenta(){
        return contadorNúmeroDeVenta;
    }
    
    public ArrayList<Venta> getListaDias(){
        return listaDiasNoRepetidos;
    }
    
    public ArrayList<Venta> getListaMeses(){
        return listaMesesNoRepetidos;
    }
}
