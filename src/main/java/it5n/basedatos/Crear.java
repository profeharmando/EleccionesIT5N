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
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Héctor Armando Herrera (profeharmando@gmail.com)
 */
public class Crear {
    
    //Crear la tabla para los datos de los Votantes
    public static boolean crearTablaVotantes(Connection conn){
        String sentenciaSQL = "CREATE TABLE TBL_VOTANTES(" +
        "CODIGO INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
        "RNE VARCHAR(20) NOT NULL, " +
        "NOMBRE VARCHAR(100) NOT NULL, " +
        "VOTO VARCHAR(2) NOT NULL, " +
        "PRIMARY KEY (CODIGO), " +
        "UNIQUE(RNE))";
        try {
            Statement instruccion = conn.createStatement();
            instruccion.execute(sentenciaSQL);
            return true;
        } catch (SQLException ex) {
            System.err.println(
                        "Error de Base de Datos tratando de crear la tabla TBL_VOTANTES.\n"+
                        "Sentencia SQL: \""+sentenciaSQL+"\"\n"+
                        "Mensaje de error(SQL): " + ex.getMessage()+"\n"
            );
            return false;
        }
    }
    
    //Crear la tabla para los datos de los Candidatos
    public static boolean crearTablaCandidatos(Connection conn){
        String sentenciaSQL = "CREATE TABLE TBL_CANDIDATOS(" +
        "CODIGO INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
        "NUMERO INTEGER NOT NULL, " +
        "NOMBRE VARCHAR(100) NOT NULL, " +
        "IMAGEN VARCHAR(200) NOT NULL, " +
        "HABILITADO VARCHAR(2) NOT NULL, " +
        "VOTOS INTEGER NOT NULL, " +
        "PRIMARY KEY (CODIGO), " +
        "UNIQUE(IMAGEN))";
        try {
            Statement instruccion = conn.createStatement();
            instruccion.execute(sentenciaSQL);
            return true;
        } catch (SQLException ex) {
            System.err.println(
                        "Error de Base de Datos tratando de crear la tabla TBL_CANDIDATOS.\n"+
                        "Sentencia SQL: \""+sentenciaSQL+"\"\n"+
                        "Mensaje de error(SQL): " + ex.getMessage()+"\n"
            );
            return false;
        }    
    }
    
    //Crear la tabla para los datos del Centro
    public static boolean crearTablaCentro(Connection conn){
        String sentenciaSQL = "CREATE TABLE TBL_CENTRO(" +
        "CODIGO INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
        "NOMBRE VARCHAR(100) NOT NULL, " +
        "IMAGEN VARCHAR(200) NOT NULL, " +
        "PRIMARY KEY (CODIGO))";
        try {
            Statement instruccion = conn.createStatement();
            instruccion.execute(sentenciaSQL);
            return true;
        } catch (SQLException ex) {
            System.err.println(
                        "Error de Base de Datos tratando de crear la tabla TBL_CENTRO.\n"+
                        "Sentencia SQL: \""+sentenciaSQL+"\"\n"+
                        "Mensaje de error(SQL): " + ex.getMessage()+"\n"
            );
            return false;
        }    
    }
    
}
