/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import javax.swing.JOptionPane;
/**
 *
 * @author saat
 */
public class modelo {
    private String ruta="";
    //usado solo para devolver ruta automatica
    private String respruta;
    private List<Parametrosapache_acceslog>lista1=new ArrayList<>();
    private List<Parametrosapache_acceslog>lista2=new ArrayList<>();
    private List<Parametrosapache_acceslog>lista3=new ArrayList<>();
    private String tipo;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    
    
    public String getRespruta() {
        return respruta;
    }

    public void setRespruta(String respruta) {
        this.respruta = respruta;
    }
    

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
    public String [] getencabezadoacces_log(){
            String[] encabezado = new String[]{
                    "IP",
                    "Cuenta",
                    "Fecha y Hora",
                    "Método",
                    "Ruta",
                    "Protocolo",
                    "Estado",
                    "Respuesta",
                    "Referer",
                    "Sistema Operativo",
                    "Navegador"
                    };
        return encabezado;    
            
    }
     public String [] getencabezadoerror_log(){
            String[] encabezado = new String[]{
                    "IP",
                    "Cuenta",
                    "Fecha y Hora",
                    "Método"
            };
        return encabezado;    
            
    }
      public String [] getencabezadoFTP(){
            String[] encabezado = new String[]{
                    "IP",
                    "Cuenta",
                   
                    };
        return encabezado;    
            
    }
    //codigo para parametrizar logs
    public  List<Parametrosapache_acceslog> leerlogacces_log(){
        try{
            BufferedReader lector=new BufferedReader(new FileReader(ruta));
            String linea="";
            
            while((linea=lector.readLine())!=null){
                String[]bloques=linea.split(" ");
                if(bloques.length==18){
                    String IP = bloques[0];
                    String cuenta = bloques[2];
                    String fechaHora =bloques[3]; 
                    String metodo = bloques[5];
                    String Ruta = bloques[6];
                    String protocolo = bloques[7];
                    String estado =bloques[8];  
                    String respuesta = bloques[9]; 
                    String referer = bloques[10];
                    String SO = bloques[13];
                    String navegador = bloques[17];
                    lista1.add(new Parametrosapache_acceslog(IP,cuenta,fechaHora,metodo,Ruta,protocolo,estado,respuesta,referer,SO,navegador));
                }
            }
            lector.close();
        }catch(IOException e){
            JOptionPane.showMessageDialog(null,"Error al leer el archivo"+e.getMessage());
        
        }
        return lista1;
    }
    public  List<Parametrosapache_acceslog> leerlogerror_log(){
        try{
            BufferedReader lector=new BufferedReader(new FileReader(ruta));
            String linea="";
            
            while((linea=lector.readLine())!=null){
                String[]bloques=linea.split(" ");
                if(bloques.length==18){
                  
                }
            }
            lector.close();
        }catch(IOException e){
            System.out.println("Error al leer el archivo"+e.getMessage());
        
        }
        return lista2;
    }
    public  List<Parametrosapache_acceslog> leerlogFTP(){
        try{
            BufferedReader lector=new BufferedReader(new FileReader(ruta));
            String linea="";
            
            while((linea=lector.readLine())!=null){
                String[]bloques=linea.split(" ");
                if(bloques.length==18){
                 
                }
            }
            lector.close();
        }catch(IOException e){
            System.out.println("Error al leer el archivo"+e.getMessage());
        
        }
        return lista3;
    }
    
    //autogenerear texto 
    public String analizajbox(){
       
        if(ruta == "Apache"){
            respruta="var/log/apache";
        }else{
            if(ruta == "FTP"){
                respruta="var/log/vsfpd_log";
            }
        }

       return this.respruta;
    
    }
   
}
