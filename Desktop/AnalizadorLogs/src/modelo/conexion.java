/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author saat
 */
public class conexion {
     Connection con=null;
    
    
    public  Connection getconexion(){
        try{
            JOptionPane.showMessageDialog(null,"Haciendo conexion");
            Class.forName("com.mysql.jdbc.Driver");
             con=DriverManager.getConnection("jdbc:mysql://localhost:33061/aso","root","");
            JOptionPane.showMessageDialog(null,"Conexion exitosa");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error "+e.getMessage());
        }
        return con;
    }



}
 