
package MVC;

import controlador.controlador;
import modelo.modelo;
import vista.*;

/**
 *
 * @author saat
 */
public class iniciar {
        public static void main(String args[]){
            modelo mod=new modelo();
            ASO01 v1=new ASO01();
            ASO02 v2=new ASO02();
            ASO03 v3=new ASO03();
            controlador ctrl=new controlador(v1, v2, v3, mod);
            ctrl.iniciar();
        }
}
