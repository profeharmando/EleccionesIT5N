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

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Héctor Armando Herrera (profeharmando@gmail.com)
 */
public class ImportarVotantes {
    
    private SimpleStringProperty rne;
    private SimpleStringProperty nombre;
    private SimpleStringProperty voto;
    
    public ImportarVotantes(String rne, String nombre, String voto) {
        this.rne = new SimpleStringProperty(rne);
        this.nombre = new SimpleStringProperty(nombre);
        this.voto = new SimpleStringProperty(voto);
    }
    
    public String getRne() {
        return rne.get();
    }
    public void setRne(String rne) {
            this.rne = new SimpleStringProperty(rne);
    }
    public String getNombre() {
        return nombre.get();
    }
    public void setNombre(String nombre) {
            this.nombre = new SimpleStringProperty(nombre);
    }
    public String getVoto() {
        return voto.get();
    }
    public void setVoto(String voto) {
            this.voto = new SimpleStringProperty(voto);
    }
    
    @Override
    public String toString() {
        return "Rne: " + rne +
                "   nombre: " + nombre +
                "   voto: " + nombre;
    }
}
