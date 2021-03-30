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
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import it5n.basedatos.Conexion;
import it5n.modelos.VerResultados;
import java.text.DecimalFormat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Héctor Armando Herrera (profeharmando@gmail.com)
 */
public class VerResultados_ctrl implements Initializable {    
    
    int contar = 0;
    int votos_totales = 0;
    ObservableList<VerResultados> candidato = FXCollections.observableArrayList();
    ObservableList<VerResultados> resultados = FXCollections.observableArrayList();
    
    @FXML private TableView tblResultados;
    @FXML private Label lblVotosTotales;
    @FXML private Button btnSalir;
    
    Seleccionar selecionar = new Seleccionar();
    
    Conexion conn = new Conexion();   

    public void initialize(URL url, ResourceBundle rb) {
        TableColumn clmContar = new TableColumn("No");
        clmContar.setMinWidth(60);
        TableColumn clmNombre = new TableColumn("Nombre");
        clmNombre.setMinWidth(300);
        TableColumn clmProgreso = new TableColumn("Gráfica");
        clmProgreso.setMinWidth(400);
        TableColumn clmVotos = new TableColumn("Votos");
        clmVotos.setMinWidth(100);
        TableColumn clmPorciento = new TableColumn("%");
        clmPorciento.setMinWidth(100);
        tblResultados.getColumns().addAll(clmContar, clmNombre, clmProgreso, clmVotos, clmPorciento);
        votos_totales = Seleccionar.optenerVotosTotales(conn.establecerConexion());
        agregarCandidatos();
        lblVotosTotales.setText("Votos totales " + String.valueOf(votos_totales));
        clmContar.setCellValueFactory(
            new PropertyValueFactory<VerResultados,String>("contar")
        );
        clmNombre.setCellValueFactory(
            new PropertyValueFactory<VerResultados,String>("nombre")
        );
        clmProgreso.setCellValueFactory(     
            new PropertyValueFactory<VerResultados,String>("progreso")
        );
        clmVotos.setCellValueFactory(     
            new PropertyValueFactory<VerResultados,String>("votos")
        );
        clmPorciento.setCellValueFactory(     
            new PropertyValueFactory<VerResultados,String>("porciento")
        );
        tblResultados.setItems(candidato);
        iniciarControlesVentana();
    }
    
    @FXML
    public void agregarCandidatos(){
        selecionar.obtenerResultados(conn.establecerConexion(), resultados);
        resultados.forEach(lista -> {
            contar++;
            double progreso = (Double.parseDouble(lista.getVotos().getText()) / votos_totales);
            double porciento = ((Double.parseDouble(lista.getVotos().getText()) / votos_totales) * 100);
            DecimalFormat porciento_formateado = new DecimalFormat("###.##");
            candidato.add( new VerResultados(String.valueOf(contar), lista.getNombre().getText(), progreso, lista.getVotos().getText(), String.valueOf(porciento_formateado.format(porciento)) + " %"));
        });
                       
    }
    
    public void inicializarDatos(boolean p_1, boolean p_2, boolean p_3){
        
    }
    
   
    private void iniciarControlesVentana(){
        btnSalir.setDisable(false);
    }
    
    @FXML
    public void salir(){
        // Obtener el escenario (Stage)
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        // Cerrar el formulario.
        stage.close();
    }
    
}
