package DAO;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import modelo.Función;

public class DAOTicketBoletos{
    int númeroDeVenta;
    String númeroDeBoleto; //En este caso será el número de asiento 
    
    Función funciónTeatral;
    String fechaDeVenta;
    String horaDeVenta;
    int boletosVendidos;
    float total;
    float montoEntregado;
    float cambio;
    boolean[] asientosAImprimir;
    
    public DAOTicketBoletos(Función función, int boletosVendidos, float total, float montoEntregado, float cambio,  boolean[] asientosAImprimir) {
        this.funciónTeatral = función;
        this.boletosVendidos = boletosVendidos;
        this.total = total;
        this.montoEntregado = montoEntregado;
        this.cambio = cambio;
        
        Calendar c = Calendar.getInstance();
        String anio = Integer.toString(c.get(Calendar.YEAR));
        int mesReal = c.get(Calendar.MONTH) + 1;
        String mes = Integer.toString(mesReal);
        String dia = Integer.toString(c.get(Calendar.DATE));
        if(mes.length() != 1 && dia.length() != 1){
            this.fechaDeVenta = (anio + "-" + mes + "-" + dia);
        }else if(mes.length() != 1 && dia.length() == 1){
            this.fechaDeVenta = (anio + "-" + mes + "-0" + dia);
        }else if(mes.length() == 1 && dia.length() != 1){
            this.fechaDeVenta = (anio + "-0" + mes + "-" + dia);
        }else{
            this.fechaDeVenta = (anio + "-0" + mes + "-0" + dia);
        }
        
        String horas = Integer.toString(c.get(Calendar.HOUR));
        String minutos = Integer.toString(c.get(Calendar.MINUTE));
        horaDeVenta = horas + ":" + minutos;
        
        DAOVentas daoVentas = new DAOVentas();
        númeroDeVenta = daoVentas.getNúmeroDeVenta();
        
        this.asientosAImprimir =  asientosAImprimir;
    }
    
    public void imprimirTicket(){
        try{ //Imprimimos un ticket nuevo
            String ticketNombre = "Ticket" + númeroDeVenta;
            FileWriter writer = new FileWriter("test/Tickets impresos/"+ ticketNombre + ".txt", false);
                writer.write("Teatro Chachita \r\n");
                writer.write("Venta número: " + númeroDeVenta + "\r\n");
                writer.write("Función teatral: " + funciónTeatral.getNombreDeLaObra() + "\r\n");
                writer.write("Fecha y hora de venta: " + fechaDeVenta + " " + horaDeVenta +  "\r\n");
                writer.write("Boletos vendidos: " + boletosVendidos + "\r\n");
                writer.write("Total: " + total + "\r\n");
                writer.write("Monto entregado: " + montoEntregado + "\r\n");
                writer.write("Cambio: " + cambio + "\r\n");
            writer.close();
            DAOVentas daoVentas = new DAOVentas();
            daoVentas.actualizarNumeroDeVenta();
            daoVentas.agregarVenta(funciónTeatral.getNombreDeLaObra(), fechaDeVenta, Integer.toString(boletosVendidos), Float.toString(total));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void imprimirBoletos(){
        for(int i=0; i<asientosAImprimir.length; i++){
            if(asientosAImprimir[i]){
                String asiento;
                int aux;
                switch(i){
                    default:
                        aux = i+1;
                        asiento = ("A" + Integer.toString(aux));
                        break;
                    case 5: case 6: case 7: case 8: case 9:
                        aux = i-4;
                        asiento = ("B" + Integer.toString(aux));
                        break;
                    case 10: case 11: case 12: case 13: case 14:
                        aux = i-9;
                        asiento = ("C" + Integer.toString(aux));
                        break;
                    case 15: case 16: case 17: case 18: case 19:
                        aux = i-14;
                        asiento = ("D" + Integer.toString(aux));
                        break;
                }
                try{ //Imprimimos los boletos
                    String fechaYHoraFunción = funciónTeatral.getFecha() + " " + funciónTeatral.getHora();
                    FileWriter writer = new FileWriter("test/Boletos impresos/" + funciónTeatral.getFecha() + " " + asiento + ".txt", true);
                    writer.write("Función teatral: " + funciónTeatral.getNombreDeLaObra() + "\r\n");
                    writer.write("Fecha y hora de la función: " + fechaYHoraFunción +  "\r\n");
                    writer.write("Número del boleto (Asiento): " + asiento +  "\r\n");
                    writer.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
    }
}