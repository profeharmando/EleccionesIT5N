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
import java.io.File;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import it5n.basedatos.Conexion;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Héctor Armando Herrera (profeharmando@gmail.com)
 */
public class Centro_ctrl implements Initializable {

    @FXML private TextField txtNombre;
    @FXML private TextField txtRutaImagen;
    @FXML private ImageView imgImagenCentro;
    @FXML private Button btnBuscarImagen;
    @FXML private Button btnVerImagen;
    @FXML private Button btnAceptar;
    @FXML private Button btnCancelar;
    @FXML private Button btnSalir;
    
    Conexion conn = new Conexion();
    
    Configurar_ctrl configurar;
    Principal_ctrl principal;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }

    public void inicializarDatos(Configurar_ctrl configurar, Principal_ctrl principal){
        this.configurar = configurar;
        this.principal = principal;
        configurarControles();
        manejarEventos();        
    }
    
    private void manejarEventos(){
        //Ocurre cada vez que se escribe en el txtNombre
        txtNombre.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(txtNombre.getText() != null){
                    if(txtNombre.isDisable() == false){
                        if(txtNombre.getText().length() > 0){
                            txtRutaImagen.setDisable(false);
                            btnBuscarImagen.setDisable(false);
                            btnCancelar.setDisable(false);
                        }else{
                            txtRutaImagen.setDisable(true);
                            btnBuscarImagen.setDisable(true);
                            btnCancelar.setDisable(true);
                            btnAceptar.setDisable(true);
                        }
                    }
                }
            }
        });
    }
    
    private void configurarControles(){
        txtNombre.clear();
        txtRutaImagen.clear();
        txtRutaImagen.setDisable(true);
        btnBuscarImagen.setDisable(true);
        btnVerImagen.setDisable(true);
        btnAceptar.setDisable(true);
        btnCancelar.setDisable(true);
        txtNombre.requestFocus();
    }
    
    @FXML
    private void buscarImagen(){
        String ruta_imagen;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Buscar archivo de imagen");
        //Muestra por defecto el directorio de usuario (Home)
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        // Agregar filtros para facilitar la busqueda
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Archivos de imagen", "*.png")
        );
        // Mostrar el FileChooser
        File archivo = fileChooser.showOpenDialog(null);
        // Mostar la ruta del archivo y cargar la imagen
        if (archivo != null) {
            ruta_imagen = archivo.getAbsolutePath();
            txtRutaImagen.setText(ruta_imagen);
            btnVerImagen.setDisable(false);
            try {
            File file = new File(ruta_imagen);
            Image imagen = new Image(file.toURI().toString());
            imgImagenCentro.setImage(imagen);
            if(imagen != null){
                centrarImagen(imagen, imgImagenCentro);
            }            
        } catch (Exception ex) {
            System.err.println("\n\t\t...error al intentar mostrar la imagen: " + ruta_imagen + "\n" + ex.getMessage());
        }
            btnAceptar.setDisable(false);
        }else{
            txtRutaImagen.setText(":( upssss... NO HAY ARCHIVO de imagen");
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
    private void verImagen(){
        if(!txtRutaImagen.getText().isEmpty()){
            System.out.println("\n\t\t...iniciando Ver Imagen");
            String formulario = "/fxml/VerImagen.fxml";
            try {            
                FXMLLoader loader = new FXMLLoader(getClass().getResource(formulario));
                Stage stage = new Stage(StageStyle.DECORATED);
                stage.setScene( new Scene((Pane) loader.load()));
                VerImagen_ctrl controlador =  loader.<VerImagen_ctrl>getController();
                controlador.inicializarDatos(null, txtRutaImagen.getText());
                Image icon = new Image(getClass().getResourceAsStream("/imagenes/icono.png"));
                stage.getIcons().add(icon);
                stage.setTitle("Imagen");
                stage.initModality(Modality.APPLICATION_MODAL);
                System.out.println("Mostrado formulario: " + formulario);
                stage.show();
            } catch (IOException e) {
                System.err.println("Error al intentar mostrar la ventana " + formulario + ": \n" + e.getMessage());
            }
        }else{
            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setTitle("Ruta de imagen vacía");
            mensaje.setContentText("");
            mensaje.setHeaderText("Dede seleccionar una imagen.\n Utilice el boton Buscar.");
            mensaje.show();
        }
    }
    
    @FXML
    private void aceptar(){
        String nombre = txtNombre.getText().toUpperCase();
        String ruta_imagen = txtRutaImagen.getText();
        String ruta_nueva = copiarImagen(ruta_imagen);        
        Insertar.guardarCentro(conn.establecerConexion(), nombre, ruta_nueva);
        System.out.println("\t\t...se ha guardado el Centro " + nombre);
        Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
        mensaje.setTitle("Centro guardado");
        mensaje.setContentText("");
        mensaje.setHeaderText("El centro " + nombre + " ha sido guardado.");
        mensaje.showAndWait();
        configurarControles();
        txtNombre.setDisable(true);
        configurar.btnConfigurarCentro.setDisable(true);
        cambiarImagenCentro(ruta_nueva);
    }
    
    private void cambiarImagenCentro(String ruta_nueva){
        File file = new File(ruta_nueva);
        Image imagen = new Image(file.toURI().toString());
        principal.imgCentro.setImage(imagen);
    }
    
    private String copiarImagen(String ruta){
        String nueva_ruta = null;
        try {
            File archivo = new File(ruta);
            String nombre_archivo = archivo.getName();
            String pathOrigen = ruta.replace(nombre_archivo, "");
            System.out.println("\n\n\t\t Ruta de origen: " + pathOrigen);
            String pathDestino = System.getProperty("user.dir") + System.getProperty("file.separator") + "imagenes";
            System.out.println("\t\t Ruta de destino: " + pathDestino);            
            System.out.println("\t\t Nombre del archivo: " + nombre_archivo + "\n");
            nueva_ruta = pathDestino + System.getProperty("file.separator") + nombre_archivo;
            File ficheroCopiar = new File(pathOrigen, nombre_archivo);
            File ficheroDestino = new File(pathDestino, nombre_archivo);
            if(ficheroCopiar.exists()) {
                Files.copy(Paths.get(ficheroCopiar.getAbsolutePath()), Paths.get(ficheroDestino.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("\nEl archivo " + nombre_archivo + " ha sido copiado en la carpeta de destino " + pathDestino);
            } else {
                System.out.println("\nEl archivo " + nombre_archivo + " no existe en el directorio de origen " + pathOrigen);
            }
        } catch (Exception ex) {
            System.out.println("\nError tratando de copiar el archivo de imagen del voto en blanco.\n"
                    + "Error: " + ex.getMessage() + "\n");
        }
        return nueva_ruta;
    }
    
    @FXML
    private void cancelar(){
        configurarControles();
    }
    
    @FXML
    private void salir(){
        // Obtener el escenario (Stage)
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        // Cerrar el formulario.
        stage.close();
    }
    
}
