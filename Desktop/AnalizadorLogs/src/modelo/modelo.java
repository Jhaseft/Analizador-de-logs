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
import java.sql.*;
 import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Date;
/**
 *
 * @author saat
 */
public class modelo {
    private String ruta="";
    //usado solo para devolver ruta automatica
    private String respruta;
    private List<Parametrosapache_acceslog>lista1=new ArrayList<>();
    private List<Parametros_errorlog>lista2=new ArrayList<>();
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
                    "Fecha",
                    "Codigo",
                    "Pid",
                    "Comando",
                    "Mensaje"
            };
        return encabezado;  
            
    }
      public String [] getencabezadoFTP(){
            String[] encabezado = new String[]{
                    "Fecha",
                    "Pid",
                    "Comando",
                    "Usuario",
                    "Ip",
                    "Mensaje",
                    };
        return encabezado;    
            
    }
    //codigo para parametrizar logs
    public  List<Parametrosapache_acceslog> leerlogacces_log(){
         try{
            BufferedReader lector=new BufferedReader(new FileReader(ruta));
            String linea="";
            
            while((linea=lector.readLine())!=null){ 
                String normalizar = normalizarlinea(linea);
                String[] bloques = normalizar.split(" "); 
                if(bloques.length>=18){
                    String IP = bloques[0];
                    String cuenta = bloques[2]; 
                    Timestamp fechaHora =convertiracces(bloques[3]); 
                    String metodo = bloques[5];
                    String Ruta = bloques[6];
                    String protocolo = bloques[7];
                    String estado =bloques[8];  
                    String respuesta = bloques[9];  
                    String referer = bloques[10];
                    String SO = bloques[13];
                    String navegador = bloques[17];
                    lista1.add(new Parametrosapache_acceslog(IP,cuenta,fechaHora,metodo,Ruta,protocolo,estado,respuesta,referer,SO,navegador));
                }else{
                    if(bloques.length>=11){ 
                        String IP = bloques[0];
                        String cuenta = bloques[2];
                        Timestamp fechaHora =convertiracces(bloques[3]); 
                        String metodo = bloques[5];
                        String Ruta = bloques[6];
                        String protocolo = bloques[7];
                        String estado =bloques[8];  
                        String respuesta = bloques[9];  
                        String referer = bloques[10];
                        String SO = "";
                        String navegador = bloques[11];
                        lista1.add(new Parametrosapache_acceslog(IP,cuenta,fechaHora,metodo,Ruta,protocolo,estado,respuesta,referer,SO,navegador));  
                    }else{
                         if(bloques.length>=9){
                            String IP = bloques[0];
                            String cuenta = bloques[2];
                            Timestamp fechaHora =convertiracces(bloques[3]); 
                            String metodo = bloques[5];
                            String Ruta = bloques[6];
                            String protocolo = bloques[7];
                            String estado =bloques[8];  
                            String respuesta = bloques[9];  
                            String referer="-";
                            String SO = "-";
                            String navegador = "-";
                            lista1.add(new Parametrosapache_acceslog(IP,cuenta,fechaHora,metodo,Ruta,protocolo,estado,respuesta,referer,SO,navegador));  
                    
                                
                        }else{
                           String IP = bloques[0];
                            String cuenta = "-";
                            Timestamp fechaHora =Timestamp.valueOf("1970-12-31 23:59:59"); 
                            String metodo = "-";
                            String Ruta = "-";
                            String protocolo = "-";
                            String estado ="-";  
                            String respuesta = "-";  
                            String referer="-";
                            String SO = "-";
                            String navegador = "-";
                            lista1.add(new Parametrosapache_acceslog(IP,cuenta,fechaHora,metodo,Ruta,protocolo,estado,respuesta,referer,SO,navegador));  
                      
                         }
                    
                    }
                }
            }
            lector.close();
        }catch(IOException e){
            JOptionPane.showMessageDialog(null,"Error al leer el archivo"+e.getMessage());
        
        }
        return lista1;
    }
    
   
    
    
    
    
    public  List<Parametros_errorlog> leerlogerror_log(){
        try{
        BufferedReader lector = new BufferedReader(new FileReader(ruta));
        String linea="";
        while ((linea = lector.readLine()) != null) {  
            String normalizar = normalizarlinea(linea);
            String[] bloques = normalizar.split(" "); 
            String codigo=bloques[0];
            if(codigo.contains("AH")){
                String comando=bloques[1]; 
                String mensaje=armarmensaje(bloques,2);
                Timestamp fecha =Timestamp.valueOf("1970-12-31 23:59:59");
                lista2.add(new Parametros_errorlog(fecha,codigo,"-",comando,mensaje));
            }else{
                Timestamp fecha=convertirerror(bloques[0] + " "  + bloques[1] + " " + bloques[2] + " " + bloques[4]+" " + bloques[3] ); 
                String comando=bloques[5];
                String pid=bloques[7];
                String error=bloques[8];
                String mensaje=armarmensaje(bloques,9);
                lista2.add(new Parametros_errorlog(fecha,error,pid,comando,mensaje));
            }
            }
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, "Error al leer el archivo: " + e.getMessage());
        } 
    
    return lista2;
        
    }
    public Timestamp convertiracces(String fecha){ 
        fecha=fecha.replace("/","-");
        int separador=fecha.indexOf(":");
        fecha=fecha.substring(0,separador)+" "+fecha.substring(separador+1);
        try{
            SimpleDateFormat form=new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
            Date fech=form.parse(fecha);
            return new Timestamp(fech.getTime());
        }catch(Exception e){
            return null;
        }
        
    }
    public Timestamp convertirerror(String fecha){  
       
        try{
            int punto = fecha.indexOf(".");
            fecha = fecha.substring(0, punto);
              String[] partes = fecha.split(" ");

        
            String nuevaFecha = partes[2] + "-" + partes[1] + "-" + partes[3] + " " + partes[4];
            String ffsf="";
            
            SimpleDateFormat form=new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
            Date fech=form.parse(nuevaFecha);
            return new Timestamp(fech.getTime());
        }catch(Exception e){
            return null;
        }
        
    }
    public Timestamp convertirftp(String fecha){  
       
        try{
            String[] partes = fecha.split(" "); 
            String nuevaFecha = partes[2] + "-" + partes[1] + "-" + partes[4] + " " + partes[3];
            SimpleDateFormat form=new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
            Date fech=form.parse(nuevaFecha);
            return new Timestamp(fech.getTime());
        }catch(Exception e){
            return null;
        }
        
    }
    //vsftpd jhoel
     public List<Parametros_vsftpd> leerLogVsftpd() {
    try {
        BufferedReader lector = new BufferedReader(new FileReader(ruta));
        String linea="";
    while ((linea = lector.readLine()) != null) {
            String normalizar = normalizarlinea(linea);
            String[] bloques = normalizar.split(" ");
        if(bloques.length>7){
            Timestamp fecha = convertirftp(bloques[0] + " " + bloques[1] + " " + bloques[3] + " " + bloques[4] + " " + bloques[5]); // Fecha completa
            String pid = bloques[7];
            String comandoftp = bloques[8];

            // Procesar dependiendo del comando FTP
            if (comandoftp.equals("CONNECT:")) {
                procesarConectar(fecha, pid, comandoftp, bloques);
            } else if (comandoftp.equals("FTP")) {
                // El comando FTP puede tener parámetros adicionales como FTP <comando>
                procesarFtp(fecha, pid, comandoftp, bloques);
            } else if (comandoftp.equals("OK UPLOAD:")|| comandoftp.equals("FAIL UPLOAD:")) {
                procesarUpload(fecha, pid, comandoftp, bloques);
            } else  if (comandoftp.equals("OK LOGIN:") || comandoftp.equals("FAIL LOGIN:")) {
                procesarLogin(fecha, pid, comandoftp, bloques);
            } else if (comandoftp.equals("OK DOWNLOAD:")|| comandoftp.equals("FAIL DOWNLOAD:")) {
                procesardownload(fecha, pid, comandoftp, bloques);
            } else {
                procesarMensajeGenerico(fecha, pid, comandoftp, bloques);
            }
            
        }
    }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "Error al leer el archivo: " + e.getMessage());
    }
    return lista3;
}

