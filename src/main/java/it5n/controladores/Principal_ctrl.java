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

import it5n.basedatos.Seleccionar;
import it5n.basedatos.Verificar;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import it5n.basedatos.Conexion;

/**
 * FXML Controller class
 *
 * @author Héctor Armando Herrera (profeharmando@gmail.com)
 */
public class Principal_ctrl implements Initializable {
    
    @FXML private Label lblMensaje;
    @FXML private Label lblCentro;
    @FXML public ImageView imgCentro;
    @FXML private Button btnVotar;
    @FXML private Button btnVerResultados;
    @FXML private Button btnConfigurar;
    @FXML private Button btnACercaDe;
    @FXML private Button btnSalir;
    
    Calendar fecha = new GregorianCalendar();    
    int año = fecha.get(Calendar.YEAR);
    int mes = fecha.get(Calendar.MONTH);
    int dia = fecha.get(Calendar.DAY_OF_MONTH);
    LocalDate FECHA = LocalDate.now();
   
    Verificar verificar = new Verificar();
    Conexion conn = new Conexion();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn.crearBaseDatos();
        if(conn.hayConexion()){
            lblMensaje.setText(FECHA.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
            System.out.println("\n\t\t...HAY CONEXIÓN CON LA BASE DE DATOS");
            btnConfigurar.setDisable(false);
            if(hayCentro()){
                System.out.println("\n\t\t...Hay Centro configurado");
                lblCentro.setText(Seleccionar.obtenerNombreCentro(conn.establecerConexion()));
                String ruta_imagen = Seleccionar.obtenerImagenCentro(conn.establecerConexion());
                mostrarImagenCentro(ruta_imagen);
            }
        }else{           
            System.out.println("\n\t\t...NO HAY CONEXIÓN CON LA BASE DE DATOS");
            lblMensaje.setText("Aviso: NO HAY CONEXIÓN CON LA BASE DE DATOS");
            btnVotar.setDisable(true);
            btnConfigurar.setDisable(true);
            btnVerResultados.setDisable(true);
            btnACercaDe.setDisable(false);
            btnSalir.setDisable(false);
        }
    }
    
    private boolean hayCandidatos(){
        if(verificar.existeTablaCandidatos(conn.establecerConexion())){
            if(Seleccionar.hayCandidatos(conn.establecerConexion())){
                return true;
            }
        }
        return false;
    }
    
    private boolean hayVotantes(){
        if(verificar.existeTablaVotantes(conn.establecerConexion())){
            if(Seleccionar.hayVotantes(conn.establecerConexion())){                
                return true;
            }
        }
        return false;
    }
    
    private boolean hayCentro(){
        if(verificar.existeTablaCentro(conn.establecerConexion())){
            if(Seleccionar.hayCentro(conn.establecerConexion())){
                return true;
            }
        }
        return false;
    }
    
    @FXML
    private void votacion(ActionEvent event) {
        if(hayCandidatos()){
            if(Seleccionar.hayVotoEnBlanco(conn.establecerConexion())){
                if(hayVotantes()){
                    String formulario = "/fxml/Autenticar.fxml";
                    try {            
                        FXMLLoader loader = new FXMLLoader(getClass().getResource(formulario));
                        Stage stage = new Stage(StageStyle.DECORATED);
                        stage.setScene( new Scene((Pane) loader.load()));
                        Autenticar_ctrl controlador =  loader.<Autenticar_ctrl>getController();
                        controlador.inicializarDatos();
                        Image icon = new Image(getClass().getResourceAsStream("/imagenes/icono.png"));
                        stage.getIcons().add(icon);
                        stage.setTitle("Autenticar votante");
                        stage.initModality(Modality.APPLICATION_MODAL);
                        System.out.println("\n\nMostrado formulario: " + formulario);
                        stage.show();
                    } catch (IOException e) {
                        System.err.println("Error al intentar mostrar la ventana " + formulario + ": \n" + e.getMessage());
                    } 
                }else{
                    Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
                    mensaje.setTitle("No hay votantes");
                    mensaje.setHeaderText("Aun no hay votantes registrados en el sistema");
                    mensaje.setContentText("");
                    mensaje.show();
                }
            }else{
                Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
                mensaje.setTitle("No hay opción de voto en blanco");
                mensaje.setHeaderText("Aun no ha configurado la opción para el \"Voto en Blanco\".");
                mensaje.setContentText("");
                mensaje.show();
            }
                
        }else{
            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setTitle("No hay Candidatos");
            mensaje.setHeaderText("Aun no hay candidatos registrados en el sistema");
            mensaje.setContentText("");
            mensaje.show();
        }        
    }
    
