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
import it5n.modelos.ImportarVotantes;
import it5n.utilidades.ArrLstAlumno;
import it5n.utilidades.ImportarHojaCalculo;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import it5n.basedatos.Conexion;

/**
 * FXML Controller class
 *
 * @author Héctor Armando Herrera (profeharmando@gmail.com)
 */
public class ImportarVotantes_ctrl implements Initializable {
    
    //Elementos GUI
    @FXML private TextField txtRuta;
    @FXML private Button btnBuscar;
    @FXML private TableView<ImportarVotantes> tblAlumnos;
    @FXML private TableColumn<ImportarVotantes, String> clmRne; 
    @FXML private TableColumn<ImportarVotantes, String> clmNombre;
    @FXML private Button btnAceptar;
    @FXML private Button btnEliminarFila;
    @FXML private Button btnCancelar;
    @FXML private Button btnSalir;
    
    private int contar = 0;
    
    Conexion conn = new Conexion();
    
    //ArrayList para llenar el ObservableList
    private ArrayList<ArrLstAlumno> lista = new ArrayList<ArrLstAlumno>();
    //ObservableList para los llenar el TableView
    private ObservableList<ImportarVotantes> lista_alumnos = FXCollections.observableArrayList(); 
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        //Hace editable el TableView
        tblAlumnos.setEditable(true);
        clmRne.setCellFactory(TextFieldTableCell.<ImportarVotantes>forTableColumn());
        clmNombre.setCellFactory(TextFieldTableCell.<ImportarVotantes>forTableColumn());
        //Hace editable la columna Rne
        clmRne.setCellFactory(TextFieldTableCell.forTableColumn());
        clmRne.setOnEditCommit(new EventHandler<CellEditEvent<ImportarVotantes, String>>(){
                public void handle(CellEditEvent<ImportarVotantes, String> t) {
                    ((ImportarVotantes) t.getTableView().getItems().get(t.getTablePosition().getRow())).setRne(t.getNewValue());
                }
            }
        );
        //Hace editable la columna Nombre
        clmNombre.setCellFactory(TextFieldTableCell.forTableColumn());
        clmNombre.setOnEditCommit(new EventHandler<CellEditEvent<ImportarVotantes, String>>(){
                public void handle(CellEditEvent<ImportarVotantes, String> t) {
                    ((ImportarVotantes) t.getTableView().getItems().get(t.getTablePosition().getRow())).setNombre(t.getNewValue());
                }
            }
        );        
        inhabilitarLimpiarElementos();
        gestionarEventos();
        btnBuscar.requestFocus();
    } 
    
    public void inicializarDatos(boolean p_1, boolean p_2, boolean p_3){
        
    }
    
    public void gestionarEventos(){
        //Ocurre cada vez que se selecciona un item del TableView
        tblAlumnos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ImportarVotantes>(){
                @Override
                public void changed(ObservableValue<? extends ImportarVotantes> ov, 
                    ImportarVotantes valorAnterior, ImportarVotantes valorSeleccionado) {
                    if(valorSeleccionado != null){
                        btnEliminarFila.setDisable(false);
                    }
                }  
            }
        );
    }
    
    @FXML
    private void buscarArchivo(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Buscar archivo de Hoja de Cálculo");
        //Muestra por defecto el directorio de usuario (Home)
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        // Agregar filtros para facilitar la busqueda
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Archivos de Hoja de Cálculo", "*.xls")
        );
        // Mostrar el FileChooser
        File archivo = fileChooser.showOpenDialog(null);
        // Mostar la ruta del archivo
        if (archivo != null) {
            txtRuta.setText(archivo.getAbsolutePath());
            ImportarHojaCalculo importar = new ImportarHojaCalculo();
            importar.leerArchivoExcel(archivo);
            llenarTablaAlumnos();
        }else{
            txtRuta.setText("NO HAY ARCHIVO");
        }
    }
    
    private void llenarTablaAlumnos(){
        tblAlumnos.getItems().clear();
        //Limpia el ArrayList
        lista.clear();
        //Limpiar el ObservableList
        lista_alumnos.clear();
        //Trae los datos del ArrayList
        lista = ImportarHojaCalculo.obtenerListaAlumnos();
        //Agrega los elementos del ArrayList al modelo del ObservableList
        for(ArrLstAlumno aa: lista){
            lista_alumnos.add(new ImportarVotantes(aa.getRne(), aa.getNombre().toUpperCase(), "NO"));
        }
        //Agrega el ObservableList al TableView
        tblAlumnos.setItems(lista_alumnos);
        //Muestra los datos en las columnas del TableView
        clmRne.setCellValueFactory(new PropertyValueFactory<ImportarVotantes, String>("rne"));
        clmNombre.setCellValueFactory(new PropertyValueFactory<ImportarVotantes, String>("nombre"));
        if(tblAlumnos.getItems().isEmpty()){
            inhabilitarLimpiarElementos();
        }else{            
            btnAceptar.setDisable(false);
            btnCancelar.setDisable(false);
        }
    }
    
    @FXML
    private void eliminarFila(){
        lista_alumnos.remove(tblAlumnos.getSelectionModel().getSelectedIndex());
        if(tblAlumnos.getItems().isEmpty()){
            inhabilitarLimpiarElementos();
        }
    }
    
    @FXML
    private void guardarVotantes(){
        if(lista_alumnos.size() > 0){
            Alert confirmar = new Alert(Alert.AlertType.CONFIRMATION);
            confirmar.setTitle("Agregar Alumnos(as)");
            confirmar.setHeaderText("Se dispone a agregar " + lista_alumnos.size() + " nuevos votantes al sistema." +
                    "\n\n¿Desea continuar?");
            confirmar.setContentText("");
            Optional<ButtonType> result = confirmar.showAndWait();
            if(result.get() == ButtonType.OK){
                lista_alumnos.forEach((registro) -> {
                    if(Insertar.guardarVotante(conn.establecerConexion(), registro.getRne().toString(), registro.getNombre().toUpperCase(), registro.getVoto().toString())){
                        contar++;
                    }                    
                });
                if(contar == lista_alumnos.size()){
                    Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
                    mensaje.setTitle("Guardadar votantes");
                    mensaje.setContentText("");
                    mensaje.setHeaderText("Se agregaron " + lista_alumnos.size() + " nuevos votantes.");
                    mensaje.show();
                }
                if(contar < lista_alumnos.size()){
                    Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
                    mensaje.setTitle("Guardadar votantes");
                    mensaje.setContentText("");
                    mensaje.setHeaderText("Se agregaron " + contar + " nuevos votantes de " + lista_alumnos.size() + ".\n\n" + (lista_alumnos.size() - contar) + " no se agregaron, posiblemente ya existen en el sistema.");
                    mensaje.show();
                }
                limpiarListas();
                inhabilitarLimpiarElementos();
            }
        }
    }
    
    private void limpiarListas(){
        lista_alumnos.clear();
        lista.clear();
        tblAlumnos.getItems().clear();
    }

    private void inhabilitarLimpiarElementos(){
        txtRuta.setText(null);
        txtRuta.setDisable(false);
        btnBuscar.setDisable(false);        
        btnAceptar.setDisable(true);
        btnCancelar.setDisable(true);
        btnEliminarFila.setDisable(true);       
    } 
    
    @FXML
    private void cancelar(){
        txtRuta.setText(null);
        lista_alumnos.clear();
        lista.clear();
        tblAlumnos.getItems().clear();
        btnAceptar.setDisable(true);
        btnEliminarFila.setDisable(true);
        btnSalir.requestFocus();
    }
    
    @FXML
    private void salir(){
        // Obtener el escenario (Stage)
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        // Cerrar el formulario.
        stage.close();
    }
    
}
