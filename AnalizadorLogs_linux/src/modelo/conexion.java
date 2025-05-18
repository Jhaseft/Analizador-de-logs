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
            JOptionPane.showMessageDialog(null,"Haciendo conexion con la Base de datos");
            Class.forName("com.mysql.jdbc.Driver");
            String usuario=JOptionPane.showInputDialog("Usuario de la base de datos");
            String password=JOptionPane.showInputDialog("contraseña");
            if(password.isEmpty()){
             con=DriverManager.getConnection("jdbc:mysql://localhost/aso",usuario,"");
            }else{
                  con=DriverManager.getConnection("jdbc:mysql://localhost/aso",usuario,password);
            } 
            JOptionPane.showMessageDialog(null,"Conexion exitosa");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error "+e.getMessage());
            JOptionPane.showMessageDialog(null,"Cerrando app");
            con=null;
        }
        return con;
    }



}
 