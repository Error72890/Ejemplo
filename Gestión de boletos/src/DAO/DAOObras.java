package DAO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import modelo.ObraTeatral;

public class DAOObras{
private ArrayList<ObraTeatral> lista;
    
    public DAOObras() {
        this.lista = new ArrayList<ObraTeatral>();
        ObraTeatral aux;
        
        String SEPARADOR = ";";
        BufferedReader bufferLectura = null;
        try {
            // Abrir el .txt en buffer de lectura
            bufferLectura = new BufferedReader(new FileReader("test/Obras.txt"));
            // Leer una linea del archivo
            String linea = bufferLectura.readLine();
            while (linea != null) {
                // Sepapar la linea leída con el separador definido previamente
                String[] campos = linea.split(SEPARADOR); 
                aux = new ObraTeatral(campos[0], campos[1], campos[2], Short.parseShort(campos[3]), campos[4], campos[5], Float.parseFloat(campos[6]));
                lista.add(aux);
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
    
    public ArrayList<ObraTeatral> getListaObras(){
        return lista;
    }
    
    public boolean eliminarObra(int index){
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
                FileWriter writer = new FileWriter("test/Obras.txt", false);
                for(int i=0; i < lista.size(); i++){
                    writer.write(lista.get(i).getNombreDeLaObra() + ";" + lista.get(i).getGénero() + ";" + lista.get(i).getResumenTemático() + ";" + lista.get(i).getDuración()+ ";" + lista.get(i).getPrimerActorPrincipal() + ";" + lista.get(i).getSegundoActorPrincipal() + ";" + lista.get(i).getPrecioDelBoleto());
                    writer.write("\r\n");   // Escribir nueva linea
                }
                writer.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
                
        return deleted;
    }
    
    public int modificarObra(int index, String nombreDeLaObra, String género, String resumenTemático, short duración, String primerActorPrincipal, String segundoActorPrincipal, float precioDelBoleto){
        ObraTeatral nuevosDatos;
        boolean faltanDatos = false, obrasIdenticas = false;
        if(nombreDeLaObra.equals("") || género.equals("") || resumenTemático.equals("") || Short.toString(duración).equals("") || primerActorPrincipal.equals("") || segundoActorPrincipal.equals("") || Float.toString(precioDelBoleto).equals("")){
            faltanDatos = true;
        }else{
            faltanDatos = false;
            for(int i=0; i < lista.size(); i++){ 
                //Solo compara el nombre y los actores principales
                if(i!=index && nombreDeLaObra.equals(lista.get(i).getNombreDeLaObra()) && primerActorPrincipal.equals(lista.get(i).getPrimerActorPrincipal()) && segundoActorPrincipal.equals(lista.get(i).getSegundoActorPrincipal())){
                    obrasIdenticas = true;
                }
            }
            if(!obrasIdenticas){
                nuevosDatos = new ObraTeatral(nombreDeLaObra, género, resumenTemático, duración, primerActorPrincipal, segundoActorPrincipal, precioDelBoleto);
                lista.set(index, nuevosDatos);
            }
        }
        //Imprimir al .txt
        if(!faltanDatos){
            if(!obrasIdenticas){
                try{
                    FileWriter writer = new FileWriter("test/Obras.txt", false);
                    for(int i=0; i < lista.size(); i++){
                        writer.write(lista.get(i).getNombreDeLaObra() + ";" + lista.get(i).getGénero() + ";" + lista.get(i).getResumenTemático() + ";" + lista.get(i).getDuración()+ ";" + lista.get(i).getPrimerActorPrincipal() + ";" + lista.get(i).getSegundoActorPrincipal() + ";" + lista.get(i).getPrecioDelBoleto());
                        writer.write("\r\n");   // Escribir nueva linea
                    }
                    writer.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }
                return 0; //Añadido exito
            }else{
                return 2; //Existe obra con ese nombre
            }
        }else{
            return 1; //Faltan datos
        }
    }
    
    public int añadirObra(String nombreDeLaObra, String género, String resumenTemático, short duración, String primerActorPrincipal, String segundoActorPrincipal, float precioDelBoleto){
        boolean faltanDatos = false, obrasIdenticas = false;
        if(nombreDeLaObra.equals("") || género.equals("") || resumenTemático.equals("") || Short.toString(duración).equals("") || primerActorPrincipal.equals("") || segundoActorPrincipal.equals("") || Float.toString(precioDelBoleto).equals("")){
            faltanDatos = true;
        }else{
            faltanDatos = false;
            for(int i=0; i < lista.size(); i++){ 
                //Solo compara el nombre y los actores principales
                if(nombreDeLaObra.equals(lista.get(i).getNombreDeLaObra()) && primerActorPrincipal.equals(lista.get(i).getPrimerActorPrincipal()) && segundoActorPrincipal.equals(lista.get(i).getSegundoActorPrincipal())){
                    obrasIdenticas = true;
                }
            }
            if(!obrasIdenticas){
                lista.add(new ObraTeatral(nombreDeLaObra, género, resumenTemático, duración, primerActorPrincipal, segundoActorPrincipal, precioDelBoleto));
            }
        }
        //Imprimir al .txt
        if(!faltanDatos){
            if(!obrasIdenticas){
                try{
                    FileWriter writer = new FileWriter("test/Obras.txt", false);
                    for(int i=0; i < lista.size(); i++){
                        writer.write(lista.get(i).getNombreDeLaObra() + ";" + lista.get(i).getGénero() + ";" + lista.get(i).getResumenTemático() + ";" + lista.get(i).getDuración()+ ";" + lista.get(i).getPrimerActorPrincipal() + ";" + lista.get(i).getSegundoActorPrincipal() + ";" + lista.get(i).getPrecioDelBoleto());
                        writer.write("\r\n");   // Escribir nueva linea
                    }
                    writer.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }
                return 0; //Añadido exito
            }else{
                return 2; //Existe obra con ese nombre
            }
        }else{
            return 1; //Faltan datos
        }
    }
}
