/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author saat
 */
public class Parametrosapache_acceslog {
    private String IP; 
    private String cuenta;
    private String fechaHora;
    private String metodo;
    private String Ruta;
    private String protocolo;
    private String estado;
    private String respuesta;
    private String referer;
    private String SO;
    private String navegador;

    public Parametrosapache_acceslog(String IP, String cuenta, String fechaHora, String metodo, String Ruta, String protocolo, String estado, String respuesta, String referer, String SO, String navegador) {
        this.IP = IP;
        this.cuenta = cuenta;
        this.fechaHora = fechaHora;
        this.metodo = metodo;
        this.Ruta = Ruta;
        this.protocolo = protocolo;
        this.estado = estado;
        this.respuesta = respuesta;
        this.referer = referer;
        this.SO = SO;
        this.navegador = navegador;
    }
    public void mostrardatos(){
        
        System.out.print("IP: " + IP+" ");
        System.out.print("Cuenta: " + cuenta+" ");
        System.out.print("Fecha y Hora: " + fechaHora+" ");
        System.out.print("Método: " + metodo+" ");
        System.out.print("Ruta: " + Ruta+" ");
        System.out.print("Protocolo: " + protocolo+" ");
        System.out.print("Estado: " + estado+" ");
        System.out.print("Respuesta: " + respuesta+" ");
        System.out.print("Referer: " + referer+" ");
        System.out.print("SO: " + SO+" ");
        System.out.print("Navegador: " + navegador+" ");
    
    }
    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public String getRuta() {
        return Ruta;
    }

    public void setRuta(String Ruta) {
        this.Ruta = Ruta;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getSO() {
        return SO;
    }

    public void setSO(String SO) {
        this.SO = SO;
    }

    public String getNavegador() {
        return navegador;
    }

    public void setNavegador(String navegador) {
        this.navegador = navegador;
    }
    
}
