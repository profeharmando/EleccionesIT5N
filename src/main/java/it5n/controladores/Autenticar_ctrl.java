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
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import it5n.basedatos.Conexion;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author Héctor Armando Herrera (profeharmando@gmail.com)
 */
public class Autenticar_ctrl implements Initializable {
    
    @FXML private TextField txtRNE;
    @FXML private Button btnAutenticar;
    @FXML private Button btnVotar;
    @FXML private Button btnAgregar;
    @FXML private Button btnSalir;
    @FXML private Label lblMensaje;
    
    Conexion conn = new Conexion();
    
    //Valiables locales
    String RNE;
    String NOMBRE;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void inicializarDatos(){
        manejarEventos();
        lblMensaje.setText("?");
        btnAutenticar.setDisable(true);
        btnVotar.setDisable(true);
        btnAgregar.setDisable(true);
        btnSalir.setDisable(false);
    }
    
    private void manejarEventos(){
        //Ocurre cada vez que se escribe en el txtRne
        txtRNE.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(txtRNE.getText() != null){
                    if(txtRNE.isDisable() == false){
                        if(txtRNE.getText().length() > 0){
                            btnAutenticar.setDisable(false);
                        }else{
                            btnAutenticar.setDisable(true);
                        }
                    }
                }
            }
        });
        txtRNE.setOnKeyPressed(new EventHandler<KeyEvent>(){            
            @Override
            public void handle(KeyEvent ke)
            {
                if(txtRNE.getText() != null){
                    if (ke.getCode().equals(KeyCode.ENTER))
                    {
                        autenticar();
                        btnVotar.requestFocus();
                    }
                }
            }            
        });
        btnAutenticar.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
           public void handle(KeyEvent ke)
           {
               if (ke.getCode().equals(KeyCode.ENTER))
               {
                   autenticar();
               }
           }
        });
        btnVotar.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
           public void handle(KeyEvent ke)
           {
               if (ke.getCode().equals(KeyCode.ENTER))
               {
                   votar();
               }
           }
        });
    }
    
    
    
    @FXML
    private void votar(){
        String formulario = "/fxml/Votar.fxml";
        try {            
            FXMLLoader loader = new FXMLLoader(getClass().getResource(formulario));
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.setScene( new Scene((Pane) loader.load()));
            Votar_ctrl controlador =  loader.<Votar_ctrl>getController();
            controlador.inicializarDatos(txtRNE.getText(), lblMensaje.getText());
            stage.setTitle("Votante " + NOMBRE);
            Image icon = new Image(getClass().getResourceAsStream("/imagenes/icono.png"));
            stage.getIcons().add(icon);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.isMaximized();
            System.out.println("\n\nMostrado formulario: " + formulario);
            txtRNE.setText("");
            lblMensaje.setText("");
            btnVotar.setDisable(true);
            btnAgregar.setDisable(true);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error al intentar mostrar la ventana " + formulario + ": \n" + e.getMessage());
        } 
    }
    
    @FXML
    private void autenticar(){
        String rne = txtRNE.getText();
        if(rneExiste(rne)){
            NOMBRE = obtenerNombreAlumno(rne);
            if(NOMBRE.length() > 0){
                if(alumnoSinVotar(rne)){
                    lblMensaje.setText(NOMBRE);
                    btnAutenticar.setDisable(true);
                    btnVotar.setDisable(false);
                }else{
                    Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
                    mensaje.setTitle("Ya votó");
                    mensaje.setHeaderText("El votante " + NOMBRE + "\nRegistro: " + rne + "\n\nYA VOTÓ EN ESTA URNA.");
                    mensaje.setContentText("");
                    mensaje.show();
                    btnAutenticar.setDisable(false);
                    btnVotar.setDisable(true);
                    btnAgregar.setDisable(true);
                    txtRNE.requestFocus();
                }
            }else{
                lblMensaje.setText("No se encontró votante para el registro " + rne);
                btnAutenticar.setDisable(true);
                btnVotar.setDisable(true);
                btnAgregar.setDisable(false);
            }
            
        }else{
            lblMensaje.setText("El código de registro " + rne + " no está en el sistema");
            btnAutenticar.setDisable(false);
            btnVotar.setDisable(true);
            btnAgregar.setDisable(false);
        }
    }
    
    private boolean rneExiste(String rne){
        String rne_bd = Seleccionar.obtenerRne(conn.establecerConexion(), rne);
        if(rne.equals(rne_bd)){
            return  true;
        }
        return false;
    }
    
    private String obtenerNombreAlumno(String rne){
        String nombre = Seleccionar.obtenerNombreAlumno(conn.establecerConexion(), rne);
        return nombre;
    }
    
    private boolean alumnoSinVotar(String rne){
        if(Seleccionar.alumnoLibre(conn.establecerConexion(), rne)){
            return true;
        }
        return false;
    }
    
    @FXML
    private void agregarAlumno(){
        String formulario = "/fxml/Alumno.fxml";
        try {            
            FXMLLoader loader = new FXMLLoader(getClass().getResource(formulario));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene( new Scene((Pane) loader.load()));
            Alumno_ctrl controlador =  loader.<Alumno_ctrl>getController();
            String rne = txtRNE.getText();
            controlador.inicializarDatos(rne, true);
            Image icon = new Image(getClass().getResourceAsStream("/imagenes/icono.png"));
            stage.getIcons().add(icon);
            stage.setTitle("Agregar nuevo votante");
            stage.initModality(Modality.APPLICATION_MODAL);
            System.out.println("\n\nMostrado formulario: " + formulario);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error al intentar mostrar la ventana " + formulario + ": \n" + e.getMessage());
        }
    }
    
    @FXML
    private void salir(){
        // Obtener el escenario (Stage)
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        // Cerrar el formulario.
        stage.close();
    }
    
}
