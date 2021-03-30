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
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Héctor Armando Herrera (profeharmando@gmail.com)
 */
public class Eliminar {
    
    
    //ELIMINAR UN REGISTRO DE LA TABLA
    
    public static boolean eliminarVotante(Connection conn, int codigo){
        boolean eliminado = false;
        String sentenciaSQL = "DELETE FROM TBL_VOTANTES " +
                              "WHERE CODIGO = ?";
        try {
            PreparedStatement instruccion = conn.prepareStatement(sentenciaSQL);
            instruccion.setInt(1, codigo);
            if(instruccion.executeUpdate() == 1){
                eliminado = true;
            }
        } catch (SQLException ex) {
            System.err.println(
                        "Error de Base de Datos tratando de eliminar el Votante.\n"+
                        "Sentencia SQL: \""+sentenciaSQL+"\"\n"+
                        "Mensaje de error(SQL): " + ex.getMessage()+"\n"
            );
        }
        return eliminado;
    }
    
    public static boolean eliminarCentro(Connection conn, int codigo){
        boolean eliminado = false;
        String sentenciaSQL = "DELETE FROM TBL_CENTRO " +
                              "WHERE CODIGO = ?";
        try {
            PreparedStatement instruccion = conn.prepareStatement(sentenciaSQL);
            instruccion.setInt(1, codigo);
            if(instruccion.executeUpdate() == 1){
                eliminado = true;
            }
        } catch (SQLException ex) {
            System.err.println(
                        "Error de Base de Datos tratando de eliminar el Centro.\n"+
                        "Sentencia SQL: \""+sentenciaSQL+"\"\n"+
                        "Mensaje de error(SQL): " + ex.getMessage()+"\n"
            );
        }
        return eliminado;
    }
    
    public static boolean eliminarCandidato(Connection conn, int codigo){
        boolean eliminado = false;
        String sentenciaSQL = "DELETE FROM TBL_CANDIDATOS " +
                              "WHERE CODIGO = ?";
        try {
            PreparedStatement instruccion = conn.prepareStatement(sentenciaSQL);
            instruccion.setInt(1, codigo);
            if(instruccion.executeUpdate() == 1){
                eliminado = true;
            }
        } catch (SQLException ex) {
            System.err.println(
                        "Error de Base de Datos tratando de eliminar el Candidato.\n"+
                        "Sentencia SQL: \""+sentenciaSQL+"\"\n"+
                        "Mensaje de error(SQL): " + ex.getMessage()+"\n"
            );
        }
        return eliminado;
    }
    
    
    
    //ELIMINAR EL CONTENIDO DE LAS TABLAS
    
    public static boolean eliminarDatosVotantes(Connection conn){
        boolean eliminado = false;
        String sentenciaSQL = "DELETE FROM TBL_VOTANTES";
        try {
            PreparedStatement instruccion = conn.prepareStatement(sentenciaSQL);
            if(instruccion.executeUpdate() > 0){
                eliminado = true;
            }
        } catch (SQLException ex) {
            System.err.println(
                        "Error de Base de Datos tratando de eliminar los datos de los Votantes.\n"+
                        "Sentencia SQL: \""+sentenciaSQL+"\"\n"+
                        "Mensaje de error(SQL): " + ex.getMessage()+"\n"
            );
        }
        return eliminado;
    }
    
    public static boolean eliminarDatosCandidatos(Connection conn){
        boolean eliminado = false;
        String sentenciaSQL = "DELETE FROM TBL_CANDIDATOS";
        try {
            PreparedStatement instruccion = conn.prepareStatement(sentenciaSQL);
            if(instruccion.executeUpdate() > 0){
                eliminado = true;
            }
        } catch (SQLException ex) {
            System.err.println(
                        "Error de Base de Datos tratando de eliminar los datos de los Candidatos.\n"+
                        "Sentencia SQL: \""+sentenciaSQL+"\"\n"+
                        "Mensaje de error(SQL): " + ex.getMessage()+"\n"
            );
        }
        return eliminado;
    }
    
    public static boolean eliminarDatosCentros(Connection conn){
        boolean eliminado = false;
        String sentenciaSQL = "DELETE FROM TBL_CENTRO";
        try {
            PreparedStatement instruccion = conn.prepareStatement(sentenciaSQL);
            if(instruccion.executeUpdate() > 0){
                eliminado = true;
            }
        } catch (SQLException ex) {
            System.err.println(
                        "Error de Base de Datos tratando de eliminar los datos del Centro.\n"+
                        "Sentencia SQL: \""+sentenciaSQL+"\"\n"+
                        "Mensaje de error(SQL): " + ex.getMessage()+"\n"
            );
        }
        return eliminado;
    }
    
    
    
    //ELIMINAR LAS TABLAS
    
    public static boolean eliminarTablaVotantes(Connection conn){
        String sentenciaSQL = "DROP TABLE TBL_VOTANTES";
        try {
            Statement instruccion = conn.createStatement();
            instruccion.execute(sentenciaSQL);
            return true;
        } catch (SQLException ex) {
            System.err.println(
                        "Error de Base de Datos tratando de eliminar la tabla TBL_VOTANTES.\n"+
                        "Sentencia SQL: \""+sentenciaSQL+"\"\n"+
                        "Mensaje de error(SQL): " + ex.getMessage()+"\n"
            );
            return false;
        }
    }
    
    public static boolean eliminarTablaCandidatos(Connection conn){
        String sentenciaSQL = "DROP TABLE TBL_CANDIDATOS";
        try {
            Statement instruccion = conn.createStatement();
            instruccion.execute(sentenciaSQL);
            return true;
        } catch (SQLException ex) {
            System.err.println(
                        "Error de Base de Datos tratando de eliminar la eliminar la tabla TBL_CANDIDATOS.\n"+
                        "Sentencia SQL: \""+sentenciaSQL+"\"\n"+
                        "Mensaje de error(SQL): " + ex.getMessage()+"\n"
            );
            return false;
        }
    }    
    
    public static boolean eliminarTablaCentros(Connection conn){
        String sentenciaSQL = "DROP TABLE TBL_CENTRO";
        try {
            Statement instruccion = conn.createStatement();
            instruccion.execute(sentenciaSQL);
            return true;
        } catch (SQLException ex) {
            System.err.println(
                        "Error de Base de Datos tratando de eliminar la tabla TBL_CENTRO.\n"+
                        "Sentencia SQL: \""+sentenciaSQL+"\"\n"+
                        "Mensaje de error(SQL): " + ex.getMessage()+"\n"
            );
            return false;
        }
    }
    
}
