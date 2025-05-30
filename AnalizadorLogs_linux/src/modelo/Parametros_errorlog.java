/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.sql.*;
/**
 *
 * @author saat
 */
public class Parametros_errorlog {
    private Timestamp fecha;  
    private String codigoerror;
    private String pid;
    private String comandoEjecutado;
    private String mensaje;
    

    // Constructor
    public Parametros_errorlog(Timestamp fecha, String nivelLog, String pid,String comandoEjecutado ,String mensaje ) {
        this.fecha = fecha;
        this.codigoerror = nivelLog;
        this.pid = pid;
        this.mensaje = mensaje;
        this.comandoEjecutado = comandoEjecutado;
    }

    // Getters y Setters
    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

   

    public String getNivelLog() {
        return codigoerror;
    }

    public void setNivelLog(String nivelLog) {
        this.codigoerror = nivelLog;
    }


    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getComandoEjecutado() {
        return comandoEjecutado;
    }

    public void setComandoEjecutado(String comandoEjecutado) {
        this.comandoEjecutado = comandoEjecutado;
    }
    
}
