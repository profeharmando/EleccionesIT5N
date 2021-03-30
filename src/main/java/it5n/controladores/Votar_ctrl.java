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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import it5n.basedatos.Conexion;
import it5n.basedatos.*;
import it5n.modelos.Candidato;
import it5n.modelos.Votar;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Héctor Armando Herrera (profeharmando@gmail.com)
 */
public class Votar_ctrl implements Initializable {
    
    String RNE;
    String NOMBRE;    
    private int numero_candidatos = 0;//El numero de candidatos que hay registrados en la BD
    private int contador = 0;//Para saber la posicion del candidato en el ArrayList
    private int codigo_candidato = 0;
    @FXML private TableView tblCandidatos;    
    Conexion conn = new Conexion();    
    private Timeline timeline;//Temporizador para cerrar la ventana Voto
    int segundos = 3;
    boolean ya_voto = false;//Para saber si el votante ya ha votado.
    ObservableList<Votar> candidato = FXCollections.observableArrayList();
    ArrayList<Candidato> lista_candidatos = new ArrayList<Candidato>();//Mantiene un ArrayList de los candidatos
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Traer la lista de los candidatos de la BD
        Seleccionar.obtenerCandidatos(conn.establecerConexion(), lista_candidatos);
        //Hace que no detecte pulsaciones de teclas en el teclado
        tblCandidatos.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() != null) {
                   keyEvent.consume();
                 }
            }
        });
    }    
    
    public void inicializarDatos(String rne, String nombre){
        this.RNE = rne;
        this.NOMBRE = nombre;        
        System.out.println("\n\t\t..datos recibidos del votante  Nombre: " + nombre + "   Codigo: " + rne);
        this.numero_candidatos = Seleccionar.obtenerNumeroCandidatos(conn.establecerConexion());
        System.out.println("\n\n\t\t...hay " + numero_candidatos + " candidatos en el sistema\n");
        //Para saber la cantidad de columnas y filas a agregar en el TableView en base al numero de candidatos
        configurarColumnas(numero_candidatos);
        if(numero_candidatos >= 2 && numero_candidatos <= 6){
            agregarImagenesFila1(numero_candidatos);
        }
        if(numero_candidatos > 6 && numero_candidatos <= 12){
            agregarImagenesFila1(numero_candidatos);
            agregarImagenesFila2(numero_candidatos);
        }
        if(numero_candidatos > 12 && numero_candidatos <= 18){
            agregarImagenesFila1(numero_candidatos);
            agregarImagenesFila2(numero_candidatos);
            agregarImagenesFila3(numero_candidatos);
        }        
        tblCandidatos.setItems(candidato);
        //Ocurre cada vez que se selecciona un item del TableView
        tblCandidatos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Votar>(){
                @Override
                public void changed(ObservableValue<? extends Votar> ov, Votar valorAnterior, Votar valorSeleccionado) {
                    if(valorSeleccionado != null){
                        if(!ya_voto){
                            TablePosition posision = (TablePosition) tblCandidatos.getSelectionModel().getSelectedCells().get(0);
                            //Obtiene el numero de celda de izquierda a derecha y de arriba hacia abajo
                            int celda = obtenerNumeroCelda(posision.getRow(), posision.getColumn());
                            //Compara el numero de orden del candidato en el ArrayList con el numero de celda seleccionada
                            for(Candidato lista : lista_candidatos){
                                contador++;
                                if(contador == celda){
                                    System.out.println("\n\t\t..Se ha selecionado el candidato   Codigo: " + lista.getCodigo() + "  Nombre: " + lista.getCandidato());
                                    //Se obtiene el codigo del candidato
                                    codigo_candidato = lista.getCodigo();
                                    break;
                                }
                            }
                            Votar item = (Votar) tblCandidatos.getItems().get(posision.getRow());                            
                            TableColumn col = posision.getTableColumn();
                            ImageView image_view = (ImageView) col.getCellObservableValue(item).getValue();
                            String url = image_view.getImage().getUrl();
                            String ruta_imagen = obtenerRuta(url);
                            System.out.println("\n\t\t...marca puesta en celda con imagen imagen " + ruta_imagen);
                            //Se actualizan los votos del candidato seleccionado
                            guardarVotosCandidato(codigo_candidato);
                            //Sustituye la imagen del candidato por una marca X
                            image_view.setImage(imagenMarca(numero_candidatos));
                            ya_voto = true;
                        }else{
                            System.out.println("\nEl votante YA_VOTÓ");
                        }
                    }
                }  
            }
        );
    }
    
    //Obtiene el numero o posicion de la celda selecionado
    private int obtenerNumeroCelda(int fila, int columna){
        int celda = 0;
        if(fila == 0){
            for(int i=0; i<=columna; i++){
                celda++;
            }
            celda = celda;
        }
        if(fila == 1){
            for(int i=0; i<=columna; i++){
                celda++;
            }
            celda = celda + 6;
        }
        if(fila == 2){
            for(int i=0; i<=columna; i++){
                celda++;
            }
            celda = celda + 12;
        }
        return celda;
    }
    
    //Esta función "obtenerRuta(String url)" identifica el patrón "file:/C:/" en la url del archivo de imagen
    //el patrón "file:/C:/" es es propio de Windows, en Linux el patron es "file:/"
    //si el patrón existe, le quita los primeros 6 caracteres, sino le quita 5
    //En caso de estar en Windows se llama a "reemplazaCaracter(url, "/", "\\")" para cambiar el formato de la url
    private String obtenerRuta(String url){
        String modificada = null;
        String ruta = null;
        if(url.contains("file:/C:/")){
            ruta = reemplazaCaracter(url, "/", "\\");
            modificada = ruta.substring(6, url.length());
        }else{
            modificada = url.substring(5, url.length());
        }
        return modificada;
    }
    
    //Cambia el caracter "remplazado" por el caracter "remplazador"
    public String reemplazaCaracter(String str, String reemplazado, String reemplazador ) {
    char charToreplace = reemplazado.charAt(0);
    String salida = "";
    for (int i = 0; i < str.length(); i++) {         
         if( str.charAt(i) == charToreplace) {
             salida += reemplazador; 
             str.substring(i+1, str.length());
         }else{
             salida += str.charAt(i);
        }            
    }
    return salida;
}
    
    private Image imagenMarca(int num_candidatos){
        Image imagen = null;
        if(num_candidatos == 2){
            imagen = new Image(getClass().getResourceAsStream("/imagenes/marca.png"), 600, 600, false, false);
        }
        if(num_candidatos == 3){
            imagen = new Image(getClass().getResourceAsStream("/imagenes/marca.png"), 400, 400, false, false);
        }
        if(num_candidatos == 4){
            imagen = new Image(getClass().getResourceAsStream("/imagenes/marca.png"), 300, 300, false, false);
        }
        if(num_candidatos == 5){
            imagen = new Image(getClass().getResourceAsStream("/imagenes/marca.png"), 240, 240, false, false);
        }
        if(num_candidatos >= 6){
            imagen = new Image(getClass().getResourceAsStream("/imagenes/marca.png"), 200, 200, false, false);
        }
        return imagen;
    }
    
    private void configurarColumnas(int candidatos){
        if(candidatos == 2){
            TableColumn clm1 = new TableColumn("");
            TableColumn clm2 = new TableColumn("");
            clm1.setMinWidth(600);
            clm2.setMinWidth(600);
            tblCandidatos.getColumns().addAll(clm1, clm2);
            clm1.setCellValueFactory(
                new PropertyValueFactory<Votar, String>("imagen1")
            );
            clm2.setCellValueFactory(
                new PropertyValueFactory<Votar, String>("imagen2")
            );
        }
        if(candidatos == 3){
            TableColumn clm1 = new TableColumn("");
            TableColumn clm2 = new TableColumn("");
            TableColumn clm3 = new TableColumn("");
            clm1.setMinWidth(400);
            clm2.setMinWidth(400);
            clm3.setMinWidth(400);
            tblCandidatos.getColumns().addAll(clm1, clm2, clm3);
            clm1.setCellValueFactory(
                new PropertyValueFactory<Votar, String>("imagen1")
            );
            clm2.setCellValueFactory(
                new PropertyValueFactory<Votar, String>("imagen2")
            );
            clm3.setCellValueFactory(
                new PropertyValueFactory<Votar, String>("imagen3")
            );
        }
        if(candidatos == 4){
            TableColumn clm1 = new TableColumn("");
            TableColumn clm2 = new TableColumn("");
            TableColumn clm3 = new TableColumn("");
            TableColumn clm4 = new TableColumn("");
            clm1.setMinWidth(300);
            clm2.setMinWidth(300);
            clm3.setMinWidth(300);
            clm4.setMinWidth(300);
            tblCandidatos.getColumns().addAll(clm1, clm2, clm3, clm4);
            clm1.setCellValueFactory(
                new PropertyValueFactory<Votar, String>("imagen1")
            );
            clm2.setCellValueFactory(
                new PropertyValueFactory<Votar, String>("imagen2")
            );
            clm3.setCellValueFactory(
                new PropertyValueFactory<Votar, String>("imagen3")
            );
            clm4.setCellValueFactory(
                new PropertyValueFactory<Votar, String>("imagen4")
            );
        }
        if(candidatos == 5){
            TableColumn clm1 = new TableColumn("");
            TableColumn clm2 = new TableColumn("");
            TableColumn clm3 = new TableColumn("");
            TableColumn clm4 = new TableColumn("");
            TableColumn clm5 = new TableColumn("");
            clm1.setMinWidth(240);
            clm2.setMinWidth(240);
            clm3.setMinWidth(240);
            clm4.setMinWidth(240);
            clm5.setMinWidth(240);
            tblCandidatos.getColumns().addAll(clm1, clm2, clm3, clm4, clm5);
            clm1.setCellValueFactory(
                new PropertyValueFactory<Votar, String>("imagen1")
            );
            clm2.setCellValueFactory(
                new PropertyValueFactory<Votar, String>("imagen2")
            );
            clm3.setCellValueFactory(
                new PropertyValueFactory<Votar, String>("imagen3")
            );
            clm4.setCellValueFactory(
                new PropertyValueFactory<Votar, String>("imagen4")
            );
            clm5.setCellValueFactory(
                new PropertyValueFactory<Votar, String>("imagen5")
            );
        }
        if(candidatos >= 6){
            TableColumn clm1 = new TableColumn("");
            TableColumn clm2 = new TableColumn("");
            TableColumn clm3 = new TableColumn("");
            TableColumn clm4 = new TableColumn("");
            TableColumn clm5 = new TableColumn("");
            TableColumn clm6 = new TableColumn("");
            clm1.setMinWidth(205);
            clm2.setMinWidth(205);
            clm3.setMinWidth(205);
            clm4.setMinWidth(205);
            clm5.setMinWidth(205);
            clm6.setMinWidth(205);
            tblCandidatos.getColumns().addAll(clm1, clm2, clm3, clm4, clm5, clm6);
            clm1.setCellValueFactory(
                new PropertyValueFactory<Votar, String>("imagen1")
            );
            clm2.setCellValueFactory(
                new PropertyValueFactory<Votar, String>("imagen2")
            );
            clm3.setCellValueFactory(
                new PropertyValueFactory<Votar, String>("imagen3")
            );
            clm4.setCellValueFactory(
                new PropertyValueFactory<Votar, String>("imagen4")
            );
            clm5.setCellValueFactory(
                new PropertyValueFactory<Votar, String>("imagen5")
            );
            clm6.setCellValueFactory(
                new PropertyValueFactory<Votar, String>("imagen6")
            );
        }
    }
    
    private void agregarImagenesFila1(int num_candidatos){
        if(num_candidatos == 2){
            String ruta_imagen1 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 1);
            String ruta_imagen2 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 2);
            try {
                File file1 = new File(ruta_imagen1);
                Image imagen1 = new Image(file1.toURI().toString(), 600, 600, false, false);
                File file2 = new File(ruta_imagen2);
                Image imagen2 = new Image(file2.toURI().toString(), 600, 600, false, false);
                candidato.add( new Votar(imagen1, imagen2));
            } catch (Exception ex) {
                System.err.println("\n\t\t...error al intentar mostrar las imagenes de lso candidatso.\n\n" + ex.getMessage());
            }
        }
        if(num_candidatos == 3){
            String ruta_imagen1 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 1);
            String ruta_imagen2 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 2);
            String ruta_imagen3 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 3);
            try {
                File file1 = new File(ruta_imagen1);
                Image imagen1 = new Image(file1.toURI().toString(), 400, 400, false, false);
                File file2 = new File(ruta_imagen2);
                Image imagen2 = new Image(file2.toURI().toString(), 400, 400, false, false);
                File file3 = new File(ruta_imagen3);
                Image imagen3 = new Image(file3.toURI().toString(), 400, 400, false, false);
                candidato.add( new Votar(imagen1, imagen2, imagen3));
            } catch (Exception ex) {
                System.err.println("\n\t\t...error al intentar mostrar las imagenes de lso candidatso.\n\n" + ex.getMessage());
            }
        }
        if(num_candidatos == 4){
            String ruta_imagen1 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 1);
            String ruta_imagen2 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 2);
            String ruta_imagen3 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 3);
            String ruta_imagen4 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 4);
            try {
                File file1 = new File(ruta_imagen1);
                Image imagen1 = new Image(file1.toURI().toString(), 300, 300, false, false);
                File file2 = new File(ruta_imagen2);
                Image imagen2 = new Image(file2.toURI().toString(), 300, 300, false, false);
                File file3 = new File(ruta_imagen3);
                Image imagen3 = new Image(file3.toURI().toString(), 300, 300, false, false);
                File file4 = new File(ruta_imagen4);
                Image imagen4 = new Image(file4.toURI().toString(), 300, 300, false, false);
                candidato.add( new Votar(imagen1, imagen2, imagen3, imagen4));
            } catch (Exception ex) {
                System.err.println("\n\t\t...error al intentar mostrar las imagenes de lso candidatso.\n\n" + ex.getMessage());
            }
        }
        if(num_candidatos == 5){
            String ruta_imagen1 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 1);
            String ruta_imagen2 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 2);
            String ruta_imagen3 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 3);
            String ruta_imagen4 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 4);
            String ruta_imagen5 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 5);
            try {
                File file1 = new File(ruta_imagen1);
                Image imagen1 = new Image(file1.toURI().toString(), 240, 240, false, false);
                File file2 = new File(ruta_imagen2);
                Image imagen2 = new Image(file2.toURI().toString(), 240, 240, false, false);
                File file3 = new File(ruta_imagen3);
                Image imagen3 = new Image(file3.toURI().toString(), 240, 240, false, false);
                File file4 = new File(ruta_imagen4);
                Image imagen4 = new Image(file4.toURI().toString(), 240, 240, false, false);
                File file5 = new File(ruta_imagen5);
                Image imagen5 = new Image(file5.toURI().toString(), 240, 240, false, false);
                candidato.add( new Votar(imagen1, imagen2, imagen3, imagen4, imagen5));
            } catch (Exception ex) {
                System.err.println("\n\t\t...error al intentar mostrar las imagenes de lso candidatso.\n\n" + ex.getMessage());
            }
        }
        if(num_candidatos >= 6){
            String ruta_imagen1 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 1);
            String ruta_imagen2 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 2);
            String ruta_imagen3 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 3);
            String ruta_imagen4 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 4);
            String ruta_imagen5 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 5);
            String ruta_imagen6 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 6);
            try {
                File file1 = new File(ruta_imagen1);
                Image imagen1 = new Image(file1.toURI().toString(), 200, 200, false, false);
                File file2 = new File(ruta_imagen2);
                Image imagen2 = new Image(file2.toURI().toString(), 200, 200, false, false);
                File file3 = new File(ruta_imagen3);
                Image imagen3 = new Image(file3.toURI().toString(), 200, 200, false, false);
                File file4 = new File(ruta_imagen4);
                Image imagen4 = new Image(file4.toURI().toString(), 200, 200, false, false);
                File file5 = new File(ruta_imagen5);
                Image imagen5 = new Image(file5.toURI().toString(), 200, 200, false, false);
                File file6 = new File(ruta_imagen6);
                Image imagen6 = new Image(file6.toURI().toString(), 200, 200, false, false);
                candidato.add( new Votar(imagen1, imagen2, imagen3, imagen4, imagen5, imagen6));
            } catch (Exception ex) {
                System.err.println("\n\t\t...error al intentar mostrar las imagenes de los candidatos.\n\n" + ex.getMessage());
            }
        }
    }
    
    private void agregarImagenesFila2(int num_candidatos){
        if(num_candidatos == 7){
            String ruta_imagen7 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 7);
            try {
                File file7 = new File(ruta_imagen7);
                Image imagen7 = new Image(file7.toURI().toString(), 200, 200, false, false);                
                candidato.add( new Votar(imagen7));
            } catch (Exception ex) {
                System.err.println("\n\t\t...error al intentar mostrar las imagenes de lso candidatos.\n\n" + ex.getMessage());
            }
        }
        if(num_candidatos == 8){
            String ruta_imagen7 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 7);
            String ruta_imagen8 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 8);
            try {
                File file7 = new File(ruta_imagen7);
                Image imagen7 = new Image(file7.toURI().toString(), 200, 200, false, false);
                File file8 = new File(ruta_imagen8);
                Image imagen8 = new Image(file8.toURI().toString(), 200, 200, false, false);
                candidato.add( new Votar(imagen7, imagen8));
            } catch (Exception ex) {
                System.err.println("\n\t\t...error al intentar mostrar las imagenes de lso candidatos.\n\n" + ex.getMessage());
            }
        }
        if(num_candidatos == 9){
            String ruta_imagen7 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 7);
            String ruta_imagen8 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 8);
            String ruta_imagen9 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 9);
            try {
                File file7 = new File(ruta_imagen7);
                Image imagen7 = new Image(file7.toURI().toString(), 200, 200, false, false);
                File file8 = new File(ruta_imagen8);
                Image imagen8 = new Image(file8.toURI().toString(), 200, 200, false, false);
                File file9 = new File(ruta_imagen9);
                Image imagen9 = new Image(file9.toURI().toString(), 200, 200, false, false);
                candidato.add( new Votar(imagen7, imagen8, imagen9));
            } catch (Exception ex) {
                System.err.println("\n\t\t...error al intentar mostrar las imagenes de los candidatos.\n\n" + ex.getMessage());
            }
        }
        if(num_candidatos == 10){
            String ruta_imagen7 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 7);
            String ruta_imagen8 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 8);
            String ruta_imagen9 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 9);
            String ruta_imagen10 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 10);
            try {
                File file7 = new File(ruta_imagen7);
                Image imagen7 = new Image(file7.toURI().toString(), 200, 200, false, false);
                File file8 = new File(ruta_imagen8);
                Image imagen8 = new Image(file8.toURI().toString(), 200, 200, false, false);
                File file9 = new File(ruta_imagen9);
                Image imagen9 = new Image(file9.toURI().toString(), 200, 200, false, false);
                File file10 = new File(ruta_imagen10);
                Image imagen10 = new Image(file10.toURI().toString(), 200, 200, false, false);                
                candidato.add( new Votar(imagen7, imagen8, imagen9, imagen10));
            } catch (Exception ex) {
                System.err.println("\n\t\t...error al intentar mostrar las imagenes de los candidatos.\n\n" + ex.getMessage());
            }
        }
        if(num_candidatos == 11){
            String ruta_imagen7 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 7);
            String ruta_imagen8 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 8);
            String ruta_imagen9 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 9);
            String ruta_imagen10 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 10);
            String ruta_imagen11 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 11);
            try {
                File file7 = new File(ruta_imagen7);
                Image imagen7 = new Image(file7.toURI().toString(), 200, 200, false, false);
                File file8 = new File(ruta_imagen8);
                Image imagen8 = new Image(file8.toURI().toString(), 200, 200, false, false);
                File file9 = new File(ruta_imagen9);
                Image imagen9 = new Image(file9.toURI().toString(), 200, 200, false, false);
                File file10 = new File(ruta_imagen10);
                Image imagen10 = new Image(file10.toURI().toString(), 200, 200, false, false);
                File file11 = new File(ruta_imagen11);
                Image imagen11 = new Image(file11.toURI().toString(), 200, 200, false, false);
                candidato.add( new Votar(imagen7, imagen8, imagen9, imagen10, imagen11));
            } catch (Exception ex) {
                System.err.println("\n\t\t...error al intentar mostrar las imagenes de los candidatos.\n\n" + ex.getMessage());
            }
        }
        if(num_candidatos >= 12){
            String ruta_imagen7 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 7);
            String ruta_imagen8 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 8);
            String ruta_imagen9 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 9);
            String ruta_imagen10 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 10);
            String ruta_imagen11 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 11);
            String ruta_imagen12 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 12);
            try {
                File file7 = new File(ruta_imagen7);
                Image imagen7 = new Image(file7.toURI().toString(), 200, 200, false, false);
                File file8 = new File(ruta_imagen8);
                Image imagen8 = new Image(file8.toURI().toString(), 200, 200, false, false);
                File file9 = new File(ruta_imagen9);
                Image imagen9 = new Image(file9.toURI().toString(), 200, 200, false, false);
                File file10 = new File(ruta_imagen10);
                Image imagen10 = new Image(file10.toURI().toString(), 200, 200, false, false);
                File file11 = new File(ruta_imagen11);
                Image imagen11 = new Image(file11.toURI().toString(), 200, 200, false, false);
                File file12 = new File(ruta_imagen12);
                Image imagen12 = new Image(file12.toURI().toString(), 200, 200, false, false);
                candidato.add( new Votar(imagen7, imagen8, imagen9, imagen10, imagen11, imagen12));
            } catch (Exception ex) {
                System.err.println("\n\t\t...error al intentar mostrar las imagenes de los candidatos.\n\n" + ex.getMessage());
            }
        }
    }
    
    private void agregarImagenesFila3(int num_candidatos){
        if(num_candidatos == 13){
            String ruta_imagen13 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 13);
            try {
                File file13 = new File(ruta_imagen13);
                Image imagen13 = new Image(file13.toURI().toString(), 200, 200, false, false);                
                candidato.add( new Votar(imagen13));
            } catch (Exception ex) {
                System.err.println("\n\t\t...error al intentar mostrar las imagenes de los candidatos.\n\n" + ex.getMessage());
            }
        }
        if(num_candidatos == 14){
            String ruta_imagen13 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 13);
            String ruta_imagen14 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 14);
            try {
                File file13 = new File(ruta_imagen13);
                Image imagen13 = new Image(file13.toURI().toString(), 200, 200, false, false);
                File file14 = new File(ruta_imagen14);
                Image imagen14 = new Image(file14.toURI().toString(), 200, 200, false, false);
                candidato.add( new Votar(imagen13, imagen14));
            } catch (Exception ex) {
                System.err.println("\n\t\t...error al intentar mostrar las imagenes de los candidatos.\n\n" + ex.getMessage());
            }
        }
        if(num_candidatos == 15){
            String ruta_imagen13 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 13);
            String ruta_imagen14 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 14);
            String ruta_imagen15 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 15);
            try {
                File file13 = new File(ruta_imagen13);
                Image imagen13 = new Image(file13.toURI().toString(), 200, 200, false, false);
                File file14 = new File(ruta_imagen14);
                Image imagen14 = new Image(file14.toURI().toString(), 200, 200, false, false);
                File file15 = new File(ruta_imagen15);
                Image imagen15 = new Image(file15.toURI().toString(), 200, 200, false, false);
                candidato.add( new Votar(imagen13, imagen14, imagen15));
            } catch (Exception ex) {
                System.err.println("\n\t\t...error al intentar mostrar las imagenes de los candidatos.\n\n" + ex.getMessage());
            }
        }
        if(num_candidatos == 16){
            String ruta_imagen13 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 13);
            String ruta_imagen14 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 14);
            String ruta_imagen15 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 15);
            String ruta_imagen16 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 16);
            try {
                File file13 = new File(ruta_imagen13);
                Image imagen13 = new Image(file13.toURI().toString(), 200, 200, false, false);
                File file14 = new File(ruta_imagen14);
                Image imagen14 = new Image(file14.toURI().toString(), 200, 200, false, false);
                File file15 = new File(ruta_imagen15);
                Image imagen15 = new Image(file15.toURI().toString(), 200, 200, false, false);
                File file16 = new File(ruta_imagen16);
                Image imagen16 = new Image(file16.toURI().toString(), 200, 200, false, false);
                
                candidato.add( new Votar(imagen13, imagen14, imagen15, imagen16));
            } catch (Exception ex) {
                System.err.println("\n\t\t...error al intentar mostrar las imagenes de los candidatos.\n\n" + ex.getMessage());
            }
        }
        if(num_candidatos == 17){
            String ruta_imagen13 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 13);
            String ruta_imagen14 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 14);
            String ruta_imagen15 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 15);
            String ruta_imagen16 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 16);
            String ruta_imagen17 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 17);
            try {
                File file13 = new File(ruta_imagen13);
                Image imagen13 = new Image(file13.toURI().toString(), 200, 200, false, false);
                File file14 = new File(ruta_imagen14);
                Image imagen14 = new Image(file14.toURI().toString(), 200, 200, false, false);
                File file15 = new File(ruta_imagen15);
                Image imagen15 = new Image(file15.toURI().toString(), 200, 200, false, false);
                File file16 = new File(ruta_imagen16);
                Image imagen16 = new Image(file16.toURI().toString(), 200, 200, false, false);
                File file17 = new File(ruta_imagen17);
                Image imagen17 = new Image(file17.toURI().toString(), 200, 200, false, false);
                candidato.add( new Votar(imagen13, imagen14, imagen15, imagen16, imagen17));
            } catch (Exception ex) {
                System.err.println("\n\t\t...error al intentar mostrar las imagenes de los candidatos.\n\n" + ex.getMessage());
            }
        }
        if(num_candidatos >= 18){
            String ruta_imagen13 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 13);
            String ruta_imagen14 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 14);
            String ruta_imagen15 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 15);
            String ruta_imagen16 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 16);
            String ruta_imagen17 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 17);
            String ruta_imagen18 = Seleccionar.obtenerImagenCandidato(conn.establecerConexion(), 18);
            try {
                File file13 = new File(ruta_imagen13);
                Image imagen13 = new Image(file13.toURI().toString(), 200, 200, false, false);
                File file14 = new File(ruta_imagen14);
                Image imagen14 = new Image(file14.toURI().toString(), 200, 200, false, false);
                File file15 = new File(ruta_imagen15);
                Image imagen15 = new Image(file15.toURI().toString(), 200, 200, false, false);
                File file16 = new File(ruta_imagen16);
                Image imagen16 = new Image(file16.toURI().toString(), 200, 200, false, false);
                File file17 = new File(ruta_imagen17);
                Image imagen17 = new Image(file17.toURI().toString(), 200, 200, false, false);
                File file18 = new File(ruta_imagen18);
                Image imagen18 = new Image(file18.toURI().toString(), 200, 200, false, false);
                candidato.add( new Votar(imagen13, imagen14, imagen15, imagen16, imagen17, imagen18));
            } catch (Exception ex) {
                System.err.println("\n\t\t...error al intentar mostrar las imagenes de los candidatos.\n\n" + ex.getMessage());
            }
        }
    }
    
    private void guardarVotosCandidato(int codigo_candidato){
        //Obtiene los votos del acandidatos ya guardados en la BD
        int votos_existentes = Seleccionar.obtenerVotosCandidato(conn.establecerConexion(), codigo_candidato);
        System.out.println("\n\t\t...el candidato tiene actualmente " + votos_existentes + " votos");
        //Suma un voto a los votos ya guardados
        int votos_nuevos = votos_existentes + 1;
        int codigo_votante = Seleccionar.obtenerCodigoVotante(conn.establecerConexion(), NOMBRE, RNE);
        System.out.println("\n\t\t...el codigo del votante es " + codigo_votante);
        //Modifica los datos de votantes y candidatos en la BD
        if(Modificar.cambiarEstadoVotante(conn.establecerConexion(), codigo_votante)){            
            if(Modificar.modificarVotosCandidato(conn.establecerConexion(), codigo_candidato, votos_nuevos)){
                iniciarTiempo();
            }else{
                System.out.println("\n\t\t...no se pudo registrar el voto en la BD para el candidato con codigo " + codigo_candidato);
            }
        }else{
            System.out.println("\n\t\t...no se pudo cambiar el estado del votante en la BD");
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
    
    public void iniciarTiempo(){        
        if (timeline != null) {
            timeline.stop();
        }
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1), (ActionEvent event1) -> {
                    segundos--;
                    System.out.println("\n\t\t...cerrando ventana en " + segundos);
                    if (segundos <= 0) {
                        timeline.stop();
                        cerrarVentana();
                    }
        }
        ));
        timeline.playFromStart();
    }
    
    private void cerrarVentana(){
        Stage stage = (Stage) tblCandidatos.getScene().getWindow();
        stage.close();
    }
    
}
