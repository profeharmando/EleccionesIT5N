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

package it5n.basedatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Héctor Armando Herrera (profeharmando@gmail.com)
 */
public class Insertar {   
    
    public static boolean guardarVotante(Connection conn, String rne, String nombre, String voto){
        boolean guardado = false;
        String sentenciaSQL = "INSERT INTO TBL_VOTANTES(RNE,"
                                                        + " NOMBRE,"
                                                        + " VOTO)"
                              + " VALUES(?,?,?)";
        try {
            PreparedStatement instruccion = conn.prepareStatement(sentenciaSQL, PreparedStatement.RETURN_GENERATED_KEYS);
            instruccion.setString(1, rne);
            instruccion.setString(2, nombre);
            instruccion.setString(3, voto);
            if(instruccion.executeUpdate() == 1){
                ResultSet rs = instruccion.getGeneratedKeys();
                guardado = true;
            }            
        } catch (SQLException ex) {
            System.err.println(
                        "Error de Base de Datos tratando de guardar el Votante.\n"+
                        "Sentencia SQL: \""+sentenciaSQL+"\"\n"+
                        "Mensaje de error(SQL): "+ex.getMessage()+"\n"
            );
        }
        return guardado;
    }
    
    public static void guardarCandidatos(Connection conn, int numero, String nombre, String imagen, String habilitado, int votos){
        String sentenciaSQL = "INSERT INTO TBL_CANDIDATOS("
                                                        + " NUMERO,"
                                                        + " NOMBRE,"
                                                        + " IMAGEN,"
                                                        + " HABILITADO,"
                                                        + " VOTOS)"
                              + " VALUES(?,?,?,?,?)";
        try {
            PreparedStatement instruccion = conn.prepareStatement(sentenciaSQL, PreparedStatement.RETURN_GENERATED_KEYS);
            instruccion.setInt(1, numero);
            instruccion.setString(2, nombre);
            instruccion.setString(3, imagen);
            instruccion.setString(4, habilitado);
            instruccion.setInt(5, votos);
            instruccion.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(
                        "Error de Base de Datos tratando de guardar los Candidatos.\n"+
                        "Sentencia SQL: \"" + sentenciaSQL + "\"\n"+
                        "Mensaje de error(SQL): " + ex.getMessage() + "\n"
            );
        }
    }
    
    public static void guardarCentro(Connection conn, String nombre, String imagen){
        String sentenciaSQL = "INSERT INTO TBL_CENTRO(NOMBRE,"
                                                        + " IMAGEN)"
                              + " VALUES(?,?)";
        try {
            PreparedStatement instruccion = conn.prepareStatement(sentenciaSQL, PreparedStatement.RETURN_GENERATED_KEYS);
            instruccion.setString(1, nombre);
            instruccion.setString(2, imagen);
            instruccion.executeUpdate();
            ResultSet rs = instruccion.getGeneratedKeys();
        } catch (SQLException ex) {
            System.err.println(
                        "Error de Base de Datos tratando de guardar el Centro.\n"+
                        "Sentencia SQL: \""+sentenciaSQL+"\"\n"+
                        "Mensaje de error(SQL): "+ex.getMessage()+"\n"
            );
        }
    }
    
}
