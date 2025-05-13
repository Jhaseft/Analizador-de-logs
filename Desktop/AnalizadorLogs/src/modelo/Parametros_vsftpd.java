package modelo;
/**
 *
 * @author Usuario
 */
import java.sql.*;
public class Parametros_vsftpd {
    private Timestamp fecha;
    private String pid;
    private String comando;
    private String usuario;
    private String ip;
    private String message;

    public Parametros_vsftpd(Timestamp fecha, String pid, String comando, String usuario, String ip, String message) {
        this.fecha = fecha;
        this.pid = pid;
        this.comando = comando;
        this.usuario = usuario;
        this.ip = ip;
        this.message = message;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getComando() {
        return comando;
    }

    public void setComando(String comando) {
        this.comando = comando;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    

   
}
