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
    private List<Parametros_vsftpd>lista3=new ArrayList<>();
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
    
    
    //vsftpd jhoel
    public  List<Parametros_vsftpd> leerLogVsftpd(){
        try {
            BufferedReader lector = new BufferedReader(new FileReader(ruta));
            String linea = "";
            
            while ((linea = lector.readLine()) != null) {
                // Procesar diferentes tipos de líneas del log
                if (linea.contains("CONNECT") || linea.contains("LOGIN")) {
                    procesarConexion(linea);
                } else if (linea.contains("UPLOAD") || linea.contains("DOWNLOAD")) {
                    procesarTransferencia(linea);
                } else if (linea.contains("COMMAND")) {
                    procesarComando(linea);
                }
            }
            lector.close();
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        return lista3;
    }
    
    private void procesarConexion(String linea) {
        String[] bloques = linea.split("\\[|\\]|\"");
        
        if (bloques.length >= 6) {
            String fechaHora = bloques[0].trim();
            String pid = bloques[1].replace("pid", "").trim();
            String usuario = bloques[2].trim();
            String estado = bloques[3].trim().split(" ")[0]; // OK o FAIL
            String tipoEvento = bloques[3].trim().split(" ")[1]; // CONNECT o LOGIN
            String IP = bloques[5].trim();
            
            lista3.add(new Parametros_vsftpd(
                IP, 
                usuario, 
                fechaHora, 
                null, // comando
                null, // respuesta
                null, // archivo
                null, // tamaño
                null, // tiempoTransferencia
                null, // velocidad
                estado,
                tipoEvento
            ));
        }
    }
    
    private void procesarTransferencia(String linea) {
        String[] bloques = linea.split("\\[|\\]|\"|,");
        
        if (bloques.length >= 9) {
            String fechaHora = bloques[0].trim();
            String pid = bloques[1].replace("pid", "").trim();
            String usuario = bloques[2].trim();
            String estado = bloques[3].trim().split(" ")[0]; // OK o FAIL
            String tipoEvento = bloques[3].trim().split(" ")[1]; // UPLOAD o DOWNLOAD
            String IP = bloques[5].trim();
            String archivo = bloques[7].trim();
            String[] datosTransferencia = bloques[8].trim().split(" ");
            String tamaño = datosTransferencia[0] + " " + datosTransferencia[1];
            String velocidad = datosTransferencia.length > 3 ? 
                datosTransferencia[2] + " " + datosTransferencia[3] : "";
            
            lista3.add(new Parametros_vsftpd(
                IP, 
                usuario, 
                fechaHora, 
                null, // comando
                null, // respuesta
                archivo, 
                tamaño, 
                null, // tiempoTransferencia
                velocidad, 
                estado,
                tipoEvento
            ));
        }
    }
    
    private void procesarComando(String linea) {
        String[] bloques = linea.split("\\[|\\]|\"");
        
        if (bloques.length >= 8) {
            String fechaHora = bloques[0].trim();
            String pid = bloques[1].replace("pid", "").trim();
            String usuario = bloques[2].trim();
            String estado = bloques[3].trim().split(" ")[0]; // OK o FAIL
            String tipoEvento = bloques[3].trim().split(" ")[1]; // COMMAND
            String IP = bloques[5].trim();
            String comando = bloques[7].trim();
            
            lista3.add(new Parametros_vsftpd(
                IP, 
                usuario, 
                fechaHora, 
                comando, 
                null, // respuesta
                null, // archivo
                null, // tamaño
                null, // tiempoTransferencia
                null, // velocidad
                estado,
                tipoEvento
            ));
        }
    }
    
    //autogenerear texto 
    public String analizajbox(){
       
        if (ruta.equalsIgnoreCase("Apache")) {
            respruta = "/var/log/apache";
        } else if (ruta.equalsIgnoreCase("FTP")) {
            respruta = "/var/log/vsftpd.log";
        } else if (ruta.equalsIgnoreCase("Error")) {
            respruta = "/var/log/error.log";
        }
        return this.respruta;
    
    }
   
}
