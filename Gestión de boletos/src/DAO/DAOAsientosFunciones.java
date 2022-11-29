package DAO;

import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import modelo.Función;;
import DAO.DAOFunciones;
import modelo.AsientosFunción;
import modelo.ObraTeatral;
import DAO.DAOFunciones;
import DAO.DAOObras;
import modelo.ObraTeatral;

public class DAOAsientosFunciones{
    private ArrayList<AsientosFunción> lista; //Lista de los asientos de funciones proximas
    private String fechaActual;
    
    public DAOAsientosFunciones() {
        lista = new ArrayList<AsientosFunción>();
        AsientosFunción aux = null;
        
        //Creamos una lista con las funciones que están por presentarse
        DAOFunciones daoFunciones =  new DAOFunciones();
        int auxIndex = daoFunciones.getAuxIndex();
        if(!daoFunciones.getListaFunciones().isEmpty()){
            for(int i=auxIndex; i<daoFunciones.getListaFunciones().size(); i++){ //Para cada función que está proxima
                String datosFunción = daoFunciones.getListaFunciones().get(i).getNombreDeLaObra() + " / " + daoFunciones.getListaFunciones().get(i).getFecha() + " / " + daoFunciones.getListaFunciones().get(i).getHora();
                aux = new AsientosFunción(daoFunciones.getListaFunciones().get(i)); //Creamos un temporal cuyo primer dato será un recopilatorio de los datos de la obra en cuestión
                //Leemos el fichero de funciones para localizar si alguna función emn el fichero previo a modificar ya tiene valores previos asignados
                String SEPARADOR = ";";
                BufferedReader bufferLectura = null;
                try {
                    bufferLectura = new BufferedReader(new FileReader("test/AsientosFunciones.txt"));   // Abrir el .txt en buffer de lectura
                    String linea = bufferLectura.readLine();    // Leer una linea del archivo
                    while (linea != null) {
                        String[] campos = linea.split(SEPARADOR); 
                        if(aux.getDatosFunción().equals(campos[0])){ //Si ya los datos nuevos equivalen a unos previos, copia su distribución de asientos
                            for(int j=0; j<20; j++){
                                if(campos[j+1].equals("1")){
                                    aux.ocuparAsiento(j);
                                }
                            }
                            break;
                        }
                        // Volver a leer otra línea del fichero
                        linea = bufferLectura.readLine();
                    }
                }catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if (bufferLectura != null) {
                        try {
                            bufferLectura.close();
                        } 
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                lista.add(aux);
            }
        }
        
            
        try{ //Imprimimos la lista actualizada al día
            FileWriter writer = new FileWriter("test/AsientosFunciones.txt", false);
            if(daoFunciones.getListaFunciones().isEmpty()){
                writer.write("");
            }else{
                for(int i=0; i < lista.size(); i++){
                    writer.write(lista.get(i).getDatosFunción() + ";" + lista.get(i).getAsiento(0) + ";" + lista.get(i).getAsiento(1) + ";" + lista.get(i).getAsiento(2) + ";" + lista.get(i).getAsiento(3) + ";" + lista.get(i).getAsiento(4) + ";" + lista.get(i).getAsiento(5) + ";" + lista.get(i).getAsiento(6) + ";" + lista.get(i).getAsiento(7) + ";" + lista.get(i).getAsiento(8) + ";" + lista.get(i).getAsiento(9) + ";" + lista.get(i).getAsiento(10) + ";" + lista.get(i).getAsiento(11) + ";" + lista.get(i).getAsiento(12) + ";" + lista.get(i).getAsiento(13) + ";" + lista.get(i).getAsiento(14) + ";" + lista.get(i).getAsiento(15) + ";" + lista.get(i).getAsiento(16) + ";" + lista.get(i).getAsiento(17) + ";" + lista.get(i).getAsiento(18) + ";" + lista.get(i).getAsiento(19));
                    writer.write("\r\n");   // Escribir nueva linea
                }
            }
            writer.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void venderAsientos(boolean[] nuevasVentas, int index){
        for(int i=0; i<20; i++){
            if(nuevasVentas[i]){
                lista.get(index).ocuparAsiento(i);
            }
        }
        try{ //Imprimimos la lista actualizada
            FileWriter writer = new FileWriter("test/AsientosFunciones.txt", false);
            for(int i=0; i < lista.size(); i++){
                writer.write(lista.get(i).getDatosFunción() + ";" + lista.get(i).getAsiento(0) + ";" + lista.get(i).getAsiento(1) + ";" + lista.get(i).getAsiento(2) + ";" + lista.get(i).getAsiento(3) + ";" + lista.get(i).getAsiento(4) + ";" + lista.get(i).getAsiento(5) + ";" + lista.get(i).getAsiento(6) + ";" + lista.get(i).getAsiento(7) + ";" + lista.get(i).getAsiento(8) + ";" + lista.get(i).getAsiento(9) + ";" + lista.get(i).getAsiento(10) + ";" + lista.get(i).getAsiento(11) + ";" + lista.get(i).getAsiento(12) + ";" + lista.get(i).getAsiento(13) + ";" + lista.get(i).getAsiento(14) + ";" + lista.get(i).getAsiento(15) + ";" + lista.get(i).getAsiento(16) + ";" + lista.get(i).getAsiento(17) + ";" + lista.get(i).getAsiento(18) + ";" + lista.get(i).getAsiento(19));
                writer.write("\r\n");   // Escribir nueva linea
            }
            writer.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public ArrayList<AsientosFunción> getListaDeObrasConFunciones(){
        return lista;
    }
}