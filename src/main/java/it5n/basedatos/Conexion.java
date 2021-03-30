/*
 *  Eleciones IT5N por Hector Armando Herrera
 *
 * Este programa es software libre: usted puede distribuir y/o modificarlo
 * bajo los términos de la GNU General Public License publicada por
 * la Free Software Foundation, ya sea la versión 3 de la Licencia, o
 * versiones posteriores.
 *
 * Este programa se distribuye con la esperanza de que sea útil.,
 * pero SIN NINGUNA GARANTÍA; sin ni siquiera la garantía implícita de
 * COMERCIABILIDAD o APTITUD PARA UN PROPÓSITO PARTICULAR. Ver la
 * GNU General Public License para mas detalles.
 *
 * Debería haber recibido una copia de la GNU General Public License
 * junto con este programa. Si no, visite <http://www.gnu.org/licenses/>.
 */

package it5n.basedatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.scene.control.Alert;

/**
 *
 * @author Héctor Armando Herrera (profeharmando@gmail.com)
 */
public class Conexion {
           
    private Connection conn = null;
    
    public Connection crearBaseDatos(){
        try {
            System.setProperty("derby.system.home", System.getProperty("user.dir") + System.getProperty("file.separator") + "BaseDatos");
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            try {
                conn = DriverManager.getConnection("jdbc:derby:BD_ELECCIONES;create=true");
                if(conn != null){
                    System.out.println("\t\t...Aviso: la base de datos BD_ELECCIONES ha sido creada en " + System.getProperty("user.dir"));
                }
            } catch (SQLException ex) {
                System.err.println("\t\t...Error: al crear la BD.\n" + ex.getMessage());
                Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
                mensaje.setTitle("Base de Datos no creada");
                mensaje.setHeaderText("No se pudo crear la Base de Datos en el sistema.\n"
                        + "Intente ejecutar la aplicación con privilegios de Administrador."
                        + "Error: " + ex.getMessage());
                mensaje.setContentText("");
                mensaje.showAndWait();
            }
        } catch (ClassNotFoundException ex) {
            System.err.println("\t\t...Error: al cargar el Driver conector de la BD.\n" + ex.getMessage());
            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setTitle("Error con el driver conector de la Base de Datos");
            mensaje.setHeaderText("No se pudo cargar el driver conectos de la Base de Datos.\n" + ex.getMessage());
            mensaje.setContentText("");
            mensaje.showAndWait();
        }
        return conn;
    }
    
    public Connection establecerConexion(){
        try {
            System.setProperty("derby.system.home", System.getProperty("user.dir") + System.getProperty("file.separator") + "BaseDatos");
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            try {
                conn = DriverManager.getConnection("jdbc:derby:BD_ELECCIONES");
                if(conn != null){
                    //System.out.println("\t\t... conexion hecha a la Base de Datos");
                    return conn;                    
                }
            } catch (SQLException ex) {
                System.err.println("Error al conectar la BD.\n" + ex.getMessage());
            }
        } catch (ClassNotFoundException ex) {
            System.err.println("\t\t...Error al cargar el Driver conector de la BD.\n" + ex.getMessage());
        }
        return null;
    }
    
    public boolean hayConexion(){
        try {
            System.setProperty("derby.system.home", System.getProperty("user.dir") + System.getProperty("file.separator") + "BaseDatos");
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            try {
                conn = DriverManager.getConnection("jdbc:derby:BD_ELECCIONES");
                if(conn != null){
                    System.out.println("\t\t...Aviso: hay conexion a la BD");
                    return true;                    
                }
            } catch (SQLException ex) {
                System.err.println("No hay conexion con la BD.\n" + ex.getMessage());
            }
        } catch (ClassNotFoundException ex) {
            System.err.println("\t\t...Error: al cargar el Driver conector de la BD.\n" + ex.getMessage());
        }
        return false;
    }
    
    public void cerrarConexion(){
        if(conn != null){
            try {
                conn.close();
                System.out.println("\t\t...Aviso: la conexion con la BD ha sido cerrada");
            } catch (SQLException ex) {
                System.err.println("\t\t...Aviso: no se pudo cerrar la conexion con la BD.\n" + ex.getMessage());
            }                      
        }
    }
    
}
