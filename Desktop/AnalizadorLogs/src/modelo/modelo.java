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
/**
 *
 * @author saat
 */
public class modelo {
    private String ruta="";
    //usado solo para devolver ruta automatica
    private String respruta;
    private List<Parametrosapache>lista=new ArrayList<>() ;
    
    
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
    
    //codigo para parametrizar logs
    public  List<Parametrosapache> leerlog(){
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
                    lista.add(new Parametrosapache(IP,cuenta,fechaHora,metodo,Ruta,protocolo,estado,respuesta,referer,SO,navegador));
                }
            }
            lector.close();
        }catch(IOException e){
            System.out.println("Error al leer el archivo"+e.getMessage());
        
        }
        return lista;
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
