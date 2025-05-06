/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Usuario
 */
public class Parametros_vsftpd {
    private String IP;
    private String usuario;
    private String fechaHora;
    private String comando;
    private String respuesta;
    private String archivo;
    private String tamaño;
    private String tiempoTransferencia;
    private String velocidad;
    private String estado;
    private String tipoEvento;

    public Parametros_vsftpd(String IP, String usuario, String fechaHora, String comando, 
                             String respuesta, String archivo, String tamaño, 
                             String tiempoTransferencia, String velocidad, 
                             String estado, String tipoEvento) {
        this.IP = IP;
        this.usuario = usuario;
        this.fechaHora = fechaHora;
        this.comando = comando;
        this.respuesta = respuesta;
        this.archivo = archivo;
        this.tamaño = tamaño;
        this.tiempoTransferencia = tiempoTransferencia;
        this.velocidad = velocidad;
        this.estado = estado;
        this.tipoEvento = tipoEvento;
    }

    public void mostrarDatos() {
        System.out.println("=== Entrada de Log vsftpd ===");
        System.out.println("IP: " + IP);
        System.out.println("Usuario: " + usuario);
        System.out.println("Fecha/Hora: " + fechaHora);
        System.out.println("Tipo de Evento: " + tipoEvento);
        
        if(comando != null && !comando.isEmpty()) {
            System.out.println("Comando: " + comando);
        }
        
        if(respuesta != null && !respuesta.isEmpty()) {
            System.out.println("Respuesta: " + respuesta);
        }
        
        if(archivo != null && !archivo.isEmpty()) {
            System.out.println("Archivo: " + archivo);
            System.out.println("Tamaño: " + tamaño);
            System.out.println("Tiempo Transferencia: " + tiempoTransferencia);
            System.out.println("Velocidad: " + velocidad);
        }
        
        System.out.println("Estado: " + estado);
        System.out.println("=============================");
    }

    // Getters y Setters
    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getComando() {
        return comando;
    }

    public void setComando(String comando) {
        this.comando = comando;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public String getTamaño() {
        return tamaño;
    }

    public void setTamaño(String tamaño) {
        this.tamaño = tamaño;
    }

    public String getTiempoTransferencia() {
        return tiempoTransferencia;
    }

    public void setTiempoTransferencia(String tiempoTransferencia) {
        this.tiempoTransferencia = tiempoTransferencia;
    }

    public String getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(String velocidad) {
        this.velocidad = velocidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }
}
