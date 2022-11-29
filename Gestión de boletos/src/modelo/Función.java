package modelo;

import modelo.ObraTeatral;


public class Función{
    ObraTeatral Obra;
    String fecha;
    String hora;

    public Función(ObraTeatral Obra, String fecha, String hora){
        this.Obra = Obra;
        this.fecha = fecha;
        this.hora = hora;
    }
    
    public Función(){
        
    }
    
    public void setNombreDeLaObra(ObraTeatral Obra){
        this.Obra = Obra;
    }
    
    public void setFecha(String fecha){
        this.fecha = fecha;
    }
    
    public void setHora(String hora){
        this.hora = hora;
    }
    
    public String getNombreDeLaObra(){
        return this.Obra.nombreDeLaObra;
    }
    
    public ObraTeatral getObra(){
        return this.Obra;
    }
    
    public String getFecha(){
        return this.fecha;
    }
    
    public String getHora(){
        return this.hora;
    }
}
