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

package it5n.utilidades;

/**
 *
 * @author Héctor Armando Herrera (profeharmando@gmail.com)
 */
public class ArrLstAlumno {
    
    private String rne;
    private String nombre;
    
    public ArrLstAlumno(String rne, String nombre) {
        this.rne = rne;
        this.nombre = nombre;
    }
    
    public String getRne() {
        return rne;
    }
    
    public void setRne(String rne) {
            this.rne = rne;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
            this.nombre = nombre;
    }
    
    public String mostrarDatos(){
        return "El alumno es " + getRne() + " " + getNombre();
    }
}
