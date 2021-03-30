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

/**
 *
 * @author Héctor Armando Herrera (profeharmando@gmail.com)
 */
public class Alumno {
    
    int codigo;
    String rne;
    String nombre;

    public Alumno(int codigo, String rne, String nombre) {
        this.codigo = codigo;
        this.rne = rne;
        this.nombre = nombre;
    }
    
    
    
}