private void procesarConectar(Timestamp fecha, String pid, String comandoftp, String[] bloques) {
    String ip = bloques[10];
    lista3.add(new Parametros_vsftpd(fecha, pid, comandoftp, "local host", ip, "Alguien trata de entrar al servidor"));
}

private void procesarFtp(Timestamp fecha, String pid, String comandoftp, String[] bloques) {
    comandoftp=comandoftp+" "+bloques[9];
    String ip=bloques[11];
    String mensaje=armarmensaje(bloques,12);
    lista3.add(new Parametros_vsftpd(fecha,pid,comandoftp,"local host",ip,mensaje));
}

private void procesarUpload(Timestamp fecha, String pid, String comandoftp, String[] bloques) {
   String res="";
    String usuario=comandoftp;
    comandoftp=bloques[9]+" "+bloques[10];
    String ip=bloques[12];
    String mensaje=armarmensaje(bloques,13);
    if(comandoftp.equals("OK UPLOAD:")){
        res="Se subio con exito el archivo: ";
    }else{
        res="No se subio con exito el archivo: ";
    }

    lista3.add(new Parametros_vsftpd(fecha,pid,comandoftp,usuario,ip,res+mensaje));
}
private void procesarLogin(Timestamp fecha, String pid, String comandoftp, String[] bloques) {
    String res="";
    String usuario=comandoftp;
    comandoftp=bloques[9]+" "+bloques[10];
    if(comandoftp.equals("OK LOGIN:")){
        res="Acceso permitido a ";
    }else{
        res="Acceso denegado a ";
    }
    String ip=bloques[12];
    lista3.add(new Parametros_vsftpd(fecha,pid,comandoftp,usuario,ip,res+usuario));
}

