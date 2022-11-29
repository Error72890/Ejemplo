package DAO;

import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import modelo.Función;
import DAO.DAOObras;
import modelo.ObraTeatral;

public class DAOFunciones{
private ArrayList<Función> lista;
private ArrayList<Función> funcionesActivas;
private int auxIndex = -1; //El index del primer elemento de funciones activas dentro de la lista completa
private String fechaActual;
    
    public DAOFunciones() {
        lista = new ArrayList<Función>();
        funcionesActivas = new ArrayList<Función>();
        Función aux = null;
        
        String SEPARADOR = ";";
        BufferedReader bufferLectura = null;
        try {
            // Abrir el .txt en buffer de lectura
            bufferLectura = new BufferedReader(new FileReader("test/Funciones.txt"));
            // Leer una linea del archivo
            String linea = bufferLectura.readLine();
            DAOObras daoObras =  new DAOObras();
            while (linea != null) {
                // Sepapar la linea leída con el separador definido previamente
                String[] campos = linea.split(SEPARADOR); 
                for(int i=0; i<daoObras.getListaObras().size(); i++){ //Localizamos la obra en la lista
                    if(campos[0].equals(daoObras.getListaObras().get(i).getNombreDeLaObra())){
                        aux = new Función(daoObras.getListaObras().get(i), campos[1], campos[2]);
                        break;
                    }
                }
                lista.add(aux);
                // Volver a leer otra línea del fichero
                linea = bufferLectura.readLine();
            }
            
            sortList(); //Reordenamos la lista
            
            try{ //Imprimimos la lista ya ordenada
                FileWriter writer = new FileWriter("test/Funciones.txt", false);
                for(int i=0; i < lista.size(); i++){
                    writer.write(lista.get(i).getNombreDeLaObra() + ";" + lista.get(i).getFecha() + ";" + lista.get(i).getHora());
                    writer.write("\r\n");   // Escribir nueva linea
                }
                writer.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
            
            //Creamos una nueva lista solo con las funciones activas (fechas mayores o iguales al día actual) 
            //Esto es para las funciones sobre compra de boletos
            Calendar c = Calendar.getInstance();
            String anio = Integer.toString(c.get(Calendar.YEAR));
            int mesReal = c.get(Calendar.MONTH) + 1;
            String mes = Integer.toString(mesReal);
            String dia = Integer.toString(c.get(Calendar.DATE));
            if(mes.length() != 1 && dia.length() != 1){
                fechaActual = (anio + "-" + mes + "-" + dia);
            }else if(mes.length() != 1 && dia.length() == 1){
                fechaActual = (anio + "-" + mes + "-0" + dia);
            }else if(mes.length() == 1 && dia.length() != 1){
                fechaActual = (anio + "-0" + mes + "-" + dia);
            }else{
                fechaActual = (anio + "-0" + mes + "-0" + dia);
            }
            for(int i=0; i<lista.size(); i++){ //nueva lista solo con las funciones activas
                if(fechaActual.compareTo(lista.get(i).getFecha()) <= 0){
                    funcionesActivas.add(lista.get(i));
                    if(auxIndex == -1){
                        auxIndex = i;
                    }
                }
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
    
    public ArrayList<Función> getListaFunciones(){
        return lista;
    }
   
    public void sortList(){
        Función aux;
        int n = lista.size();
        for(int i=0; i < n; i++){
            for(int j=1; j < (n-i); j++){
                if(lista.get(j-1).getFecha().compareTo(lista.get(j).getFecha()) >  0){
                    System.out.println(lista.get(j-1).getFecha().compareTo(lista.get(j).getFecha()));
                    aux = lista.get(j-1);
                    lista.set(j-1, lista.get(j));
                    lista.set(j, aux);
                }
            }
        }
    }
    
    public boolean eliminarFunción(int index){
        boolean deleted;
        try{
            lista.remove(index);
            deleted = true;
        }catch(Exception e){
            deleted = false;
        }
        //Imprimir al .txt
        if(deleted){
            try{
                FileWriter writer = new FileWriter("test/Funciones.txt", false);
                for(int i=0; i < lista.size(); i++){
                    writer.write(lista.get(i).getNombreDeLaObra() + ";" + lista.get(i).getFecha() + ";" + lista.get(i).getHora());
                    writer.write("\r\n");   // Escribir nueva linea
                }
                writer.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
                
        return deleted;
    }
    
    public int modificarFunción(int index, String nombre, String fecha, String hora){
        Función nuevosDatos;
        ObraTeatral nuevaObra = null;
        boolean fechaPasada = false, obraExiste = false;
        DAOObras daoObras = new DAOObras();
        for(int i = 0; i < daoObras.getListaObras().size(); i++){ //Revisamos que esa obra exista
            if(nombre.compareTo(daoObras.getListaObras().get(i).getNombreDeLaObra())== 0){
                nuevaObra = daoObras.getListaObras().get(i);
                obraExiste = true; 
                break;
            }
        }
        if(!obraExiste){
            return 2;
        }
        //Revisamos que el formato de la fecha sea correcto
        String[] fechaDesglozada = fecha.split("-"); 
        if(fechaDesglozada[0].length()!=4 || fechaDesglozada[1].length()!=2 || fechaDesglozada[2].length()!=2){
            return 3;
        }
        try{ //Comprobamos que sean numéricos y que sean solo 3 parámetros
            int aux = Integer.parseInt(fechaDesglozada[0]);
            int aux1 = Integer.parseInt(fechaDesglozada[1]);
            int aux2 = Integer.parseInt(fechaDesglozada[2]);
            if((aux1 < 1 && aux1 > 12)|| (aux2 < 1 && aux2 > 31)){
                return 3;
            }
        }catch(Exception e){
            return 3;
        }
        try{
            int aux = Integer.parseInt(fechaDesglozada[3]);
            return 3;
        }catch(Exception e){}
        
        //Comprobamos que no exista otra función en la misma fecha y horario
        for(int i=0; i < lista.size(); i++){ 
            if(fecha.equals(lista.get(i).getFecha()) && hora.equals(lista.get(i).getHora())){
                return 4;
            }
        }
        
        nuevosDatos = new Función(nuevaObra, fecha, hora);
        lista.set(index, nuevosDatos);
        if(fechaActual.compareTo(nuevosDatos.getFecha()) > 0){
            fechaPasada = true;
        }
        
        //Imprimir al .txt
        try{
            FileWriter writer = new FileWriter("test/Funciones.txt", false);
            for(int i=0; i < lista.size(); i++){
                writer.write(lista.get(i).getNombreDeLaObra() + ";" + lista.get(i).getFecha() + ";" + lista.get(i).getHora());
                  writer.write("\r\n");   // Escribir nueva linea
            }
             writer.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        if(fechaPasada){
            return 1;// Añadido con exito, pero en una fecha ya pasada
        }else{
            return 0;
        }
    }
    
    public int añadirFunción(String nombre, String fecha, String hora){
        Función nuevosDatos;
        ObraTeatral nuevaObra = null;
        boolean fechaPasada = false, obraExiste=false;
        DAOObras daoObras = new DAOObras();
        for(int i = 0; i < daoObras.getListaObras().size(); i++){ //Revisamos que esa obra exista
            if(nombre.compareTo(daoObras.getListaObras().get(i).getNombreDeLaObra())== 0){
                nuevaObra = daoObras.getListaObras().get(i);
                obraExiste = true; 
                break;
            }
        }
        if(!obraExiste){
            return 2;
        }
        //Revisamos que el formato de la fecha sea correcto
        String[] fechaDesglozada = fecha.split("-"); 
        if(fechaDesglozada[0].length()!=4 || fechaDesglozada[1].length()!=2 || fechaDesglozada[2].length()!=2){
            return 3;
        }
        try{ //Comprobamos que sean numéricos y que sean solo 3 parámetros
            int aux = Integer.parseInt(fechaDesglozada[0]);
            int aux1 = Integer.parseInt(fechaDesglozada[1]);
            int aux2 = Integer.parseInt(fechaDesglozada[2]);
            if((aux1 < 1 && aux1 > 12)|| (aux2 < 1 && aux2 > 31)){
                return 3;
            }
        }catch(Exception e){
            return 3;
        }
        try{
            int aux = Integer.parseInt(fechaDesglozada[3]);
            return 3;
        }catch(Exception e){}
        
        //Comprobamos que no exista otra función en la misma fecha y horario
        for(int i=0; i < lista.size(); i++){ 
            if(fecha.equals(lista.get(i).getFecha()) && hora.equals(lista.get(i).getHora())){
                return 4;
            }
        }
        
        nuevosDatos = new Función(nuevaObra, fecha, hora);
        lista.add(nuevosDatos);
        if(fechaActual.compareTo(nuevosDatos.getFecha()) > 0){
            fechaPasada = true;
        }
        
        //Imprimir al .txt
        try{
            FileWriter writer = new FileWriter("test/Funciones.txt", false);
            for(int i=0; i < lista.size(); i++){
                writer.write(lista.get(i).getNombreDeLaObra() + ";" + lista.get(i).getFecha() + ";" + lista.get(i).getHora());
                  writer.write("\r\n");   // Escribir nueva linea
            }
             writer.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        if(fechaPasada){
            return 1;
        }else{
            return 0;
        }
    }
    
    public int getAuxIndex(){
        return auxIndex;
    }
}
