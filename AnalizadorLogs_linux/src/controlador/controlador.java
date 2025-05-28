/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;


import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import vista.*;
import modelo.*;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author saat
 */
public class controlador implements ActionListener {
    private ASO01 view1;
    private ASO02 view2;
    private ASO03 view3;
    private modelo model;
    private conexion cn;
    private Connection con;
    
    public controlador(ASO01 view1, ASO02 view2, ASO03 view3, modelo model) {
        this.view1 = view1;
        this.view2 = view2;
        this.view3 = view3;
        this.model = model; 
        //codigo para dar a los botones el escucha aaciones y puedan trabajar
        this.view1.jbox1.addActionListener(this);
        this.view1.btnanalizar.addActionListener(this);
        this.view1.btningresardatos.addActionListener(this);
        this.view1.btnsalir.addActionListener(this);
        this.view2.jButton_volver.addActionListener(this);
        this.view3.btnvolver3.addActionListener(this);
        this.view2.txtrango.addActionListener(this);
        this.view2.btnactualizar.addActionListener(this);
        this.view2.btnlistar.addActionListener(this);
        this.view2.txtbuscarfecha.addActionListener(this);
        this.view2.lblbuscar.addActionListener(this);
        this.view2.combox.addActionListener(this);
        this.view2.btnFiltrar.addActionListener(this);
        this.view3.fallas.addActionListener(this);
        this.view3.Errores.addActionListener(this);
        this.view3.accesos.addActionListener(this);
        this.view2.btnreport1.addActionListener(this);
        this.view2.btnresum.addActionListener(this);
    }
   public void iniciar(){
       cn=new conexion();
        con=(Connection) cn.getconexion();
     if(con!=null){       
       view1.setLocationRelativeTo(null);
        view1.setVisible(true);
     }else{
         try{
         System.exit(0);
         }catch(Exception e){
         
          }
     } 
   }
   public void actionPerformed(ActionEvent e){
      //acciones para el boton de ingresar datos manualmente
      if(e.getSource()==view1.btningresardatos){    
         view1.txtruta.setEnabled(true);
         view1.jbox1.setSelectedIndex(0);
         view1.txtmens.setText("");
         view1.txtruta.setText("");
        }
      //acciones para el boton de salir
     if(e.getSource()==view1.btnsalir){    
         System.exit(0);
         }
     //acciones para el boton de volver
     if(e.getSource()==view2.jButton_volver){    
         view1.setVisible(true);
         view2.dispose();
        }
    //boton para ir a la tercera ventana
    if(e.getSource()==view2.btnFiltrar){
        view3.lblruta3.setText(model.getRuta());
        view2.setVisible(false);
        view3.setVisible(true);
    }
    
     //boton para volver de la tercera ventrana a la segunda
        if(e.getSource()==view3.btnvolver3){    
         view3.setVisible(false);
         view2.setVisible(true);
         }
     //acciones para el combobox
       if(e.getSource()==view1.jbox1){    
         model.setRuta(view1.jbox1.getSelectedItem().toString());
         model.analizajbox();
         view1.txtruta.setText(model.getRespruta());
         
        }
       //acciones para el boton de analizar datos   
        if(e.getSource()==view1.btnanalizar){
        //mandar ruta que puso el usuario   
        model.setRuta(view1.txtruta.getText()); 
        view1.setVisible(false);
        view2.lblruta.setText(model.getRuta());
        view2.setVisible(true);
        view2.andor.setVisible(false);
                //codigo para meter datos a tabla
                if(model.getRuta().contains("access_log")){
                    List<Parametrosapache_acceslog>lista1=model.leerlogacces_log(); 
                    DefaultTableModel modelo=(DefaultTableModel)view2.Tabla1.getModel();
                    modelo.setRowCount(0);
                    modelo.setColumnIdentifiers(model.getencabezadoacces_log());
                    personalizarencabezado(model.getencabezadoacces_log());
                    meterdatosBDacces(lista1);
                }
                if(model.getRuta().contains("error_log")){  
                    List<Parametros_errorlog>lista2=model.leerlogerror_log();
                    DefaultTableModel modelo=(DefaultTableModel)view2.Tabla1.getModel();
                    modelo.setRowCount(0);
                    modelo.setColumnIdentifiers(model.getencabezadoerror_log());
                    personalizarencabezado(model.getencabezadoerror_log());
                    meterdatosBDerror(lista2);
                }
                if(model.getRuta().contains("vsftpd")){                   
                    List<Parametros_vsftpd>lista3=model.leerLogVsftpd();
                    DefaultTableModel modelo=(DefaultTableModel)view2.Tabla1.getModel();      
                    modelo.setRowCount(0);
                    modelo.setColumnIdentifiers(model.getencabezadoFTP());
                    personalizarencabezado(model.getencabezadoFTP());
                    meterdatosBDftp(lista3);
                }
        }
         if(e.getSource()==view2.btnlistar){
                try {
                    if(model.getRuta().contains("access_log")){
                    List<Parametrosapache_acceslog>lista1=new ArrayList<>();
                      PreparedStatement pst=(PreparedStatement) con.prepareStatement("select * from acces_log");
                      ResultSet rs=pst.executeQuery();
                      while(rs.next()) {
                        Parametrosapache_acceslog p=new Parametrosapache_acceslog(rs.getString(1),rs.getString(2), rs.getTimestamp(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11));
                        lista1.add(p);
                        }
                        JOptionPane.showMessageDialog(null,"Exportando datos");
                        DefaultTableModel modelo=(DefaultTableModel)view2.Tabla1.getModel();
                        modelo.setRowCount(0);
                        meterdatosacces(lista1, modelo);
                        JOptionPane.showMessageDialog(null,"Datos exportados con exito");
                    }
                    
                    if(model.getRuta().contains("error_log")){
                    List<Parametros_errorlog>lista1=new ArrayList<>();
                      PreparedStatement pst=(PreparedStatement) con.prepareStatement("select * from error_log");
                      ResultSet rs=pst.executeQuery();
                      while(rs.next()) {
                        Parametros_errorlog p=new Parametros_errorlog(rs.getTimestamp(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                        lista1.add(p);
                        }
                        JOptionPane.showMessageDialog(null,"Exportando datos");
                        DefaultTableModel modelo=(DefaultTableModel)view2.Tabla1.getModel();
                        modelo.setRowCount(0);
                        meterdatoserror(lista1, modelo);
                        JOptionPane.showMessageDialog(null,"Datos exportados con exito");
                    }
                    
                     if(model.getRuta().contains("vsftpd")){ 
                     List<Parametros_vsftpd>lista1=new ArrayList<>();
                      PreparedStatement pst=(PreparedStatement) con.prepareStatement("select * from ftp");
                      ResultSet rs=pst.executeQuery();
                      while(rs.next()) {
                        Parametros_vsftpd p=new Parametros_vsftpd(rs.getTimestamp(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6));
                        lista1.add(p);
                        }
                        JOptionPane.showMessageDialog(null,"Exportando datos");
                        DefaultTableModel modelo=(DefaultTableModel)view2.Tabla1.getModel();
                        modelo.setRowCount(0);
                        meterdatosvsftpd(lista1, modelo);
                        JOptionPane.showMessageDialog(null,"Datos exportados con exito");
                     
                     }
                    
                    
                    
                    
             
       } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Error "+ex.getMessage());
       }
         
    }
         
        if(e.getSource()==view2.btnactualizar){
            
                if(model.getRuta().contains("access_log")){
                    List<Parametrosapache_acceslog>lista1=model.leerlogacces_log(); 
                    JOptionPane.showMessageDialog(null,"Actualizando base de datos");
                    DefaultTableModel modelo=(DefaultTableModel)view2.Tabla1.getModel();
                    modelo.setRowCount(0);
                    meterdatosBDacces(lista1);
                    JOptionPane.showMessageDialog(null,"Actualizacion exitosa apretar el boton listar");
                }
                if(model.getRuta().contains("error_log")){
                    List<Parametros_errorlog>lista1=model.leerlogerror_log(); 
                    JOptionPane.showMessageDialog(null,"Actualizando base de datos");
                    DefaultTableModel modelo=(DefaultTableModel)view2.Tabla1.getModel();
                    modelo.setRowCount(0);
                    meterdatosBDerror(lista1);
                    JOptionPane.showMessageDialog(null,"Actualizacion exitosa apretar el boton listar");
                }
                if(model.getRuta().contains("vsftpd")){ 
                List<Parametros_vsftpd>lista1=model.leerLogVsftpd(); 
                JOptionPane.showMessageDialog(null,"Actualizando base de datos");
                    DefaultTableModel modelo=(DefaultTableModel)view2.Tabla1.getModel();
                    modelo.setRowCount(0);
                    meterdatosBDftp(lista1);
                    JOptionPane.showMessageDialog(null,"Actualizacion exitosa apretar el boton listar");
                }
         
        }
         if(e.getSource()==view2.lblbuscar){
            String res=view2.combox.getSelectedItem().toString();
            if(res.equals("<>")){
                    if(model.getRuta().contains("access_log")){
                     buscaracces(view2.txtbuscado.getText());
                    }
                     if(model.getRuta().contains("error_log")){
                     buscarerror(view2.txtbuscado.getText());
                     }
                     if(model.getRuta().contains("vsftpd")){ 
                     buscarftp(view2.txtbuscado.getText());
                    }
            } 
            if(res.equals("AND")){
                     if(model.getRuta().contains("access_log")){
                     buscaraccesand(view2.txtbuscado.getText(),view2.andor.getText(),"AND");
                     }
                     if(model.getRuta().contains("error_log")){;
                     buscarerrorand(view2.txtbuscado.getText(),view2.andor.getText(),"AND");
                     }
                     if(model.getRuta().contains("vsftpd")){ 
                    buscarftpand(view2.txtbuscado.getText(),view2.andor.getText(),"AND");
                    }
            }
            
            if(res.equals("OR")){
             if(model.getRuta().contains("access_log")){
                     buscaraccesand(view2.txtbuscado.getText(),view2.andor.getText(),"OR");
                     }
                     if(model.getRuta().contains("error_log")){
                     buscarerrorand(view2.txtbuscado.getText(),view2.andor.getText(),"OR");
                     }
                     if(model.getRuta().contains("vsftpd")){ 
                    buscarftpand(view2.txtbuscado.getText(),view2.andor.getText(),"OR");
                    }
            
            
            }
             
         }
         
         if(e.getSource()==view2.txtbuscarfecha){
           
             try {
                 
                  if(model.getRuta().contains("access_log")){
                   java.util.Date fecha = view2.txtfech.getDate();
                     java.sql.Date fechaSQL = new java.sql.Date(fecha.getTime());
                     String sql = "SELECT * FROM acces_log WHERE DATE(Fecha) = ?";
                    PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
                    pst.setDate(1, fechaSQL);  
                    ResultSet rs = pst.executeQuery();
                    List<Parametrosapache_acceslog> lista1 = new ArrayList<>();
                    while (rs.next()) {
                     Parametrosapache_acceslog p = new Parametrosapache_acceslog(
                     rs.getString(1), rs.getString(2), rs.getTimestamp(3), rs.getString(4),
                    rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),
                     rs.getString(9), rs.getString(10), rs.getString(11)
                     );
                         lista1.add(p);
                    }

                    DefaultTableModel modelo = (DefaultTableModel) view2.Tabla1.getModel();
                    modelo.setRowCount(0);
                     meterdatosacces(lista1, modelo);
                  }
                  
                  if(model.getRuta().contains("error_log")){
                   java.util.Date fecha = view2.txtfech.getDate();
                     java.sql.Date fechaSQL = new java.sql.Date(fecha.getTime());
                     String sql = "SELECT * FROM error_log WHERE DATE(Fecha) = ?";
                    PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
                    pst.setDate(1, fechaSQL);  
                    ResultSet rs = pst.executeQuery();
                    List<Parametros_errorlog> lista1 = new ArrayList<>();
                    while (rs.next()) {
                    Parametros_errorlog p = new Parametros_errorlog(
                    rs.getTimestamp(1), rs.getString(2), rs.getString(3),rs.getString(4),rs.getString(5));
                    lista1.add(p);
                    }
                    DefaultTableModel modelo = (DefaultTableModel) view2.Tabla1.getModel();
                    modelo.setRowCount(0);
                     meterdatoserror(lista1, modelo);               
                    } 
                  
                     if(model.getRuta().contains("vsftpd")){ 
                     java.util.Date fecha = view2.txtfech.getDate();
                     java.sql.Date fechaSQL = new java.sql.Date(fecha.getTime());
                     String sql = "SELECT * FROM ftp WHERE DATE(Fecha) = ?";
                    PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
                    pst.setDate(1, fechaSQL);  
                    ResultSet rs = pst.executeQuery();
                    List<Parametros_vsftpd> lista1 = new ArrayList<>();
                    while (rs.next()) {
                    Parametros_vsftpd p = new Parametros_vsftpd(
                    rs.getTimestamp(1), rs.getString(2), rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6));
                    lista1.add(p);
                    }
                    DefaultTableModel modelo = (DefaultTableModel) view2.Tabla1.getModel();
                    modelo.setRowCount(0);
                     meterdatosvsftpd(lista1, modelo);   
                     
                     
                     }   
             } catch (Exception ex) {
   
             } 
        }
         
