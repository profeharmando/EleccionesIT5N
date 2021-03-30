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

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Héctor Armando Herrera (profeharmando@gmail.com)
 */
public class VerImagen_ctrl implements Initializable {
    
    ConfigurarCandidatos_ctrl configurar_candidatos;
    @FXML private ImageView imgImagen;
    @FXML private Button btnCancelar;
    @FXML private Button btnAceptar;
    @FXML private Button btnSalir;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }
    
    //Este metodo recibe una copia del controlador de ConfigurarCandidatos_ctrl y la ruta de la imagen
    public void inicializarDatos(ConfigurarCandidatos_ctrl ventana, String ruta_imagen){
        if(ventana != null){
            configurar_candidatos = ventana;
        }else{
            btnAceptar.setDisable(true);
        }        
        mostrarImagem(ruta_imagen);
    }
    
    private void mostrarImagem(String ruta_imagen){
        try {
            File file = new File(ruta_imagen);
            Image imagen = new Image(file.toURI().toString());
            imgImagen.setImage(imagen);
            if(imagen != null){
                centrarImagen(imagen, imgImagen);
            }            
        } catch (Exception ex) {
            System.err.println("\n\t\t...error al intentar mostrar la imagen: " + ruta_imagen + "\n" + ex.getMessage());
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
    
    //Este metodo cambia el valor de la variable aceptar_imagen a true mediante el
    //metodo aceptarImagen() en el controlador de ConfigurarCandidatos_ctrl
    @FXML
    private void aceptar(){
        configurar_candidatos.aceptarImagen(true);
        salir();
    }
    
    //Cierra la ventana
    @FXML
    private void cancelar(){
        salir();
    }
    
    //Cierra la ventana
    @FXML
    private void salir(){
        // Obtener el escenario (Stage)
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        // Cerrar el formulario.
        stage.close();
    }
   
}