private void procesardownload(Timestamp fecha, String pid, String comandoftp, String[] bloques) {
     String res="";
    String usuario=comandoftp;
    comandoftp=bloques[9]+" "+bloques[10];
    if(comandoftp.equals("OK DOWNLOAD:")){
        res="Descarga exitosa, archivo: ";
    }else{
        res="No se puedo descargar el archivo: ";
    }
    String ip=bloques[12];
    String mensaje=armarmensaje(bloques,13);
    lista3.add(new Parametros_vsftpd(fecha,pid,comandoftp,usuario,ip,res+mensaje));
}

private void procesarMensajeGenerico(Timestamp fecha, String pid, String comandoftp, String[] bloques) {
    String usuario=comandoftp;
    comandoftp=bloques[9]+" "+bloques[10];
    String mensaje = armarmensaje(bloques, 13);
    String ip = bloques[12];
    lista3.add(new Parametros_vsftpd(fecha, pid, comandoftp, usuario, ip, mensaje));
}
private String normalizarlinea(String cadena){
        cadena = cadena.replace("[", "");
        cadena = cadena.replace("]", ""); 
        cadena = cadena.replace("\"", ""); // Comilla doble
        return cadena;
    }
    private String armarmensaje(String bloques[],int pos){
        String res="";
        for(int i=pos;i<bloques.length;i++){
            res=res+" "+bloques[i];
       }
    
        return res;
    }
    //autogenerear texto 
    public String analizajbox(){
       
        if (ruta.equalsIgnoreCase("Acces")) {
            //respruta = "/var/log/apache2/acces_log";
            respruta="C:\\Users\\ASUS\\Desktop\\acces_log.txt";
        } else if (ruta.equalsIgnoreCase("FTP")) {
            //respruta = "/var/log/vsftpd_log";
            respruta="C:\\Users\\ASUS\\Desktop\\vsftpd.txt";
        } else if (ruta.equalsIgnoreCase("Error")) {
            //respruta = "/var/log/apache2/error_log";
            respruta="C:\\Users\\ASUS\\Desktop\\error_log.txt";
        }
        return this.respruta;
    
    }
   
}
