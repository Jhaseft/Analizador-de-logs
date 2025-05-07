/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;
import java.sql.*;
import javax.swing.JOptionPane;
/**
 *
 * @author saat
 */
public class conexion {
    
    Connection con=null;
    
    
    public Connection getconexion(){
        try{
        Class.forName("org.apache.derby.jdbc.ClientDriver");  
        con=DriverManager.getConnection("jdbc:derby://localhost:1527/DATA");
            JOptionPane.showMessageDialog(null,"Conexion exitosa");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error "+e.getMessage());
        }
        return con;
    }
    
    public ResultSet consulta(String consulta){
        ResultSet rs=null;
        try{
            PreparedStatement ps=con.prepareStatement(consulta);
            rs=ps.executeQuery();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error"+e.getMessage());
        }
        return rs;
    }
    
    public static void main (String[] args){
    conexion c=new conexion();
    c.getconexion();
    }
   
}
 
