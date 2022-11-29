package modelo;


public class Usuario{
    String nombre;
    String apellidos;
    String curp;
    String nombreDeUsuario;
    String contraseña;

    public Usuario(String nombre, String apellidos, String curp, String nombreDeUsuario, String contraseña){
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.curp = curp;
        this.nombreDeUsuario = nombreDeUsuario;
        this.contraseña = contraseña;
    }
    
    public Usuario(){
        
    }
    
    public void setNombreDeUsuario(String nombreDeUsuario){
        this.nombreDeUsuario = nombreDeUsuario;
    }
    
    public void setContraseña(String contraseña){
        this.contraseña = contraseña;
    }

    public String getNombre(){
        return nombre;
    }
    
    public String getApellidos(){
        return apellidos;
    }
    
    public String getCurp(){
        return curp;
    }
    
    public String getNombreDeUsuario(){
        return nombreDeUsuario;
    }
    
    public String getContraseña(){
        return contraseña;
    }
}
