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

import it5n.basedatos.Insertar;
import it5n.basedatos.Seleccionar;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import it5n.basedatos.Conexion;

/**
 * FXML Controller class
 *
 * @author Héctor Armando Herrera (profeharmando@gmail.com)
 */
public class Alumno_ctrl implements Initializable {
    
    @FXML private TextField txtRne;
    @FXML private TextField txtNombre;
    @FXML private Button btnAceptar;
    @FXML private Button btnCancelar;
    @FXML private Button btnSalir;
    
    Conexion conn = new Conexion();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void inicializarDatos(String rne, boolean agregar){
        manejarEventos();
        txtRne.setText(rne);
        txtRne.setDisable(false);
        if(agregar){
            txtNombre.setDisable(false);
        }else{
            txtNombre.setDisable(true);
        }
        
        btnAceptar.setDisable(true);
        btnCancelar.setDisable(true);
        btnSalir.setDisable(false);
    }
    
    private void manejarEventos(){
        //Ocurre cada vez que se escribe en el txtRne
        txtRne.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(txtRne.getText() != null){
                    if(txtRne.isDisable() == false){
                        if(txtRne.getText().length() > 0){
                            txtNombre.setDisable(false);
                            btnCancelar.setDisable(false);
                        }else{
                            txtNombre.setDisable(true);
                            btnAceptar.setDisable(true);
                            btnCancelar.setDisable(true);
                        }
                    }
                }
            }
        });
        
        //Ocurre cada vez que se escribe en el txtNombre
        txtNombre.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(txtNombre.getText() != null){
                    if(txtNombre.isDisable() == false){
                        if(txtNombre.getText().length() > 0){
                            btnAceptar.setDisable(false);
                            btnCancelar.setDisable(false);
                        }else{
                            btnAceptar.setDisable(true);
                            btnCancelar.setDisable(true);
                        }
                    }
                }
            }
        });
    }
    
    @FXML
    private void guardarVotante(){
        String rne = txtRne.getText().toUpperCase();
        String nombre = txtNombre.getText().toUpperCase();
        Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
        try {
            if(!Seleccionar.registroExiste(conn.establecerConexion(), rne)){
                Insertar.guardarVotante(conn.establecerConexion(), rne, nombre, "NO");
                mensaje.setTitle("Votante guardado");
                mensaje.setContentText("");
                mensaje.setHeaderText("Se ha agregado al sistema el votante " + nombre + " con el registro " + rne);
                mensaje.show();
                txtRne.clear();
                txtNombre.clear();
                btnAceptar.setDisable(true);
                txtRne.requestFocus();
            }else{
                mensaje.setTitle("Error al guardar");
                mensaje.setContentText("");
                mensaje.setHeaderText("No se puede agregar el nuevo votante " + nombre + ".\n\nYa existe un votante con el registro " + rne);
                mensaje.show();
                txtRne.requestFocus();
            }
        } catch (Exception ex) {
            mensaje.setTitle("Error al guardar");
            mensaje.setContentText("");
            mensaje.setHeaderText("No se pudo agregar el nuevo votante.\n" + ex.getMessage());
            mensaje.show();
        }
    }
    
    @FXML
    private void salir(){
        // Obtener el escenario (Stage)
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        // Cerrar el formulario.
        stage.close();
    }
    
}
