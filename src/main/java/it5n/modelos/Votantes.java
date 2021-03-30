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

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Héctor Armando Herrera (profeharmando@gmail.com)
 */
public class Votantes {
    
    public IntegerProperty codigo;
    public StringProperty rne;
    public StringProperty nombre;
    public StringProperty voto;
    
    public static int CODIGO_GENERADO = 0;//codigo_generado: variable para obtener el codigo generado por el PreparedStatement al
    //autoincrementar la llave cuando se guarda un nuevo registro.
    
    public Votantes(){
        
    }
    
    public Votantes(int codigo, String rne, String nombre, String voto){
        this.codigo = new SimpleIntegerProperty(codigo);
        this.rne = new SimpleStringProperty(rne);
        this.nombre = new SimpleStringProperty(nombre);
        this.voto = new SimpleStringProperty(voto);
    }
    
    //Metodos atributo: codigo
    public int getCodigo() {
            return codigo.get();
    }
    public void setCodigo(int codigo) {
            this.codigo = new SimpleIntegerProperty(codigo);
    }
    public IntegerProperty CodigoProperty() {
            return codigo;
    }
    //Metodos atributo: rne
    public String getRne() {
            return rne.get();
    }
    public void setRne(String rne) {
            this.rne = new SimpleStringProperty(rne);
    }
    public StringProperty RneProperty() {
            return rne;
    }
    //Metodos atributo: nombre
    public String getNombre() {
            return nombre.get();
    }
    public void setNombre(String nombre) {
            this.nombre = new SimpleStringProperty(nombre);
    }
    public StringProperty NombreProperty() {
            return nombre;
    }
    //Metodos atributo: voto
    public String getVoto() {
            return voto.get();
    }
    public void setVoto(String voto) {
            this.voto = new SimpleStringProperty(voto);
    }
    public StringProperty VotoProperty() {
            return voto;
    }
    
}
