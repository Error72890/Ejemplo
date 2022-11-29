package modelo;

public class Venta{
    String nombreObra;
    String fechaDeVenta;
    String ingresos;
    String boletosVendidos;

    public Venta(String nombre, String fecha, String boletosVendidos, String ingresos){
        this.nombreObra = nombre;
        this.fechaDeVenta = fecha;
        this.ingresos = ingresos;
        this.boletosVendidos = boletosVendidos;
    }
    
    public String getNombre(){
        return nombreObra;
    }
    
    public String getFecha(){
        return fechaDeVenta;
    }
    
    public String getboletosVendidos(){
        return boletosVendidos;
    }
    
    public String getingresos(){
        return ingresos;
    }
}
