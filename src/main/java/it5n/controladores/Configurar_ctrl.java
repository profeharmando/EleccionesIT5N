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
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import it5n.basedatos.Conexion;
import java.io.File;
import javafx.scene.image.Image;

/**
 * FXML Controller class
 *
 * @author Héctor Armando Herrera (profeharmando@gmail.com)
 */
public class Configurar_ctrl implements Initializable {

    @FXML public Button btnConfigurarCentro;
    @FXML public Button btnConfigurarCandidatos;
    @FXML public Button btnConfigurarVotoBlanco;
    @FXML private Button btnBorrarDatos;
    @FXML private Button btnSalir;
    
    Verificar verificar = new Verificar();
    
    Conexion conn = new Conexion();
    
    Principal_ctrl principal;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }

    public void inicializarDatos(Principal_ctrl principal){
        this.principal = principal;
        boolean hay_algo = false;
        verificar.verificarCrearTablaCentro(conn.establecerConexion());
        if(Seleccionar.hayCentro(conn.establecerConexion())){
            hay_algo = true;
            btnConfigurarCentro.setDisable(true);
        }else{
            btnConfigurarCentro.setDisable(false);
        }
        verificar.verificarCrearTablaCandidatos(conn.establecerConexion());
        if(Seleccionar.hayCandidatos(conn.establecerConexion())){
            hay_algo = true;
            btnConfigurarCandidatos.setDisable(true);
        }else{
            btnConfigurarCandidatos.setDisable(false);
        }
        verificar.verificarCrearTablaVotantes(conn.establecerConexion());
        if(Seleccionar.hayVotoEnBlanco(conn.establecerConexion())){
            hay_algo = true;
            btnConfigurarVotoBlanco.setDisable(true);
        }else{
            btnConfigurarVotoBlanco.setDisable(false);
        }
        crearCarpetaImagenes();
        if(hay_algo){
            btnBorrarDatos.setDisable(false);
        }else{
            btnBorrarDatos.setDisable(true);
        }
        btnSalir.requestFocus();       
    }
    
    private void crearCarpetaImagenes(){
        String ruta_carpeta = System.getProperty("user.dir") + System.getProperty("file.separator") + "imagenes";
        File carpeta = new File(ruta_carpeta);
        if (!carpeta.exists()) {
            if (carpeta.mkdirs()) {
                System.out.println("\n\t\tLa carpeta \"imagenes\" ha sido creada en " + ruta_carpeta);
            } else {
                System.out.println("\n\nError, no se pudo crear la carpeta \"imagenes\" en " + ruta_carpeta);
            }
        }else{
            System.out.println("\n\t\tYa existe la carpeta \"imagenes\" en " + ruta_carpeta);
        }
    }
    
    @FXML
    private void configurarCentro(){
            String formulario = "/fxml/Centro.fxml";
            try {            
                FXMLLoader loader = new FXMLLoader(getClass().getResource(formulario));
                Stage stage = new Stage(StageStyle.DECORATED);
                stage.setScene( new Scene((Pane) loader.load()));
                Centro_ctrl controlador =  loader.<Centro_ctrl>getController();
                controlador.inicializarDatos(this, principal);
                Image icon = new Image(getClass().getResourceAsStream("/imagenes/icono.png"));
                stage.getIcons().add(icon);
                stage.setTitle("Configurar centro");
                stage.initModality(Modality.APPLICATION_MODAL);
                System.out.println("\n\nMostrado formulario: " + formulario);
                stage.show();
            } catch (IOException e) {
                System.err.println("Error al intentar mostrar la ventana " + formulario + ": \n" + e.getMessage());
            }
    }
    
    @FXML
    private void agregarVotantes() {
        String formulario = "/fxml/ImportarVotantes.fxml";
        try {            
            FXMLLoader loader = new FXMLLoader(getClass().getResource(formulario));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene( new Scene((Pane) loader.load()));
            ImportarVotantes_ctrl controlador =  loader.<ImportarVotantes_ctrl>getController();
            controlador.inicializarDatos(true, false, false);
            Image icon = new Image(getClass().getResourceAsStream("/imagenes/icono.png"));
            stage.getIcons().add(icon);
            stage.setTitle("Importar votantes");
            stage.initModality(Modality.APPLICATION_MODAL);
            System.out.println("\n\nMostrado formulario: " + formulario);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error al intentar mostrar la ventana " + formulario + ": \n" + e.getMessage());
        } 
    }
    
    @FXML
    private void configurarCandidatos(){
        String formulario = "/fxml/ConfigurarCandidatos.fxml";
        try {            
            FXMLLoader loader = new FXMLLoader(getClass().getResource(formulario));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene( new Scene((Pane) loader.load()));
            ConfigurarCandidatos_ctrl controlador =  loader.<ConfigurarCandidatos_ctrl>getController();
            controlador.inicializarDatos(this);
            Image icon = new Image(getClass().getResourceAsStream("/imagenes/icono.png"));
            stage.getIcons().add(icon);
            stage.setTitle("Configurar candidatos");
            stage.initModality(Modality.APPLICATION_MODAL);
            System.out.println("\n\nMostrado formulario: " + formulario);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error al intentar mostrar la ventana " + formulario + ": \n" + e.getMessage());
        }
    }
    
    @FXML
    private void agregarUnVotante(){
        String formulario = "/fxml/Alumno.fxml";
        try {            
            FXMLLoader loader = new FXMLLoader(getClass().getResource(formulario));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene( new Scene((Pane) loader.load()));
            Alumno_ctrl controlador =  loader.<Alumno_ctrl>getController();
            controlador.inicializarDatos("", false);
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
    private void configurarVotoBlanco(){
        if(Seleccionar.hayCandidatos(conn.establecerConexion())){
            verificar.verificarCrearTablaCentro(conn.establecerConexion());
            String formulario = "/fxml/VotoEnBlanco.fxml";
            try {            
                FXMLLoader loader = new FXMLLoader(getClass().getResource(formulario));
                Stage stage = new Stage(StageStyle.DECORATED);
                stage.setScene( new Scene((Pane) loader.load()));
                VotoEnBlanco_ctrl controlador =  loader.<VotoEnBlanco_ctrl>getController();
                controlador.inicializarDatos(this);
                Image icon = new Image(getClass().getResourceAsStream("/imagenes/icono.png"));
                stage.getIcons().add(icon);
                stage.setTitle("Configurar voto en blanco");
                stage.initModality(Modality.APPLICATION_MODAL);
                System.out.println("\n\nMostrado formulario: " + formulario);
                stage.show();
            } catch (IOException e) {
                System.err.println("Error al intentar mostrar la ventana " + formulario + ": \n" + e.getMessage());
            }
        }else{
            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setTitle("No hay candidatos");
            mensaje.setContentText("");
            mensaje.setHeaderText("Para agregar la opción de \"Voto en Blanco\", antes debe agregar Candidatos al sistema");
            mensaje.show();
        }             
    }
    
    @FXML
    private void borrarDatos(){
        Alert confirmar = new Alert(Alert.AlertType.CONFIRMATION);
        confirmar.setTitle("Borrar información");
        confirmar.setHeaderText("\nSe dispone a eliminar información del sistema.\n\nSeleccione \"Aceptar\" para continuar.");
        confirmar.setContentText("");
        Optional<ButtonType> result = confirmar.showAndWait();
        if(result.get() == ButtonType.OK){
                String formulario = "/fxml/BorrarDatos.fxml";
                try {            
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(formulario));
                    Stage stage = new Stage(StageStyle.DECORATED);
                    stage.setScene( new Scene((Pane) loader.load()));
                    BorrarDatos_ctrl controlador =  loader.<BorrarDatos_ctrl>getController();
                    controlador.inicializarDatos();
                    Image icon = new Image(getClass().getResourceAsStream("/imagenes/icono.png"));
                    stage.getIcons().add(icon);
                    stage.setTitle("Eliminar datos");
                    stage.initModality(Modality.APPLICATION_MODAL);
                    System.out.println("\n\nMostrado formulario: " + formulario);
                    stage.show();
                } catch (IOException e) {
                    System.err.println("Error al intentar mostrar la ventana " + formulario + ": \n" + e.getMessage());
                }
        }
    }   
    
    @FXML
    public void salir(){
        // Obtener el escenario (Stage)
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        // Cerrar el formulario.
        stage.close();
    }
    
}
