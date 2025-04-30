/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC;

import controlador.controlador;
import modelo.modelo;
import vista.ASO01;
import vista.ASO02;
import vista.ASO03;

/**
 *
 * @author saat
 */

public class iniciar {
    
    public static void main (String args[]){
    modelo mod=new modelo();
    ASO01 view1=new ASO01();
    ASO02 view2=new ASO02();
    ASO03 view3=new ASO03();
    
      
        controlador ctrl=new controlador(view1,view2,view3,mod);
        
         view1.setVisible(true);
    }
}
