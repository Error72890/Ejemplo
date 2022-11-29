package modelo;


public class ObraTeatral{
    String nombreDeLaObra;
    String género;
    String resumenTemático;
    short duración;
    String primerActorPrincipal;
    String segundoActorPrincipal;
    float precioDelBoleto;

    public ObraTeatral(String nombreDeLaObra, String género, String resumenTemático, short duración, String primerActorPrincipal, String segundoActorPrincipal, float precioDelBoleto){
        this.nombreDeLaObra = nombreDeLaObra;
        this.género = género;
        this.resumenTemático = resumenTemático;
        this.duración = duración;
        this.primerActorPrincipal = primerActorPrincipal;
        this.segundoActorPrincipal = segundoActorPrincipal;
        this.precioDelBoleto = precioDelBoleto;
    }
    
    public ObraTeatral(){
        
    }
    
    public void setNombreDeLaObra(String nombreDeLaObra){
        this.nombreDeLaObra = nombreDeLaObra;
    }
    
    public void setGénero(String género){
        this.género = género;
    }
    
    public void setResumenTemático(String resumenTemático){
        this.resumenTemático = resumenTemático;
    }
    
    public void setDuración(short duración){
        this.duración = duración;
    }
    
    public void setPrimerActorPrincipal(String primerActorPrincipal){
        this.primerActorPrincipal = primerActorPrincipal;
    }
    
    public void setSegundoActorPrincipal(String segundoActorPrincipal){
        this.segundoActorPrincipal = segundoActorPrincipal;
    }
    
    public void setPrecioDelBoleto(float precioDelBoleto){
        this.precioDelBoleto = precioDelBoleto;
    }
    
    public String getNombreDeLaObra(){
        return this.nombreDeLaObra;
    }
    
    public String getGénero(){
        return this.género;
    }
    
    public String getResumenTemático(){
        return this.resumenTemático;
    }
    
    public short getDuración(){
        return this.duración;
    }
    
    public String getPrimerActorPrincipal(){
        return this.primerActorPrincipal;
    }
    
    public String getSegundoActorPrincipal(){
        return this.segundoActorPrincipal;
    }
    
    public Float getPrecioDelBoleto(){
        return this.precioDelBoleto;
    }
}
