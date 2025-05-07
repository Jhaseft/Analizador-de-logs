/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import vista.*;
import modelo.*;

/**
 *
 * @author saat
 */
public class controlador implements ActionListener {
    private ASO01 view1;
    private ASO02 view2;
    private ASO03 view3;
    private modelo model;

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
        this.view2.btnfiltrar.addActionListener(this);
    }
   public void iniciar(){
       view1.setLocationRelativeTo(null);
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
         view1.dispose();
    }
     //acciones para el boton de volver
     if(e.getSource()==view2.jButton_volver){    
         view1.setVisible(true);
         view2.setVisible(false);
    }
     //boton para ir a la trecera ventana
     if(e.getSource()==view2.btnfiltrar){    
         view3.setVisible(true);
         view2.setVisible(false);
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
       //C:\\Users\\ASUS\\Desktop\\hola.txt
       
    if(e.getSource()==view1.btnanalizar){   
         
                        model.setRuta(view1.txtruta.getText()); 
                        view1.setVisible(false);
                        view2.lblruta.setText(model.getRuta());
                        view2.setVisible(true);
                    //codigo para meter datos a tabla
                if(model.getRuta().contains("access_log")){
                    List<Parametrosapache_acceslog>lista1=model.leerlogacces_log();
                        DefaultTableModel modelo=(DefaultTableModel)view2.Tabla1.getModel();
                        modelo.setRowCount(0);
                        modelo.setColumnIdentifiers(model.getencabezadoacces_log());
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
                if(model.getRuta().contains("error_log")){  
                         List<Parametros_errorlog>lista2=model.leerlogerror_log();
                         DefaultTableModel modelo=(DefaultTableModel)view2.Tabla1.getModel();
                         modelo.setRowCount(0);
                         modelo.setColumnIdentifiers(model.getencabezadoerror_log());
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
                if(model.getRuta().contains("vsftpd")){
                    
                    List<Parametros_vsftpd>lista3=model.leerLogVsftpd();
                    DefaultTableModel modelo=(DefaultTableModel)view2.Tabla1.getModel();
                    modelo.setRowCount(0);
                    modelo.setColumnIdentifiers(model.getencabezadoFTP());
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
    }
   }
}
                         
                            
                    
                
                

                
        
         
       
    
   
    
    
   
   

