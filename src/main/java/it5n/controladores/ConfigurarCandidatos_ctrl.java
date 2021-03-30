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
import it5n.modelos.Candidato;
import it5n.basedatos.Conexion;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Héctor Armando Herrera (profeharmando@gmail.com)
 */
public class ConfigurarCandidatos_ctrl implements Initializable {
    private final int max_candidatos = 17;
    private int contar_candidatos = 0;//Inicia el contador para contar los candidatos que se agregan
    private int guardados = 0;
    
    @FXML private Button btnAceptar;
    @FXML private Button btnAgregar;
    @FXML private Button btnCancelar;
    @FXML private Button btnSalir;
    
    @FXML TableView tblCandidatos;
    ObservableList<Candidato> candidato = FXCollections.observableArrayList();
    Conexion conn = new Conexion();
    
     Configurar_ctrl configurar;
    ConfigurarCandidatos_ctrl configurar_candidatos;
    
    private boolean aceptar_imagen;
    String ruta_imagen;
    String ruta_nueva;
    String nombre_archivo;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurar_candidatos = this;
        aceptar_imagen = false;
        TableColumn clmContar = new TableColumn("No");
        TableColumn clmNombre = new TableColumn("Nombre");
        clmNombre.setMinWidth(300);
        TableColumn clmRuta = new TableColumn("Ruta de imagen");
        clmRuta.setMinWidth(400);
        tblCandidatos.getColumns().addAll(clmContar, clmNombre, clmRuta);
        agregarCandidato();
        clmContar.setCellValueFactory(
            new PropertyValueFactory<Candidato,String>("contar")
        );
        clmNombre.setCellValueFactory(
            new PropertyValueFactory<Candidato,String>("nombre")
        );
         clmRuta.setCellValueFactory(     
            new PropertyValueFactory<Candidato,String>("ruta")
        ); 
        tblCandidatos.setItems(candidato);        
        agregarBotonCargarImagen();
        agregarBotonVerImagen();
        iniciarControlesVentana();
    }
    
    @FXML
    public void agregarCandidato(){  
        contar_candidatos++;
        candidato.add( new Candidato(String.valueOf(contar_candidatos)));
        if(contar_candidatos >= max_candidatos){
            btnAgregar.setDisable(true);        
        }        
    }
    
    public void inicializarDatos(Configurar_ctrl configurar){
        this.configurar = configurar;
    }
    
    private void agregarBotonCargarImagen() {
        TableColumn<Candidato, Void> columna = new TableColumn("Buscar");
        Callback<TableColumn<Candidato, Void>, TableCell<Candidato, Void>> cellFactory = new Callback<TableColumn<Candidato, Void>, TableCell<Candidato, Void>>() {
            @Override
            public TableCell<Candidato, Void> call(final TableColumn<Candidato, Void> param) {
                final TableCell<Candidato, Void> celda = new TableCell<Candidato, Void>() {
                    private final Button boton = new Button("...");                    
                    {
                        boton.setMaxWidth(80);
                        boton.setOnAction((ActionEvent event) -> {
                        Candidato candidato = getTableView().getItems().get(getIndex());
                        if(nombreLleno(candidato)){
                            if(!Seleccionar.candidatoYaExiste(conn.establecerConexion(), candidato.getNombre().getText())){
                                String rutaImagen = obtenerRutaImagen();
                                aceptar_imagen = false;//Esta variable se vuelve true al Aceptar la imagen en VerImagen_ctrl
                                mostrarImagen(rutaImagen, candidato.getNombre().getText());
                                if(aceptar_imagen){
                                    candidato.getRuta().setText(rutaImagen);                                    
                                }                                
                                if(candidato.getRuta().getText().isEmpty()){
                                    btnAgregar.setDisable(true);
                                    btnAceptar.setDisable(true);
                                }else{
                                    btnAgregar.setDisable(false);
                                    btnAceptar.setDisable(false);
                                }
                            }else{
                                Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
                                mensaje.setTitle("Nombre ocupado");
                                mensaje.setContentText("");
                                mensaje.setHeaderText("El nombre del candidato o movimiento ya está en uso.\n\n"
                                        + "Escriba otro nombre para el candidato o movimiento.");
                                mensaje.showAndWait();
                                candidato.getNombre().requestFocus();
                            }
                        }                            
                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(boton);
                        }
                    }
                };
                return celda;
            }
        };
        columna.setCellFactory(cellFactory);
        tblCandidatos.getColumns().add(columna);
    }
    
    private void agregarBotonVerImagen() {
        TableColumn<Candidato, Void> columna = new TableColumn("Ver");
        Callback<TableColumn<Candidato, Void>, TableCell<Candidato, Void>> cellFactory = new Callback<TableColumn<Candidato, Void>, TableCell<Candidato, Void>>() {
            @Override
            public TableCell<Candidato, Void> call(final TableColumn<Candidato, Void> param) {
                final TableCell<Candidato, Void> celda = new TableCell<Candidato, Void>() {
                    private final Button boton = new Button("...");
                    {
                        boton.setMaxWidth(80);
                        boton.setOnAction((ActionEvent event) -> {                            
                        Candidato candidato = getTableView().getItems().get(getIndex());
                        if(rutaLlena(candidato)){
                            String nombre = candidato.getNombre().getText();                            
                            String rutaImagen = candidato.getRuta().getText();                            
                            mostrarImagen(rutaImagen, nombre);
                        }                        
                        });                        
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(boton);
                        }
                    }
                };
                return celda;
            }
        };
        columna.setCellFactory(cellFactory);
        tblCandidatos.getColumns().add(columna);
        
    }
    
    private boolean nombreLleno(Candidato candidato){
        boolean lleno = false;
        if(!candidato.getNombre().getText().isEmpty()){
            lleno = true;
        }else{
            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setTitle("Nombre de candidato vacío");
            mensaje.setContentText("");
            mensaje.setHeaderText("Ingrese un nombre para el candidato o movimiento.");
            mensaje.showAndWait();
            candidato.getNombre().requestFocus();
        }
        return lleno;
    }
    
    private boolean rutaLlena(Candidato candidato){
        boolean lleno = false;
        if(!candidato.getRuta().getText().isEmpty()){
            lleno = true;
        }else{
            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setTitle("Ruta de imagen vacía");
            mensaje.setContentText("");
            mensaje.setHeaderText("Seleccione una imagen para el candidato o movimiento.");
            mensaje.showAndWait();
        }
        return lleno;
    }
    
    private String obtenerRutaImagen(){
        String ruta = "";
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Obtener archivo de imagen");
        //Muestra por defecto el directorio de usuario (Home)
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        // Agregar filtros para facilitar la busqueda
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Archivos de imagen", "*.png")
        );
        // Mostrar el FileChooser
        File archivo = fileChooser.showOpenDialog(null);
        // Mostar la ruta del archivo
        if (archivo != null) {
            ruta = archivo.getAbsolutePath();
        }else{
            System.out.println(":( upssss... NO hay archivo de imagen");
        }
        return ruta;
    }
    
    //Este metodo se usa desde la clase VerImagen_ctrl para aceptar o no la imagen mostrada
    public void aceptarImagen(boolean valor){
        aceptar_imagen = valor;
    }
    
    private void mostrarImagen(String rutaImagen, String nombre){
        if(!rutaImagen.isEmpty()){
            String formulario = "/fxml/VerImagen.fxml";
            try {            
                FXMLLoader loader = new FXMLLoader(getClass().getResource(formulario));
                Stage stage = new Stage(StageStyle.DECORATED);
                stage.setScene( new Scene((Pane) loader.load()));
                VerImagen_ctrl controlador =  loader.<VerImagen_ctrl>getController();
                controlador.inicializarDatos(configurar_candidatos, rutaImagen);
                Image icon = new Image(getClass().getResourceAsStream("/imagenes/icono.png"));
                stage.getIcons().add(icon);
                stage.setTitle("Imagen para " + nombre);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
            } catch (IOException e) {
                System.err.println("Error al intentar mostrar la ventana " + formulario + ": \n" + e.getMessage());
            }
        }else{
            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setTitle("La ruta de imagen está vacía");
            mensaje.setContentText("");
            mensaje.setHeaderText("Debe seleccionar una imagen.\n Utilice el boton \"...\" en la columna Cargar.");
            mensaje.show();
        }
    }
    
    private void iniciarControlesVentana(){
        tblCandidatos.setDisable(false);
        btnAgregar.setDisable(true);
        btnAceptar.setDisable(true);
        btnCancelar.setDisable(true);
        btnSalir.setDisable(false);
    }
    
    @FXML
    public void aceptar(){        
        Alert confirmar = new Alert(Alert.AlertType.CONFIRMATION);
        confirmar.setTitle("Guardar candidatos o movimientos");
        confirmar.setHeaderText("¿Agregar los candidatos o movimientos al sistema?\n\n"
                + "Seleccione \"Aceptar\" para Guardar");
        confirmar.setContentText("");
        Optional<ButtonType> result = confirmar.showAndWait();
        if(result.get() == ButtonType.OK){
            candidato.forEach(fila -> {
                if(!Seleccionar.candidatoYaExiste(conn.establecerConexion(), fila.getNombre().getText())){
                    if(!Seleccionar.imagenYaExiste(conn.establecerConexion(), fila.getRuta().getText())){                        
                        ruta_imagen = fila.getRuta().getText();
                        ruta_nueva = copiarImagen(ruta_imagen);
                        Insertar.guardarCandidatos(conn.establecerConexion(), numeroCandidato(), fila.getNombre().getText(), ruta_nueva, "SI", 0);
                        System.out.println("Guardado en BD el candidato " + fila.getNombre().getText() + ", con imagen en " + ruta_nueva);
                        guardados++;                        
                    }else{
                        Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
                        mensaje.setTitle("No se puede guardar la Imagen");
                        mensaje.setContentText("");
                        mensaje.setHeaderText("No se puede guardar la imagen para el candidato \"" + fila.getNombre().getText() + "\".\n\n"
                                + "La imagen ya está en uso por otro candidato.");
                        mensaje.show();
                        System.out.println("NO se guarda el candidato: " + fila.getNombre().getText() + " IMAGEN YA EXISTE");
                    }                        
                }else{
                    Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
                    mensaje.setTitle("No se puede guardar el Candidato");
                    mensaje.setContentText("");
                    mensaje.setHeaderText("No se puede guardar el candidato \"" + fila.getNombre().getText() + "\".\n\n"
                            + "El nombre del candidato ya está en uso.");
                    mensaje.show();
                    System.out.println("NO se guarda el candidato: " + fila.getNombre().getText() + " YA EXISTE");
                }                    
            });
            if(guardados > 0){
                Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
                mensaje.setTitle("Guardar cardidatos");
                mensaje.setContentText("");
                mensaje.setHeaderText("Se han guardado " + guardados + " candidatos.");
                mensaje.showAndWait();
                tblCandidatos.setDisable(true);
                btnAceptar.setDisable(true);
                btnAgregar.setDisable(true);
                configurar.btnConfigurarCandidatos.setDisable(true);
                btnSalir.requestFocus();
            }else{
                Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
                mensaje.setTitle("No se guarda cardidatos");
                mensaje.setContentText("");
                mensaje.setHeaderText("No se guardó candidatos.");
                mensaje.showAndWait();
                btnSalir.requestFocus();
            }
        }
        guardados = 0;
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
            System.out.println("\nError tratando de copiar el archivo de imagen del candidato.\n"
                    + "Error: " + ex.getMessage() + "\n");
        }
        return nueva_ruta;
    }
    
    private int numeroCandidato(){
        int numero = Seleccionar.obtenerNumeroCandidatos(conn.establecerConexion());
        return numero + 1;
    }
    
    @FXML
    public void salir(){
        // Obtener el escenario (Stage)
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        // Cerrar el formulario.
        stage.close();
    }
    
}
