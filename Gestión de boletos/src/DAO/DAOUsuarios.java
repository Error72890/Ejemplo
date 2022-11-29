package DAO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import modelo.Usuario;

public class DAOUsuarios{
private ArrayList<Usuario> lista;
    
    public DAOUsuarios() {
        this.lista = new ArrayList<Usuario>();
        Usuario aux;
        
        String SEPARADOR = ",";
        BufferedReader bufferLectura = null;
        try {
            // Abrir el .txt en buffer de lectura
            bufferLectura = new BufferedReader(new FileReader("test/Usuarios.txt"));
            // Leer una linea del archivo
            String linea = bufferLectura.readLine();
            while (linea != null) {
                // Sepapar la linea leída con el separador definido previamente
                String[] campos = linea.split(SEPARADOR); 
                aux = new Usuario(campos[0], campos[1], campos[2], campos[3], campos[4]);
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
    public short buscarExistencia(String usuario, String contraseña){
        if(usuario.equals("Admin") && contraseña.equals("Admin")){
            return 0;
        }
        boolean existeCuentaConNombre = false;
        for(int i=0; i< lista.size(); i++){
            if(usuario.equals(lista.get(i).getNombreDeUsuario())){
                if(contraseña.equals(lista.get(i).getContraseña())){
                    return 0;
                }
                else{
                    existeCuentaConNombre = true;
                }
            }
        }
        if(existeCuentaConNombre){
            return 2;
        }else{
            return 1;
        }
    }
    
    public boolean eliminarUsuario(String nombre, String apellidos, String curp){
        boolean deleted = false;
        for(int i=0; i < lista.size(); i++){
            if(nombre.equals(lista.get(i).getNombre()) && apellidos.equals(lista.get(i).getApellidos()) && curp.equals(lista.get(i).getCurp())){
                lista.remove(i);
                deleted = true;
                break;
            }
        }
        //Imprimir al .txt
        if(deleted){
            try{
                FileWriter writer = new FileWriter("test/Usuarios.txt", false);
                for(int i=0; i < lista.size(); i++){
                    writer.write(lista.get(i).getNombre() + "," + lista.get(i).getApellidos() + "," + lista.get(i).getCurp() + "," + lista.get(i).getNombreDeUsuario ()+ "," + lista.get(i).getContraseña());
                    writer.write("\r\n");   // Escribir nueva linea
                }
                writer.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        return deleted;
    }
    
    public int modificarUsuario(String nombre, String apellidos, String curp, String nombreNuevo, String apellidoNuevo, String curpNuevo, String nombreUsuarioNuevo, String contraseñaNueva){
        boolean existeCuentaConNombre = false;
        for(int i=0; i < lista.size(); i++){
            if(nombre.equals(lista.get(i).getNombre()) && apellidos.equals(lista.get(i).getApellidos()) && curp.equals(lista.get(i).getCurp())){
                existeCuentaConNombre = true;
                if(nombreNuevo.equals("") || apellidoNuevo.equals("") || curpNuevo.equals("") || nombreUsuarioNuevo.equals("") || contraseñaNueva.equals("")){
                    return 1; //Faltan datos
                }
                for(int j=0; j < lista.size(); j++){
                    if(i!=j && nombreUsuarioNuevo.equals(lista.get(j).getNombreDeUsuario())){
                        return 2; // Existe cuenta con ese nombre de usuario
                    }else if(i!=j && curpNuevo.equals(lista.get(j).getCurp())){
                        return 3; // Existe cuenta con ese curp
                    }
                    
                    Usuario nuevosDatos = new Usuario(nombreNuevo, apellidoNuevo, curpNuevo, nombreUsuarioNuevo, contraseñaNueva);
                    lista.set(i, nuevosDatos);
                    break;
                }
            }
        }
        if(!existeCuentaConNombre){
            return 4; //No existe una cuenta con ese nombre
        }
        //Imprimir al .txt
        try{
            FileWriter writer = new FileWriter("test/Usuarios.txt", false);
            for(int i=0; i < lista.size(); i++){
                writer.write(lista.get(i).getNombre() + "," + lista.get(i).getApellidos() + "," + lista.get(i).getCurp() + "," + lista.get(i).getNombreDeUsuario ()+ "," + lista.get(i).getContraseña());
                writer.write("\r\n");   // Escribir nueva linea
            }
                writer.close();
            }catch (IOException e) {
                e.printStackTrace();
        }
        return 0; //Modificado con exito
    }
    
    public int añadirUsuario(String nombre, String apellidos, String curp, String nombreUsuario, String contraseña){
        if(nombre.equals("") || apellidos.equals("") || curp.equals("") || nombreUsuario.equals("") || contraseña.equals("")){
            return 1; //Faltan datos
        }
        for(int i=0; i < lista.size(); i++){
            if(nombreUsuario.equals(lista.get(i).getNombreDeUsuario())){
                    return 2; // Existe cuenta con ese nombre de usuario
            }else if(curp.equals(lista.get(i).getCurp())){
                    return 3; // Existe cuenta con ese curp
            }
        }
        lista.add(new Usuario(nombre, apellidos, curp, nombreUsuario, contraseña));
        
        //Imprimir al .txt
        try{
            FileWriter writer = new FileWriter("test/Usuarios.txt", false);
            for(int i=0; i < lista.size(); i++){
                writer.write(lista.get(i).getNombre() + "," + lista.get(i).getApellidos() + "," + lista.get(i).getCurp() + "," + lista.get(i).getNombreDeUsuario ()+ "," + lista.get(i).getContraseña());
                writer.write("\r\n");   // Escribir nueva linea
            }
            writer.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return 0; //Añadido exito
    }
}
