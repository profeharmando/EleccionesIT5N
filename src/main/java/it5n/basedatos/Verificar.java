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
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import it5n.basedatos.Crear;

/**
 *
 * @author Héctor Armando Herrera (profeharmando@gmail.com)
 */
public class Verificar {
    
    public void verificarCrearTablaVotantes(Connection conn){    
        //Verifica si existe, sino crea la tabla TBL_VOTANTES
        try {
            DatabaseMetaData dmd = conn.getMetaData();
            ResultSet rs = dmd.getTables(null, "APP", "TBL_VOTANTES",null);
            if(!rs.next()){
                System.out.println("\t\t...la tabla TBL_VOTANTES no existe, será craeda");
                if(Crear.crearTablaVotantes(conn)){
                    System.out.println("\t\t...la tabla TBL_VOTANTES ha sido creda.");
                }
            } else{
                System.out.println("\t\t...la tabla TBL_VOTANTES ya existe.");
            }
        } catch (SQLException ex) {
            System.err.println(
                        "Error de Base de Datos tratando de verificar si existe y sino crear la tabla TBL_VOTANTES.\n"+
                        "Mensaje de error(SQL): " + ex.getMessage()+"\n"
            );
        }
    }
    
    public void verificarCrearTablaCandidatos(Connection conn){    
        //Verifica si existe, sino crea la tabla TBL_CANDIDATOS
        try {
            DatabaseMetaData dmd = conn.getMetaData();
            ResultSet rs = dmd.getTables(null, "APP", "TBL_CANDIDATOS",null);
            if(!rs.next()){
                System.out.println("\t\t...la tabla TBL_CANDIDATOS no existe, será craeda");
                if(Crear.crearTablaCandidatos(conn)){
                    System.out.println("\t\t...la tabla TBL_CANDIDATOS ha sido creda.");
                }
            }else{
                System.out.println("\t\t...la tabla TBL_CANDIDATOS ya existe.");
            }
        } catch (SQLException ex) {
            System.err.println(
                        "Error de Base de Datos tratando de verificar si existe y sino crear la tabla TBL_CANDIDATOS.\n"+
                        "Mensaje de error(SQL): " + ex.getMessage()+"\n"
            );
        }
    }
    
    
    public void verificarCrearTablaCentro(Connection conn){  
        //Verifica si existe, sino crea la tabla TBL_CENTRO
        try {
            DatabaseMetaData dmd = conn.getMetaData();
            ResultSet rs = dmd.getTables(null, "APP", "TBL_CENTRO",null);
            if(!rs.next()){
                System.out.println("\t\t...la tabla TBL_CENTRO no existe, será craeda");
                if(Crear.crearTablaCentro(conn)){
                    System.out.println("\t\t...la tabla TBL_CENTRO ha sido creda.");
                }
            }else{
                System.out.println("\t\t...la tabla TBL_CENTRO ya existe.");
            }
        } catch (SQLException ex) {
            System.err.println(
                        "Error de Base de Datos tratando de verificar si existe y sino crear la tabla TBL_CENTRO.\n"+
                        "Mensaje de error(SQL): " + ex.getMessage()+"\n"
            );
        }
    }
    
    public static boolean existeTablaVotantes(Connection conn){    
        //Verifica si existe la tabla TBL_VOTANTES
        try {
            DatabaseMetaData dmd = conn.getMetaData();
            ResultSet rs = dmd.getTables(null, "APP", "TBL_VOTANTES",null);
            if(rs.next()){
                System.out.println("\t\t...la tabla TBL_VOTANTES si existe.");
                return true;
            }
        } catch (SQLException ex) {
            System.err.println(
                        "Error de Base de Datos tratando de verificar si existe la tabla TBL_VOTANTES.\n"+
                        "Mensaje de error(SQL): " + ex.getMessage()+"\n"
            );
        }
        return false;
    }
    
    public static boolean existeTablaCentro(Connection conn){    
        //Verifica si existe la tabla TBL_CENTRO
        try {
            DatabaseMetaData dmd = conn.getMetaData();
            ResultSet rs = dmd.getTables(null, "APP", "TBL_CENTRO",null);
            if(rs.next()){
                System.out.println("\t\t...la tabla TBL_CENTRO si existe.");
                return true;
            }
        } catch (SQLException ex) {
            System.err.println(
                        "Error de Base de Datos tratando de verificar si existe la tabla TBL_CENTRO.\n"+
                        "Mensaje de error(SQL): " + ex.getMessage()+"\n"
            );
        }
        return false;
    }
    
    public static boolean existeTablaCandidatos(Connection conn){    
        //Verifica si existe la tabla TBL_CANDIDATOS
        try {
            DatabaseMetaData dmd = conn.getMetaData();
            ResultSet rs = dmd.getTables(null, "APP", "TBL_CANDIDATOS",null);
            if(rs.next()){
                System.out.println("\t\t...la tabla TBL_CANDIDATOS si existe.");
                return true;
            }
        } catch (SQLException ex) {
            System.err.println(
                        "Error de Base de Datos tratando de verificar si existe la tabla TBL_CANDIDATOS.\n"+
                        "Mensaje de error(SQL): " + ex.getMessage()+"\n"
            );
        }
        return false;
    }
    
    private boolean existeTabla(Connection conn, String tabla){
        try {
            DatabaseMetaData dmd = conn.getMetaData();
            ResultSet rs = dmd.getTables(null,"IT5N", tabla, null);
            if(rs.next()){
                System.out.println("\t\t...la tabla " + tabla + " ya existe.");
                return true;                
            } else{
                System.out.println("\t\t...la tabla " + tabla + " no existe.");
                return false;
            }
        } catch (SQLException ex) {
            System.err.println(
                        "Error de Base de Datos tratando de verificar si existe la tabla " + tabla + ".\n"+
                        "Mensaje de error(SQL): " + ex.getMessage()+"\n"
            );
            return false;
        }
    }
    
}
