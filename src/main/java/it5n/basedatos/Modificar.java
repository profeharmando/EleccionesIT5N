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

/**
 *
 * @author Héctor Armando Herrera (profeharmando@gmail.com)
 */
public class Modificar {
    
    public int modificarVotante(Connection conn, String rne, String nombre, int codigo){
        String sentenciaSQL = "UPDATE TBL_VOTANTES " +
                              "SET RNE = ?, " +
                                  "NOMBRE = ?, " +                                  
                              "WHERE CODIGO = ?";
        try {
            PreparedStatement instruccion = conn.prepareStatement(sentenciaSQL);
            instruccion.setString(1, rne);
            instruccion.setString(2, nombre);            
            instruccion.setInt(3, codigo);
            return instruccion.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(
                        "Error de Base de Datos tratando de modificar el Votante.\n"+
                        "Sentencia SQL: \""+sentenciaSQL+"\"\n"+
                        "Mensaje de error(SQL): " + ex.getMessage()+"\n"
            );
            return 0;
        }
    }
    
    public static boolean modificarVotosCandidato(Connection conn, int codigo, int voto){
        boolean guardado = false;
        String sentenciaSQL = "UPDATE TBL_CANDIDATOS "
                    + "SET VOTOS = ? "
                    + "WHERE CODIGO = ?";
        try {
            PreparedStatement instruccion = conn.prepareStatement(sentenciaSQL);
            instruccion.setInt(1, voto);
            instruccion.setInt(2, codigo);
            if(instruccion.executeUpdate() > 0){
                guardado = true;
                System.out.println("\t\t...Se han actualizado los votos del candidato con codigo " + codigo);
            }                 
        } catch (SQLException ex) {
            System.err.println(
                        "Error de Base de Datos tratando de guardar los voto para el candidatos con codigo " + codigo + "\n"+
                        "Sentencia SQL: \"" + sentenciaSQL + "\"\n"+
                        "Mensaje de error(SQL): " + ex.getMessage() + "\n"
            );
        }
        return guardado;
    }
    
    public static boolean restablecerVotosCandidato(Connection conn){
        boolean guardado = false;
        String sentenciaSQL = "UPDATE TBL_CANDIDATOS "
                    + "SET VOTOS = ? "
                    + "WHERE CODIGO > ?";
        try {
            PreparedStatement instruccion = conn.prepareStatement(sentenciaSQL);
            instruccion.setInt(1, 0);
            instruccion.setInt(2, 0);
            if(instruccion.executeUpdate() > 0){
                guardado = true;
                System.out.println("\t\t...Se han restablecido los votos de todos los candidatos.");
            }                 
        } catch (SQLException ex) {
            System.err.println(
                        "Error de Base de Datos tratando de restablecer los voto para todos los candidatos.\n"+
                        "Sentencia SQL: \"" + sentenciaSQL + "\"\n"+
                        "Mensaje de error(SQL): " + ex.getMessage() + "\n"
            );
        }
        return guardado;
    }
    
    public static boolean cambiarEstadoVotante(Connection conn, int codigo){
        boolean modificado = false;
        String sentenciaSQL = "UPDATE TBL_VOTANTES " +
                              "SET VOTO = ? " +      
                              "WHERE CODIGO = ?";
        try {
            PreparedStatement instruccion = conn.prepareStatement(sentenciaSQL);
            instruccion.setString(1, "SI");
            instruccion.setInt(2, codigo);
            if(instruccion.executeUpdate() > 0){
                modificado = true;
                System.out.println("\t\t...Se han actualizado el estado de votó a SI, del votante codigo " + codigo);
            }
        } catch (SQLException ex) {
            System.err.println(
                        "Error de Base de Datos tratando de modificar el estado del Votante codigo " + codigo + "\n"+
                        "Sentencia SQL: \""+sentenciaSQL+"\"\n"+
                        "Mensaje de error(SQL): " + ex.getMessage() + "\n"
            );            
        }
        return modificado;
    }
    
    public static boolean restablecerVotoVotantes(Connection conn){
        boolean modificado = false;
        String sentenciaSQL = "UPDATE TBL_VOTANTES " +
                              "SET VOTO = ? " +      
                              "WHERE CODIGO > ?";
        try {
            PreparedStatement instruccion = conn.prepareStatement(sentenciaSQL);
            instruccion.setString(1, "NO");
            instruccion.setInt(2, 0);
            if(instruccion.executeUpdate() > 0){
                modificado = true;
                System.out.println("\t\t...Se ha restablecido el estado de Votó a \"NO\" de todos los votantes.");
            }
        } catch (SQLException ex) {
            System.err.println(
                        "Error de Base de Datos tratando de modificar el estado ç\"Votó = NO\" de todos los Votantes.\n"+
                        "Sentencia SQL: \""+sentenciaSQL+"\"\n"+
                        "Mensaje de error(SQL): " + ex.getMessage() + "\n"
            );            
        }
        return modificado;
    }
    
//    public static boolean cambiarEstadoVotante(Connection conn, String votar, String rne){
//        boolean modificado = false;
//        String sentenciaSQL = "UPDATE TBL_VOTANTES " +
//                              "SET VOTO = '" + votar + "'" +
//                              "WHERE RNE = '" + rne + "'";
//        try {
//            Statement instruccion = conn.createStatement();
//            if(instruccion.execute(sentenciaSQL)){
//                modificado = true;
//            }
//        } catch (SQLException ex) {
//            System.err.println(
//                        "Error de Base de Datos tratando de modificar el estado del Votante.\n"+
//                        "Sentencia SQL: \""+sentenciaSQL+"\"\n"+
//                        "Mensaje de error(SQL): " + ex.getMessage()+"\n"
//            );            
//        }
//        return modificado;
//    }
    
    public int modificarCentro(Connection conn, String nombre, String imagen, int codigo){
        System.out.println("------------>  MODIFICANDO CENTRO");
        String sentenciaSQL = "UPDATE TBL_CENTRO " +
                              "SET NOMBRE = ?, " +
                                  "IMAGEN = ?, " +                                  
                              "WHERE CODIGO = ?";
        try {
            PreparedStatement instruccion = conn.prepareStatement(sentenciaSQL);
            instruccion.setString(1, nombre);
            instruccion.setString(2, imagen);            
            instruccion.setInt(3, codigo);
            return instruccion.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(
                        "Error de Base de Datos tratando de modificar el Centro.\n"+
                        "Sentencia SQL: \""+sentenciaSQL+"\"\n"+
                        "Mensaje de error(SQL): " + ex.getMessage()+"\n"
            );
            return 0;
        }
    }
    
    public static int reiniciarVotosCandidatos(Connection conn, int votos){
        String sentenciaSQL = "UPDATE TBL_CANDIDATOS " +
                              "SET VOTOS = ? " +      
                              "WHERE NUMERO > 0";
        try {
            PreparedStatement instruccion = conn.prepareStatement(sentenciaSQL);
            instruccion.setInt(1, votos);
            return instruccion.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(
                        "Error de Base de Datos tratando de modificar a cero los votos de los candidatos.\n"+
                        "Sentencia SQL: \""+sentenciaSQL+"\"\n"+
                        "Mensaje de error(SQL): " + ex.getMessage()+"\n"
            );
            return 0;
        }
    }
    
}
