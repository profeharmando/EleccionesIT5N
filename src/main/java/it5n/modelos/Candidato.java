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

package it5n.modelos;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author Héctor Armando Herrera (profeharmando@gmail.com)
 */
public class Candidato {
    int codigo;
    String candidato;
    private Label contar;    
    private TextField nombre;
    private TextField ruta;    
 
    public Candidato(String contar) {
        this.contar = new Label(contar);
        this.nombre = new TextField();
        this.ruta = new TextField();        
    }
    
    public Candidato(int codigo, String candidato) {
        this.codigo = codigo;
        this.candidato = candidato;
    }
 
    public Label getContar() {
        return contar;
    }
    public void setConatar(Label lblContar) {
        this.contar = lblContar;
    }
        
    public TextField getNombre() {
        return nombre;
    }
    public void setNombre(TextField txtNombre) {
        nombre = txtNombre;
    }
    
    public TextField getRuta() {
        return ruta;
    }
    public void setRuta(TextField txtRuta) {
        this.ruta = txtRuta;
    }
    
    public int getCodigo(){
        return codigo;
    }
    public void setCodigo(int codigo){
        this.codigo = codigo;
    }
    
    public String getCandidato(){
        return candidato;
    }
    public void setCandidato(String candidato){
        this.candidato = candidato;
    }
    
    @Override
    public String toString() {
        return nombre.getText();
    }
}
