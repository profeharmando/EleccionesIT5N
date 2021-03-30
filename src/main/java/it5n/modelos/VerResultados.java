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
import javafx.scene.control.ProgressBar;

/**
 *
 * @author Héctor Armando Herrera (profeharmando@gmail.com)
 */
public class VerResultados {
    private Label contar;
    private Label nombre;
    private ProgressBar progreso;
    private Label votos;
    private Label porciento;
    
    public VerResultados(String contar, String nombre, Double progreso, String votos, String porciento) {
        this.contar = new Label(contar);
        this.nombre = new Label(nombre);
        this.progreso = new ProgressBar(progreso);
        this.progreso.setPrefWidth(400);
        this.votos = new Label(votos);
        this.porciento = new Label(porciento);
    }
    
    public VerResultados(String nombre, String votos) {
        this.nombre = new Label(nombre);
        this.votos = new Label(votos);
    }
 
    public Label getContar() {
        return contar;
    }    
    public void setConatar(Label lblContar) {
        this.contar = lblContar;
    }
        
    public Label getNombre() {
        return nombre;
    }
    public void setNombre(Label lblNombre) {
        nombre = lblNombre;
    }
    
    public ProgressBar getProgreso() {
        return progreso;
    }
    public void setProgreso(ProgressBar pgbProgreso) {
        this.progreso = pgbProgreso;
    }
    
    public Label getVotos() {
        return votos;
    }    
    public void setVotos(Label lblVotos) {
        this.votos = lblVotos;
    }
    
    public Label getPorciento(){
        return porciento;
    }
    public void setPorciento(Label porciento){
        this.porciento = porciento;
    }
    
    @Override
    public String toString() {
        return nombre.getText();
    }
}
