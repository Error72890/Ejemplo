package modelo;
import modelo.Función;


public class AsientosFunción{
    Función función;
    byte[] asientosOcupados; //0 = libre, 1 = ocupado

    public AsientosFunción(Función función){
        this.función = función;
        this.asientosOcupados = new byte[20];
        for(int i=0; i<20; i++){
            asientosOcupados[i] = 0;
        }
    }
    
    public String getDatosFunción(){
        String datos = función.getNombreDeLaObra() + " / " + función.getFecha() + " / " + función.getHora();
        return datos;
    }
    
    public Función getFunción(){
        return this.función;
    }
    
    public byte getAsiento(int index){
        return this.asientosOcupados[index];
    }
    
    public void ocuparAsiento(int index){
        this.asientosOcupados[index] = 1;
    }
}