       if(e.getSource()==view2.txtrango){
            try {
                 
                  if(model.getRuta().contains("access_log")){
                     java.util.Date fecha1 = view2.txtfech2.getDate();
                     java.sql.Date fechaSQL1 = new java.sql.Date(fecha1.getTime());
                     
                      java.util.Date fecha2 = view2.txtfech3.getDate();
                     java.sql.Date fechaSQL2 = new java.sql.Date(fecha2.getTime());                    
                    String sql = "SELECT * FROM acces_log WHERE DATE(Fecha) BETWEEN ? AND ?";
                    PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
                    pst.setDate(1, fechaSQL1);
                     pst.setDate(2, fechaSQL2);

                        ResultSet rs = pst.executeQuery();
                    List<Parametrosapache_acceslog> lista1 = new ArrayList<>();
                    while (rs.next()) {
                     Parametrosapache_acceslog p = new Parametrosapache_acceslog(
                     rs.getString(1), rs.getString(2), rs.getTimestamp(3), rs.getString(4),
                    rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),
                     rs.getString(9), rs.getString(10), rs.getString(11)
                     );
                         lista1.add(p);
                    }

                    DefaultTableModel modelo = (DefaultTableModel) view2.Tabla1.getModel();
                    modelo.setRowCount(0);
                     meterdatosacces(lista1, modelo);
                  }
                  
                  if(model.getRuta().contains("error_log")){
                      java.util.Date fecha1 = view2.txtfech2.getDate();
                     java.sql.Date fechaSQL1 = new java.sql.Date(fecha1.getTime());
                     
                      java.util.Date fecha2 = view2.txtfech3.getDate();
                     java.sql.Date fechaSQL2 = new java.sql.Date(fecha2.getTime());
                     
                     String sql = "SELECT * FROM error_log WHERE DATE(Fecha) BETWEEN ? AND ?";
                    PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
                    pst.setDate(1, fechaSQL1);
                     pst.setDate(2, fechaSQL2); 
                    ResultSet rs = pst.executeQuery();
                    List<Parametros_errorlog> lista1 = new ArrayList<>();
                    while (rs.next()) {
                    Parametros_errorlog p = new Parametros_errorlog(
                    rs.getTimestamp(1), rs.getString(2), rs.getString(3),rs.getString(4),rs.getString(5));
                    lista1.add(p);
                    }
                    DefaultTableModel modelo = (DefaultTableModel) view2.Tabla1.getModel();
                    modelo.setRowCount(0);
                     meterdatoserror(lista1, modelo);               
                    } 
                  
                     if(model.getRuta().contains("vsftpd")){ 
                     java.util.Date fecha1 = view2.txtfech2.getDate();
                     java.sql.Date fechaSQL1 = new java.sql.Date(fecha1.getTime());
                     
                      java.util.Date fecha2 = view2.txtfech3.getDate();
                     java.sql.Date fechaSQL2 = new java.sql.Date(fecha2.getTime());
                     
                    String sql = "SELECT * FROM ftp WHERE DATE(Fecha) BETWEEN ? AND ?";
                    PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
                    pst.setDate(1, fechaSQL1);
                     pst.setDate(2, fechaSQL2); 
                    ResultSet rs = pst.executeQuery();
                    List<Parametros_vsftpd> lista1 = new ArrayList<>();
                    while (rs.next()) {
                    Parametros_vsftpd p = new Parametros_vsftpd(
                    rs.getTimestamp(1), rs.getString(2), rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6));
                    lista1.add(p);
                    }
                    DefaultTableModel modelo = (DefaultTableModel) view2.Tabla1.getModel();
                    modelo.setRowCount(0);
                     meterdatosvsftpd(lista1, modelo);   
                     
                     
                     }   
             } catch (Exception ex) {
   
             }
       
       }
       
       //Filtrar Datos
       
       
       if(e.getSource()==view3.fallas){
           
           try {
                    if(model.getRuta().contains("access_log")){
                    List<Parametrosapache_acceslog>lista1=new ArrayList<>();
                      PreparedStatement pst=(PreparedStatement) con.prepareStatement("select * from acces_log where Estado = '500'");
                      ResultSet rs=pst.executeQuery();
                      while(rs.next()) {
                        Parametrosapache_acceslog p=new Parametrosapache_acceslog(rs.getString(1),rs.getString(2), rs.getTimestamp(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11));
                        lista1.add(p);
                        }
                        JOptionPane.showMessageDialog(null,"Exportando datos");
                        DefaultTableModel modelo=(DefaultTableModel)view3.Tabla1.getModel();
                        modelo.setRowCount(0);
                        modelo.setColumnIdentifiers(model.getencabezadoacces_log());
                        personalizarencabezado(model.getencabezadoacces_log());
                        meterdatosacces(lista1, modelo);
                        JOptionPane.showMessageDialog(null,"Datos exportados con exito");
                    }
                    
                    if(model.getRuta().contains("error_log")){
                    List<Parametros_errorlog>lista1=new ArrayList<>();
                      PreparedStatement pst=(PreparedStatement) con.prepareStatement("select * from error_log where Comando = 'autoindex:error' or Comando = 'php:error'");
                      ResultSet rs=pst.executeQuery();
                      while(rs.next()) {
                        Parametros_errorlog p=new Parametros_errorlog(rs.getTimestamp(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                        lista1.add(p);
                        }
                        JOptionPane.showMessageDialog(null,"Exportando datos");
                        DefaultTableModel modelo=(DefaultTableModel)view3.Tabla1.getModel();
                        modelo.setRowCount(0);
                        modelo.setColumnIdentifiers(model.getencabezadoerror_log());
                        personalizarencabezado(model.getencabezadoerror_log());
                        meterdatoserror(lista1, modelo);
                        JOptionPane.showMessageDialog(null,"Datos exportados con exito");
                    }
                    
                     if(model.getRuta().contains("vsftpd")){ 
                     List<Parametros_vsftpd>lista1=new ArrayList<>();
                      PreparedStatement pst=(PreparedStatement) con.prepareStatement("select * from ftp where Comando = 'FAIL CONNECT'");
                      ResultSet rs=pst.executeQuery();
                      while(rs.next()) {
                        Parametros_vsftpd p=new Parametros_vsftpd(rs.getTimestamp(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6));
                        lista1.add(p);
                        }
                        JOptionPane.showMessageDialog(null,"Exportando datos");
                        DefaultTableModel modelo=(DefaultTableModel)view3.Tabla1.getModel();
                        modelo.setRowCount(0);
                        modelo.setColumnIdentifiers(model.getencabezadoFTP());
                        personalizarencabezado(model.getencabezadoFTP());
                        meterdatosvsftpd(lista1, modelo);
                        JOptionPane.showMessageDialog(null,"Datos exportados con exito");
                     
                     }
                    
                    
                    
                    
             
       } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Error "+ex.getMessage());
       }
           
       }
       
       
       if(e.getSource()==view3.Errores){
           
           try {
                    if(model.getRuta().contains("access_log")){
                    List<Parametrosapache_acceslog>lista1=new ArrayList<>();
                      PreparedStatement pst=(PreparedStatement) con.prepareStatement("select * from acces_log where Estado = '403' or Estado = '404'");
                      ResultSet rs=pst.executeQuery();
                      while(rs.next()) {
                        Parametrosapache_acceslog p=new Parametrosapache_acceslog(rs.getString(1),rs.getString(2), rs.getTimestamp(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11));
                        lista1.add(p);
                        }
                        JOptionPane.showMessageDialog(null,"Exportando datos");
                        DefaultTableModel modelo=(DefaultTableModel)view3.Tabla1.getModel();
                        modelo.setRowCount(0);
                        modelo.setColumnIdentifiers(model.getencabezadoacces_log());
                        personalizarencabezado(model.getencabezadoacces_log());
                        meterdatosacces(lista1, modelo);
                        JOptionPane.showMessageDialog(null,"Datos exportados con exito");
                    }
                    
                    if(model.getRuta().contains("error_log")){
                    List<Parametros_errorlog>lista1=new ArrayList<>();
                      PreparedStatement pst=(PreparedStatement) con.prepareStatement("select * from error_log where Comando = 'authz_core:error'");
                      ResultSet rs=pst.executeQuery();
                      while(rs.next()) {
                        Parametros_errorlog p=new Parametros_errorlog(rs.getTimestamp(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                        lista1.add(p);
                        }
                        JOptionPane.showMessageDialog(null,"Exportando datos");
                        DefaultTableModel modelo=(DefaultTableModel)view3.Tabla1.getModel();
                        modelo.setRowCount(0);
                        modelo.setColumnIdentifiers(model.getencabezadoerror_log());
                        personalizarencabezado(model.getencabezadoerror_log());
                        meterdatoserror(lista1, modelo);
                        JOptionPane.showMessageDialog(null,"Datos exportados con exito");
                    }
                    
                     if(model.getRuta().contains("vsftpd")){ 
                     List<Parametros_vsftpd>lista1=new ArrayList<>();
                      PreparedStatement pst=(PreparedStatement) con.prepareStatement("select * from ftp where Comando = 'FAIL DOWNLOAD'");
                      ResultSet rs=pst.executeQuery();
                      while(rs.next()) {
                        Parametros_vsftpd p=new Parametros_vsftpd(rs.getTimestamp(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6));
                        lista1.add(p);
                        }
                        JOptionPane.showMessageDialog(null,"Exportando datos");
                        DefaultTableModel modelo=(DefaultTableModel)view3.Tabla1.getModel();
                        modelo.setRowCount(0);
                        modelo.setColumnIdentifiers(model.getencabezadoFTP());
                        personalizarencabezado(model.getencabezadoFTP());
                        meterdatosvsftpd(lista1, modelo);
                        JOptionPane.showMessageDialog(null,"Datos exportados con exito");
                     
                     }
                    
                    
                    
                    
             
       } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Error "+ex.getMessage());
       }
           
       }
       
       
       if(e.getSource()==view3.accesos){
           
           try {
                    if(model.getRuta().contains("access_log")){
                    List<Parametrosapache_acceslog>lista1=new ArrayList<>();
                      PreparedStatement pst=(PreparedStatement) con.prepareStatement("select * from acces_log where Estado = '403' or Estado = '401'");
                      ResultSet rs=pst.executeQuery();
                      while(rs.next()) {
                        Parametrosapache_acceslog p=new Parametrosapache_acceslog(rs.getString(1),rs.getString(2), rs.getTimestamp(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11));
                        lista1.add(p);
                        }
                        JOptionPane.showMessageDialog(null,"Exportando datos");
                        DefaultTableModel modelo=(DefaultTableModel)view3.Tabla1.getModel();
                        modelo.setRowCount(0);
                        modelo.setColumnIdentifiers(model.getencabezadoacces_log());
                        personalizarencabezado(model.getencabezadoacces_log());
                        meterdatosacces(lista1, modelo);
                        JOptionPane.showMessageDialog(null,"Datos exportados con exito");
                    }
                    
                    if(model.getRuta().contains("error_log")){
                    List<Parametros_errorlog>lista1=new ArrayList<>();
                      PreparedStatement pst=(PreparedStatement) con.prepareStatement("select * from error_log where Comando = 'authz_basic:error'");
                      ResultSet rs=pst.executeQuery();
                      while(rs.next()) {
                        Parametros_errorlog p=new Parametros_errorlog(rs.getTimestamp(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                        lista1.add(p);
                        }
                        JOptionPane.showMessageDialog(null,"Exportando datos");
                        DefaultTableModel modelo=(DefaultTableModel)view3.Tabla1.getModel();
                        modelo.setRowCount(0);
                        modelo.setColumnIdentifiers(model.getencabezadoerror_log());
                        personalizarencabezado(model.getencabezadoerror_log());
                        meterdatoserror(lista1, modelo);
                        JOptionPane.showMessageDialog(null,"Datos exportados con exito");
                    }
                    
                     if(model.getRuta().contains("vsftpd")){ 
                     List<Parametros_vsftpd>lista1=new ArrayList<>();
                      PreparedStatement pst=(PreparedStatement) con.prepareStatement("select * from ftp where Comando = 'FAIL LOGIN'");
                      ResultSet rs=pst.executeQuery();
                      while(rs.next()) {
                        Parametros_vsftpd p=new Parametros_vsftpd(rs.getTimestamp(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6));
                        lista1.add(p);
                        }
                        JOptionPane.showMessageDialog(null,"Exportando datos");
                        DefaultTableModel modelo=(DefaultTableModel)view3.Tabla1.getModel();
                        modelo.setRowCount(0);
                        modelo.setColumnIdentifiers(model.getencabezadoFTP());
                        personalizarencabezado(model.getencabezadoFTP());
                        meterdatosvsftpd(lista1, modelo);
                        JOptionPane.showMessageDialog(null,"Datos exportados con exito");
                     
                     }
                    
                    
                    
                    
             
       } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Error "+ex.getMessage());
       }
           
       }
       
       
       if(e.getSource()==view2.combox){
        String res=view2.combox.getSelectedItem().toString();
       if(res.equals("<>")){
       view2.andor.setVisible(false);
       view2.txtbuscado.setText("");
       }else if(res.equals("AND")){
       view2.andor.setVisible(true);
 
       }else if(res.equals("OR")){
       view2.andor.setVisible(true);
         
       }else{
           
       }
       
       }
       
        if(e.getSource()==view2.btnreport1){
        
          try {
              
              if(model.getRuta().contains("access_log")){
                Map<String, Object> parametros = new HashMap<>();
                // Ruta al directorio de subreportes (como URL, por si usas subreportes)
                String subreportDir = getClass().getResource("/Reportes/").toString();
                parametros.put("SUBREPORT_DIR", subreportDir);
                // Cargar el .jasper desde el classpath
                InputStream reporteStream = getClass().getResourceAsStream("/Reportes/reporte_accesLog.jasper");   
                JasperReport  report=(JasperReport) JRLoader.loadObject(reporteStream);
                // Ejecutar el reporte
                JasperPrint print = JasperFillManager.fillReport(report, parametros, con);
                JasperExportManager.exportReportToPdf(print);
                JasperViewer viewer = new JasperViewer(print, false);
                viewer.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
                viewer.setVisible(true);
              }
                if(model.getRuta().contains("error_log")){
                Map<String, Object> parametros = new HashMap<>();
                String subreportDir = getClass().getResource("/Reportes/").toString();
                parametros.put("SUBREPORT_DIR", subreportDir);
                InputStream reporteStream = getClass().getResourceAsStream("/Reportes/REPORTE_ERRORLOG.jasper");     
                JasperPrint print = JasperFillManager.fillReport(reporteStream, parametros, con);
                JasperExportManager.exportReportToPdf(print);
                JasperViewer viewer = new JasperViewer(print, false);
                 viewer.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
                viewer.setVisible(true);
                }
                
                if(model.getRuta().contains("vsftpd")){ 
                 Map<String, Object> parametros = new HashMap<>();
                String subreportDir = getClass().getResource("/Reportes/").toString();
                parametros.put("SUBREPORT_DIR", subreportDir);
                InputStream reporteStream = getClass().getResourceAsStream("/Reportes/report_vsftpd.jasper");     
                JasperPrint print = JasperFillManager.fillReport(reporteStream, parametros, con);
                JasperExportManager.exportReportToPdf(print);
                JasperViewer viewer = new JasperViewer(print, false);
                 viewer.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
                viewer.setVisible(true);
                }
                
          } catch (JRException ex) {
              JOptionPane.showMessageDialog(null,"Error: "+ex.getMessage());
          }
        
        
        
        }
        
        if(e.getSource()==view2.btnresum){
            try{
         Map<String, Object> parametros = new HashMap<>();
                String subreportDir = getClass().getResource("/Reportes/").toString();
                parametros.put("SUBREPORT_DIR", subreportDir);
                InputStream reporteStream = getClass().getResourceAsStream("/Reportes/REPORTE_GENERAL_ACCESLOG_ERRORLOG_FTP.jasper");
                JasperPrint print = JasperFillManager.fillReport(reporteStream, parametros, con);
                JasperExportManager.exportReportToPdf(print);
                JasperViewer viewer = new JasperViewer(print, false);
                viewer.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
                viewer.setVisible(true);
                
            }catch(JRException ex){
            JOptionPane.showMessageDialog(null,"Error: "+ex.getMessage());
            }    
        
        }
         
         
            
           
}
   
   
   
   
   
   
   
   
   
   ///////////////////////////////////////////////////////////////BUSCADORES PARA CADA TIPO DE LOG/////////////////////////////
   private void buscarftp(String buscar){
       try {
                    List<Parametros_vsftpd>lista1=new ArrayList<>();
                      PreparedStatement pst=(PreparedStatement) con.prepareStatement("SELECT * FROM ftp WHERE " +
    "Fecha LIKE '%" + buscar + "%' OR " +
    "Pid LIKE '%" + buscar + "%' OR " +
    "Comando LIKE '%" + buscar + "%' OR " +
    "Usuario LIKE '%" + buscar + "%' OR " +
    "Ip LIKE '%" + buscar + "%' OR " +
    "Mensaje LIKE '%" + buscar + "%'");
                      ResultSet rs=pst.executeQuery();
                      while(rs.next()) {
                        Parametros_vsftpd p=new Parametros_vsftpd(rs.getTimestamp(1),rs.getString(2),rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6));
                        lista1.add(p);
                        }
                        DefaultTableModel modelo=(DefaultTableModel)view2.Tabla1.getModel();
                        modelo.setRowCount(0);
                        meterdatosvsftpd(lista1, modelo);
             
             
             } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Error "+ex.getMessage());
             }
   
   }
   
   private void buscarerror(String buscar){
       try {
                    List<Parametros_errorlog>lista1=new ArrayList<>();
                      PreparedStatement pst=(PreparedStatement) con.prepareStatement("SELECT * FROM error_log WHERE " +
    "Fecha LIKE '%" + buscar + "%' OR " +
    "Codigo LIKE '%" + buscar + "%' OR " +
    "Pid LIKE '%" + buscar + "%' OR " +
    "Comando LIKE '%" + buscar + "%' OR " +
    "Mensaje LIKE '%" + buscar + "%'");
                      ResultSet rs=pst.executeQuery();
                      while(rs.next()) {
                        Parametros_errorlog p=new Parametros_errorlog(rs.getTimestamp(1),rs.getString(2),rs.getString(3), rs.getString(4), rs.getString(5));
                        lista1.add(p);
                        }
                        DefaultTableModel modelo=(DefaultTableModel)view2.Tabla1.getModel();
                        modelo.setRowCount(0);
                        meterdatoserror(lista1, modelo);
             
             
             } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Error "+ex.getMessage());
             }
   
   }
   private void buscaracces(String buscar){
       try {
                    List<Parametrosapache_acceslog>lista1=new ArrayList<>();
                      PreparedStatement pst=(PreparedStatement) con.prepareStatement("SELECT * FROM acces_log WHERE " +
    "IP LIKE '%" + buscar + "%' OR " +
    "Cuenta LIKE '%" + buscar + "%' OR " +
    "Fecha LIKE '%" + buscar + "%' OR " +
    "Metodo LIKE '%" + buscar + "%' OR " +
    "Ruta LIKE '%" + buscar + "%' OR " +
    "Protocolo LIKE '%" + buscar + "%' OR " +
    "Estado LIKE '%" + buscar + "%' OR " +
    "Respuesta LIKE '%" + buscar + "%' OR " +
    "Referer LIKE '%" + buscar + "%' OR " +
    "`Sistema Operativo` LIKE '%" + buscar + "%' OR " +
    "Navegador LIKE '%" + buscar + "%'");
                      ResultSet rs=pst.executeQuery();
                      while(rs.next()) {
                        Parametrosapache_acceslog p=new Parametrosapache_acceslog(rs.getString(1),rs.getString(2), rs.getTimestamp(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11));
                        lista1.add(p);
                        }
                        DefaultTableModel modelo=(DefaultTableModel)view2.Tabla1.getModel();
                        modelo.setRowCount(0);
                        meterdatosacces(lista1, modelo);
             
             
             } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Error "+ex.getMessage());
             }
       
   }
   //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   
   /////////////////////////////////////////////////////BUSCAR AND Y OR //////////////////////////////////////////////////////
   private void buscarftpand(String buscar1,String buscar2,String Operando){
       try {
                    List<Parametros_vsftpd>lista1=new ArrayList<>();
                      PreparedStatement pst=(PreparedStatement) con.prepareStatement("SELECT * FROM ftp WHERE " +
    "(" +
        "Fecha LIKE '%" + buscar1 + "%' OR " +
        "Pid LIKE '%" + buscar1 + "%' OR " +
        "Comando LIKE '%" + buscar1 + "%' OR " +
        "Usuario LIKE '%" + buscar1 + "%' OR " +
        "Ip LIKE '%" + buscar1 + "%' OR " +
        "Mensaje LIKE '%" + buscar1 + "%'"+
        ")" + Operando + "(" +        
        "Fecha LIKE '%" + buscar2 + "%' OR " +
        "Pid LIKE '%" + buscar2 + "%' OR " +
        "Comando LIKE '%" + buscar2 + "%' OR " +
        "Usuario LIKE '%" + buscar2 + "%' OR " +
        "Ip LIKE '%" + buscar2 + "%' OR " +
        "Mensaje LIKE '%" + buscar2 + "%'"+
    ")");
                      ResultSet rs=pst.executeQuery();
                      while(rs.next()) {
                        Parametros_vsftpd p=new Parametros_vsftpd(rs.getTimestamp(1),rs.getString(2),rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6));
                        lista1.add(p);
                        }
                        DefaultTableModel modelo=(DefaultTableModel)view2.Tabla1.getModel();
                        modelo.setRowCount(0);
                        meterdatosvsftpd(lista1, modelo);
             
             
             } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Error "+ex.getMessage());
             }
   
   }
   
   private void buscarerrorand(String buscar1,String buscar2,String Operando){
       try {
                    List<Parametros_errorlog>lista1=new ArrayList<>();
                      PreparedStatement pst=(PreparedStatement) con.prepareStatement("SELECT * FROM error_log WHERE " +
    "(" +
        "Fecha LIKE '%"+buscar1+"%' OR " +
        "Codigo LIKE '%"+buscar1+"%' OR " +
        "Pid LIKE '%"+buscar1+"%' OR " +
        "Comando LIKE '%"+buscar1+"%' OR " +
        "Mensaje LIKE '%" +buscar1+ "%'" +
    ")" + Operando + "(" +
        "Fecha LIKE '%"+buscar2+"%' OR " +
        "Codigo LIKE '%"+buscar2+"%' OR " +
        "Pid LIKE '%"+buscar2+"%' OR " +
        "Comando LIKE '%"+buscar2+"%' OR " +
        "Mensaje LIKE '%"+buscar2+"%'" +
    ")");
                      ResultSet rs=pst.executeQuery();
                      while(rs.next()) {
                        Parametros_errorlog p=new Parametros_errorlog(rs.getTimestamp(1),rs.getString(2),rs.getString(3), rs.getString(4), rs.getString(5));
                        lista1.add(p);
                        }
                        DefaultTableModel modelo=(DefaultTableModel)view2.Tabla1.getModel();
                        modelo.setRowCount(0);
                        meterdatoserror(lista1, modelo);
             
             
             } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Error "+ex.getMessage());
             }
   
   }
   private void buscaraccesand(String buscar1,String buscar2,String Operando){
       try {
                    List<Parametrosapache_acceslog>lista1=new ArrayList<>();
                      PreparedStatement pst=(PreparedStatement) con.prepareStatement("SELECT * FROM acces_log WHERE " +
    "(" +
        "IP LIKE '%" + buscar1 + "%' OR " +
        "Cuenta LIKE '%" + buscar1 + "%' OR " +
        "Fecha LIKE '%" + buscar1 + "%' OR " +
        "Metodo LIKE '%" + buscar1 + "%' OR " +
        "Ruta LIKE '%" + buscar1 + "%' OR " +
        "Protocolo LIKE '%" + buscar1 + "%' OR " +
        "Estado LIKE '%" + buscar1 + "%' OR " +
        "Respuesta LIKE '%" + buscar1 + "%' OR " +
        "Referer LIKE '%" + buscar1 + "%' OR " +
        "`Sistema Operativo` LIKE '%" + buscar1 + "%' OR " +
        "Navegador LIKE '%" + buscar1 + "%'" +
        ")" + Operando + "(" +        
        "IP LIKE '%" + buscar2 + "%' OR " +
        "Cuenta LIKE '%" + buscar2 + "%' OR " +
        "Fecha LIKE '%" + buscar2 + "%' OR " +
        "Metodo LIKE '%" + buscar2 + "%' OR " +
        "Ruta LIKE '%" + buscar2 + "%' OR " +
        "Protocolo LIKE '%" + buscar2 + "%' OR " +
        "Estado LIKE '%" + buscar2 + "%' OR " +
        "Respuesta LIKE '%" + buscar2 + "%' OR " +
        "Referer LIKE '%" + buscar2 + "%' OR " +
        "`Sistema Operativo` LIKE '%" + buscar2 + "%' OR " +
        "Navegador LIKE '%" + buscar2 + "%'" +
    ")");
                      ResultSet rs=pst.executeQuery();
                      while(rs.next()) {
                        Parametrosapache_acceslog p=new Parametrosapache_acceslog(rs.getString(1),rs.getString(2), rs.getTimestamp(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11));
                        lista1.add(p);
                        }
                        DefaultTableModel modelo=(DefaultTableModel)view2.Tabla1.getModel();
                        modelo.setRowCount(0);
                        meterdatosacces(lista1, modelo);
             
             
             } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Error "+ex.getMessage());
             }
       
   }
   

   /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   
   ////////////////////////////////////////////////////////METER DATOS ALA BD/////////////////////////////////////////////////////
   private void meterdatosBDacces(List<Parametrosapache_acceslog>lista1){
       try {
            PreparedStatement pst1=(PreparedStatement) con.prepareStatement("DELETE FROM acces_log");
             pst1.executeUpdate();
             for(int i=0;i<lista1.size();i++){
             PreparedStatement pst=(PreparedStatement) con.prepareStatement("INSERT IGNORE INTO acces_log VALUES(?,?,?,?,?,?,?,?,?,?,?)");
             pst.setString(1,lista1.get(i).getIP());
             pst.setString(2,lista1.get(i).getCuenta());
             pst.setTimestamp(3,lista1.get(i).getFechaHora());
             pst.setString(4,lista1.get(i).getMetodo());
             pst.setString(5,lista1.get(i).getRuta());
             pst.setString(6,lista1.get(i).getProtocolo());
             pst.setString(7,lista1.get(i).getEstado());
             pst.setString(8,lista1.get(i).getRespuesta());
             pst.setString(9,lista1.get(i).getReferer());
             pst.setString(10,lista1.get(i).getSO());
             pst.setString(11,lista1.get(i).getNavegador());
             pst.executeUpdate();
             }
       } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Error "+ex.getMessage());
            view2.setVisible(false);
       }
       
    }
   
    private void meterdatosBDerror(List<Parametros_errorlog>lista1){
       try {
            PreparedStatement pst1=(PreparedStatement) con.prepareStatement("DELETE FROM error_log");
             pst1.executeUpdate();
             for(int i=0;i<lista1.size();i++){
             PreparedStatement pst=(PreparedStatement) con.prepareStatement("INSERT IGNORE INTO error_log VALUES(?,?,?,?,?)");
             pst.setTimestamp(1,lista1.get(i).getFecha());
             pst.setString(2,lista1.get(i).getNivelLog());
             pst.setString(3,lista1.get(i).getPid());
             pst.setString(4,lista1.get(i).getComandoEjecutado());
             pst.setString(5,lista1.get(i).getMensaje());
             
             pst.executeUpdate();
             }
       } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Error "+ex.getMessage());
            view2.setVisible(false);
       }
       
    }
        
   private void meterdatosBDftp(List<Parametros_vsftpd>lista1){
       try {
            PreparedStatement pst1=(PreparedStatement) con.prepareStatement("DELETE FROM ftp");
             pst1.executeUpdate();
             for(int i=0;i<lista1.size();i++){
             PreparedStatement pst=(PreparedStatement) con.prepareStatement("INSERT IGNORE INTO ftp VALUES(?,?,?,?,?,?)");
             pst.setTimestamp(1,lista1.get(i).getFecha());
             pst.setString(2,lista1.get(i).getPid());
             pst.setString(3,lista1.get(i).getComando());
             pst.setString(4,lista1.get(i).getUsuario());
             pst.setString(5,lista1.get(i).getIp());
             pst.setString(6,lista1.get(i).getMessage());                 
             pst.executeUpdate();
             }

       } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Error "+ex.getMessage());
            view2.setVisible(false);
       }
       
    }
 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////  
   
   
 /////////////////////////////////////////////////METER DATOS A TABLA DE LA VISTA 2////////////////////////////////////////////////  
    private void meterdatosvsftpd(List<Parametros_vsftpd>lista3,DefaultTableModel modelo){
         for(Parametros_vsftpd p:lista3){
                        modelo.addRow(new Object[]{
                            p.getFecha(),
                            p.getPid(),
                            p.getComando(),
                            p.getUsuario(),
                            p.getIp(),
                            p.getMessage(),     
                        });      
   
                    }
    }
    
     private void meterdatoserror(List<Parametros_errorlog>lista2,DefaultTableModel modelo){
         for(Parametros_errorlog p:lista2){
                        modelo.addRow(new Object[]{
                            p.getFecha(),
                            p.getNivelLog(),
                            p.getPid(),
                            p.getComandoEjecutado(),
                            p.getMensaje(),
                        });      
                        
                    }
    }
     
    private void meterdatosacces(List<Parametrosapache_acceslog>lista1,DefaultTableModel modelo){
        for(Parametrosapache_acceslog p:lista1){
                        modelo.addRow(new Object[]{
                            p.getIP(),
                            p.getCuenta(),
                            p.getFechaHora(),
                            p.getMetodo(),
                            p.getRuta(),
                            p.getProtocolo(),
                            p.getEstado(),
                            p.getRespuesta(),
                            p.getReferer(),
                            p.getSO(),
                            p.getNavegador()
                             }); 
                        }
    }
    
    private void personalizarencabezado(String encabezado[]){
        
            JTableHeader header = view2.Tabla1.getTableHeader();
    header.setDefaultRenderer(new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = new JLabel(value.toString());
            label.setOpaque(true);
            label.setBackground(new Color(45, 85, 155)); // Color de fondo (azul oscuro)
            label.setForeground(Color.WHITE);            // Texto blanco
            label.setFont(new Font("Segoe UI", Font.BOLD, 14));
            label.setHorizontalAlignment(CENTER);        // Centrado
            return label;
        }
    });
    
        view2.Tabla1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {

            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (isSelected) {
                c.setBackground(new Color(70, 130, 180)); // Color al seleccionar
                c.setForeground(Color.WHITE);
            } else {
                // Zebra (alternancia)
                if (row % 2 == 0) {
                    c.setBackground(new Color(245, 245, 245)); // Gris claro
                } else {
                    c.setBackground(new Color(225, 225, 225)); // Gris un poco más oscuro
                }
                c.setForeground(Color.BLACK);
            }

            return c;
        }
    });
        
    view2.Tabla1.setFont(new Font("SansSerif", Font.PLAIN, 13));
    view2.Tabla1.setRowHeight(22); // Altura de filas

    DefaultTableModel modeloActual = (DefaultTableModel) view2.Tabla1.getModel();
    Vector datos = modeloActual.getDataVector();

    Vector<String> encabezadoVector = new Vector<>(Arrays.asList(encabezado));

    DefaultTableModel modeloNoEditable = new DefaultTableModel(datos, encabezadoVector) {
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
    };

    view2.Tabla1.setModel(modeloNoEditable);
    
    }
    
   
    
    

}                         
                            
                    
                
                

                
        
         
       
    
   
    
    
   
   

