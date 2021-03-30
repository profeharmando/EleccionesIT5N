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

package it5n.controladores;

import it5n.basedatos.Eliminar;
import it5n.basedatos.Modificar;
import it5n.basedatos.Verificar;
import it5n.basedatos.Conexion;
import it5n.basedatos.Seleccionar;
import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Héctor Armando Herrera (profeharmando@gmail.com)
 */
public class BorrarDatos_ctrl implements Initializable {
    
    @FXML private Button btnEliminarResultados;
    @FXML private Button btnEliminarElectores;
    @FXML private Button btnEliminarCandidatos;
    @FXML private Button btnEliminarCentro;
    @FXML private Button btnEliminarTodo;
    @FXML private Button btnSalir;
    Conexion conn = new Conexion();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inhabilitarControles();
        boolean hay_algo = false;
        if(Seleccionar.hayCentro(conn.establecerConexion())){
            btnEliminarCentro.setDisable(false);
            hay_algo = true;
        }
        if(Seleccionar.hayVotantes(conn.establecerConexion())){
            btnEliminarElectores.setDisable(false);
            hay_algo = true;
        }
        if(Seleccionar.hayCandidatos(conn.establecerConexion())){
            btnEliminarCandidatos.setDisable(false);
            hay_algo = true;
        }
        if(Seleccionar.hayResultados(conn.establecerConexion())){
            btnEliminarResultados.setDisable(false);
            hay_algo = true;
        }
        if(hay_algo){
            btnEliminarTodo.setDisable(false);
        }
    }
    
    public void inicializarDatos(){
        
    }
    
    private void inhabilitarControles(){
        btnEliminarTodo.setDisable(true);
        btnEliminarCentro.setDisable(true);
        btnEliminarElectores.setDisable(true);
        btnEliminarCandidatos.setDisable(true);
        btnEliminarResultados.setDisable(true);
    }
    
    @FXML
    private void eliminarElectores(){
        Alert confirmar = new Alert(Alert.AlertType.CONFIRMATION);
        confirmar.setTitle("Eliminar votantes");
        confirmar.setHeaderText("\n¿Eliminar a todos los votantes?\n\nSeleccione \"Aceptar\" para eliminar.");
        confirmar.setContentText("");
        Optional<ButtonType> result = confirmar.showAndWait();
        if(result.get() == ButtonType.OK){
            if(Verificar.existeTablaVotantes(conn.establecerConexion())){
                if(Eliminar.eliminarDatosVotantes(conn.establecerConexion())){
                    System.out.println("\t\t...los datos de la tabla TBL_VOTANTES han sido eliminados");
                    Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
                    mensaje.setTitle("Votantes eliminados");
                    mensaje.setContentText("");
                    mensaje.setHeaderText("Se han eliminado todos los votantes del sistema.");
                    mensaje.showAndWait();
                    if(Modificar.restablecerVotosCandidato(conn.establecerConexion())){
                        System.out.println("\t\t...todos los votos de la tabla TBL_CANDIDATOS han sido puestos en 0");
                    }
                    btnEliminarElectores.setDisable(true);
                }
            }else{
                System.out.println("\t\t...la tabla TBL_VOTANTES no existe.");
            } 
        }
            
    }
    
    @FXML
    private void eliminarCandidatos(){
        Alert confirmar = new Alert(Alert.AlertType.CONFIRMATION);
        confirmar.setTitle("Eliminar candidatos");
        confirmar.setHeaderText("\n¿Eliminar a todos los candidatos?\n\nSeleccione \"Aceptar\" para eliminar.");
        confirmar.setContentText("");
        Optional<ButtonType> result = confirmar.showAndWait();
        if(result.get() == ButtonType.OK){
           if(Verificar.existeTablaCandidatos(conn.establecerConexion())){
                if(Eliminar.eliminarDatosCandidatos(conn.establecerConexion())){
                    System.out.println("\t\t...los datos de la tabla TBL_CANDIDATOS han sido eliminados");
                    Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
                    mensaje.setTitle("Candidatos eliminados");
                    mensaje.setContentText("");
                    mensaje.setHeaderText("Se han eliminado todos los candidatos del sistema.");
                    mensaje.showAndWait();
                    if(Modificar.restablecerVotoVotantes(conn.establecerConexion())){
                        System.out.println("Se ha establecido el estado \"VOTO = NO\" para todos los votantes.");
                    }
                    btnEliminarCandidatos.setDisable(true);
                }
            }else{
                System.out.println("\t\t...la tabla TBL_CANDIDATOS no existe.");
            } 
        }            
    }
    
    @FXML
    private void eliminarCentro(){
        Alert confirmar = new Alert(Alert.AlertType.CONFIRMATION);
        confirmar.setTitle("Eliminar centro");
        confirmar.setHeaderText("\n¿Eliminar información del centro?\n\nSeleccione \"Aceptar\" para eliminar.");
        confirmar.setContentText("");
        Optional<ButtonType> result = confirmar.showAndWait();
        if(result.get() == ButtonType.OK){
            if(Verificar.existeTablaCentro(conn.establecerConexion())){
                if(Eliminar.eliminarDatosCentros(conn.establecerConexion())){
                    System.out.println("\t\t...los datos de la tabla TBL_CENTRO han sido eliminados");
                    Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
                    mensaje.setTitle("Centro eliminado");
                    mensaje.setContentText("");
                    mensaje.setHeaderText("Se ha eliminado el centro.");
                    mensaje.showAndWait();
                    btnEliminarCentro.setDisable(true);
                }
            }else{
                System.out.println("\t\t...la tabla TBL_CENTRO no existe");
            }
        }
        
    }
    
    @FXML
    private void eliminarResultados(){
        Alert confirmar = new Alert(Alert.AlertType.CONFIRMATION);
        confirmar.setTitle("Eliminar resultados");
        confirmar.setHeaderText("\n¿Eliminar información de los resultados de la elección?\n\nSeleccione \"Aceptar\" para eliminar.");
        confirmar.setContentText("");
        Optional<ButtonType> result = confirmar.showAndWait();
        if(result.get() == ButtonType.OK){
            if(Verificar.existeTablaCandidatos(conn.establecerConexion())){
                if(Modificar.reiniciarVotosCandidatos(conn.establecerConexion(), 0) > 0){
                    System.out.println("\t\t...los votos de la tabla TBL_CANDIDATOS han sido reiniciados a cero");
                    Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
                    mensaje.setTitle("Resultados eliminados");
                    mensaje.setContentText("");
                    mensaje.setHeaderText("Se han eliminado todos los resultados de la votación.");
                    mensaje.showAndWait();
                    if(Modificar.restablecerVotoVotantes(conn.establecerConexion())){
                        mensaje.setHeaderText("Se ha establecido el estado \"VOTO = NO\" para todos los votantes.");
                    }
                    btnEliminarResultados.setDisable(true);
                }
            }else{
                System.out.println("\t\t...la tabla TBL_CANDIDATOS no existe.");
            }
        }            
    }
    
    @FXML
    private void eliminarTodo(){
        Alert confirmar = new Alert(Alert.AlertType.CONFIRMATION);
        confirmar.setTitle("Eliminar todo");
        confirmar.setHeaderText("\n¿Se dispone a eliminar toda la información del sistema\n"
                + " y dejarlo como nuevo?\n\nSeleccione \"Aceptar\" para continuar y eliminar todo.");
        confirmar.setContentText("");
        Optional<ButtonType> result = confirmar.showAndWait();
        if(result.get() == ButtonType.OK){
            if(Eliminar.eliminarDatosCentros(conn.establecerConexion())){
                System.out.println("\t\t...se han eliminado los datos del centro");
                if(Eliminar.eliminarDatosCandidatos(conn.establecerConexion())){
                    System.out.println("\t\t...se han eliminado los datos de los candidatos");
                    if(Eliminar.eliminarDatosVotantes(conn.establecerConexion())){
                        System.out.println("\t\t...se han eliminado los datos de los votantes");
                        Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
                        mensaje.setTitle("Todo eliminado");
                        mensaje.setContentText("");
                        mensaje.setHeaderText("Se ha restablecido totalmente el sistema.\n\n."
                                + "Debe agregar el Centro, los Candidatos y los Electores nuevamente.");
                        mensaje.showAndWait();                        
                    }
                }
            }
            eliminarImagenes();
            btnEliminarCentro.setDisable(true);
            btnEliminarCandidatos.setDisable(true);
            btnEliminarElectores.setDisable(true);
            btnEliminarResultados.setDisable(true);
            btnEliminarTodo.setDisable(true);
        }            
    }
    
    private void eliminarImagenes(){
        String carpeta_imagenes = System.getProperty("user.dir") + System.getProperty("file.separator") + "imagenes";
        File f = new File(carpeta_imagenes);
        borrarArchivos(f);
        if(f.delete())
         System.out.println("La carpeta de imagenes " + carpeta_imagenes + " y su contenido ha sido borrada correctamente.");
        else
         System.out.println("La carpeta " + carpeta_imagenes + " no se ha podido borrar.");
    }
    
    private void borrarArchivos( File carpeta){
        File[] archivo = carpeta.listFiles();
        for (int x=0; x<archivo.length; x++){
            if (archivo[x].isDirectory()) {
                borrarArchivos(archivo[x]);
            }
            archivo[x].delete();
        }
    }
    
    @FXML
    private void salir(ActionEvent event) {
        // Obtener el escenario (Stage)
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        // Cerrar el formulario.
        stage.close();
    }
    
}