    @FXML
    private void verResultados(ActionEvent event) {
        if(hayCandidatos()){
            String formulario = "/fxml/VerResultados.fxml";
            try {            
                FXMLLoader loader = new FXMLLoader(getClass().getResource(formulario));
                Stage stage = new Stage(StageStyle.DECORATED);
                stage.setScene( new Scene((Pane) loader.load()));
                VerResultados_ctrl controlador =  loader.<VerResultados_ctrl>getController();
                controlador.inicializarDatos(true, false, false);
                Image icon = new Image(getClass().getResourceAsStream("/imagenes/icono.png"));
                stage.getIcons().add(icon);
                stage.setTitle("Resultados");
                stage.initModality(Modality.APPLICATION_MODAL);
                System.out.println("\n\nMostrado formulario: " + formulario);
                stage.show();
                System.out.println("\n\t\t...iniciando Ver Resultados");
            } catch (IOException e) {
                System.err.println("Error al intentar mostrar la ventana " + formulario + ": \n" + e.getMessage());
            }
        }else{
            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setTitle("No hay votos");
            mensaje.setHeaderText("Aun no hay votos registrados en el sistema");
            mensaje.setContentText("");
            mensaje.show();
        }
    }
    
    @FXML
    private void configurar(ActionEvent event) {
        String formulario = "/fxml/Configurar.fxml";
        try {            
            FXMLLoader loader = new FXMLLoader(getClass().getResource(formulario));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene( new Scene((Pane) loader.load()));
            Configurar_ctrl controlador =  loader.<Configurar_ctrl>getController();
            controlador.inicializarDatos(this);
            Image icon = new Image(getClass().getResourceAsStream("/imagenes/icono.png"));
            stage.getIcons().add(icon);
            stage.setTitle("Configurar sistema");
            stage.initModality(Modality.APPLICATION_MODAL);
            System.out.println("\n\nMostrado formulario: " + formulario);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error al intentar mostrar la ventana " + formulario + ": \n" + e.getMessage());
        } 
    }
    
    @FXML
    private void aCercaDe(ActionEvent event) {
        System.out.println("\n\t\t...iniciando Configurar");
        String formulario = "/fxml/ACercaDe.fxml";
        try {            
            FXMLLoader loader = new FXMLLoader(getClass().getResource(formulario));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene( new Scene((Pane) loader.load()));
            ACercaDe_ctrl controlador =  loader.<ACercaDe_ctrl>getController();
            controlador.inicializarDatos(true, false, false);
            Image icon = new Image(getClass().getResourceAsStream("/imagenes/icono.png"));
            stage.getIcons().add(icon);
            stage.setTitle("A cerca de ");
            stage.initModality(Modality.APPLICATION_MODAL);
            System.out.println("Mostrado formulario: " + formulario);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error al intentar mostrar la ventana " + formulario + ": \n" + e.getMessage());
        } 
    }
    
    private void mostrarImagenCentro(String ruta_imagen){
        try {
            File file = new File(ruta_imagen);
            Image imagen = new Image(file.toURI().toString(), 400, 400, false, false);            
            if(imagen != null){
                imgCentro.setImage(imagen);                
                centrarImagen(imagen, imgCentro);
            }            
        } catch (Exception ex) {
            System.err.println("\n\t\t...error al intentar mostrar la imagen del Centro\n" + ruta_imagen + "\n" + ex.getMessage());
        }
    }
    
    private void centrarImagen(Image img, ImageView imageView){
        double w = 0;
        double h = 0;
        double ratioX = imageView.getFitWidth() / img.getWidth();
        double ratioY = imageView.getFitHeight() / img.getHeight();
        double reducCoeff = 0;
        if(ratioX >= ratioY) {
            reducCoeff = ratioY;
        } else {
            reducCoeff = ratioX;
        }
        w = img.getWidth() * reducCoeff;
        h = img.getHeight() * reducCoeff;
        imageView.setX((imageView.getFitWidth() - w) / 2);
        imageView.setY((imageView.getFitHeight() - h) / 2);
    }
    
    @FXML
    private void salir(ActionEvent event) {
        Alert confirmar = new Alert(Alert.AlertType.CONFIRMATION);
        confirmar.setTitle("Salir");
        confirmar.setHeaderText("¿Quieres salir del sistema de votación?");
        confirmar.setContentText("");
        Optional<ButtonType> result = confirmar.showAndWait();
        if(result.get() == ButtonType.OK){
            // Obtener el escenario (Stage)
            Stage stage = (Stage) btnSalir.getScene().getWindow();
            // Cerrar el formulario.
            stage.close();
        }
    }

}
