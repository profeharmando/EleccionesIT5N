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
public class ConfigurarCandidatos {
    
    public int codigo;
    public int numero;
    public String nombre;
    public String imagen;
    public int votos;
    
    public ConfigurarCandidatos(){
        //Constructor vacio
    }
    
    public ConfigurarCandidatos(int codigo, int numero, String Nombre, String imagen, int votos){
        this.codigo = codigo;
        this.numero = numero;
        this.nombre = nombre;
        this.imagen = imagen;
        this.votos = votos;
    }
    
    public int getCodigo(){
        return codigo;
    }
    public void setCodigo(int codigo){
        this.codigo = codigo;
    }
    
    public int getNumero(){
        return numero;
    }
    public void setNumero(int numero){
        this.numero = numero;
    }
    
    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    
    public String getImagen(){
        return imagen;
    }
    public void setImagen(String imagen){
        this.imagen = imagen;
    }
    
    public int getVotos(){
        return votos;
    }
    public void setVotos(int votos){
        this.votos = votos;
    }
    
}
