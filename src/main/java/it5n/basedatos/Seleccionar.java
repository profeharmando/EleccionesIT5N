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

import it5n.modelos.Candidato;
import it5n.modelos.VerResultados;
import it5n.modelos.Votar;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.collections.ObservableList;

/**
 *
 * @author Héctor Armando Herrera (profeharmando@gmail.com)
 */
public class Seleccionar {
    
    public static void obtenerResultados(Connection conn, ObservableList<VerResultados> lista){
	String sentenciaSQL = "SELECT nombre, votos "
                            + "FROM TBL_CANDIDATOS";
        try {
            Statement statement = conn.createStatement();
            ResultSet resultado = statement.executeQuery(sentenciaSQL);
            while (resultado.next()){
                lista.add( new VerResultados(
                                    resultado.getString("nombre"),
                                    resultado.getString(String.valueOf("votos"))
                                    )
                );
            }
            resultado.close();
        } catch (SQLException ex) {
            System.err.println(
                        "\nError al tratar de obtener los datos de los candidatos para ver resultados.\n" +
                        "Sentencia SQL: \"" + sentenciaSQL + "\"\n" +
                        "Mensaje de error(SQL): " + ex.getMessage() + "\n"
            );
        }
    }
    
    public static int obtenerVotosCandidato(Connection conn, int codigo){
        String sentenciaSQL = "SELECT VOTOS FROM TBL_CANDIDATOS " + 
                              "WHERE CODIGO = " + codigo;
        int votos = 0;
        try{
            Statement statement = conn.createStatement();
            ResultSet resultado = statement.executeQuery(sentenciaSQL);
            while(resultado.next()){
                votos = resultado.getInt("VOTOS");                
            }
            resultado.close();
        }catch(SQLException ex){
            System.err.println(
                        "Error de Base de Datos tratando de obtener los votos del candidato con el codigo: " + codigo + "\n"+
                        "Sentencia SQL: \"" + sentenciaSQL + "\"\n" +
                        "Mensaje de error(SQL): " + ex.getMessage() + "\n"
            );
        }
        return votos;
    }
    
    public static void obtenerCandidatos(Connection conn, ArrayList<Candidato> lista){
        String sentenciaSQL = "SELECT CODIGO, NOMBRE FROM TBL_CANDIDATOS";
        try{
            Statement statement = conn.createStatement();
            ResultSet resultado = statement.executeQuery(sentenciaSQL);
            while(resultado.next()){
                lista.add(new Candidato(resultado.getInt("CODIGO"), resultado.getString("NOMBRE")));
            }
            resultado.close();
        }catch(SQLException ex){
            System.err.println(
                        "Error de Base de Datos tratando de obtener la lista de los candidatos.\n" +
                        "Sentencia SQL: \"" + sentenciaSQL + "\"\n" +
                        "Mensaje de error(SQL): " + ex.getMessage() + "\n"
            );
        }
    }
    
    public static int optenerVotosTotales(Connection conn){
        String sentenciaSQL = "SELECT SUM(VOTOS) AS SUMA FROM TBL_CANDIDATOS";
        int votos_totales = 0;
        try{
            Statement statement = conn.createStatement();
            ResultSet resultado = statement.executeQuery(sentenciaSQL);
            while(resultado.next()){
                votos_totales = resultado.getInt("SUMA");
            }
            resultado.close();
            return votos_totales;
        }catch(SQLException ex){
            System.err.println(
                        "Error de Base de Datos tratando de obtener el Total de Votos"+
                        "Sentencia SQL: \"" + sentenciaSQL + "\"\n" +
                        "Mensaje de error(SQL): " + ex.getMessage() + "\n"
            );
        }
        return votos_totales;
    }
    
    public static int obtenerNumeroCandidatos(Connection conn){
        String sentenciaSQL = "SELECT COUNT(NOMBRE) AS CANTIDAD FROM TBL_CANDIDATOS";
        int cantidad = 0;
        try{
            Statement statement = conn.createStatement();
            ResultSet resultado = statement.executeQuery(sentenciaSQL);
            while(resultado.next()){
                cantidad = resultado.getInt("CANTIDAD");
            }
            resultado.close();
            return cantidad;
        }catch(SQLException ex){
            System.err.println(
                        "Error de Base de Datos tratando de obtener la cantidad de candidatos"+
                        "Sentencia SQL: \"" + sentenciaSQL + "\"\n" +
                        "Mensaje de error(SQL): " + ex.getMessage() + "\n"
            );
        }
        return cantidad;
    }
    
    public static String obtenerVotante(Connection conn, String rne){
        String sentenciaSQL = "SELECT NOMBRE FROM TBL_VOTANTES " + 
                              "WHERE RNE = " + rne;
        String votante = "";
        try{
            Statement statement = conn.createStatement();
            ResultSet resultado = statement.executeQuery(sentenciaSQL);
            while(resultado.next()){
                votante = resultado.getString("NOMBRE");
            }
            resultado.close();
        }catch(SQLException ex){
            System.err.println(
                        "Error de Base de Datos tratando de obtener el Nombre para el Rne: " + rne + ".\n"+
                        "Sentencia SQL: \""+sentenciaSQL+"\"\n"+
                        "Mensaje de error(SQL): "+ex.getMessage()+"\n"
            );
            votante = null;
        }
        return votante;
    }
    
    public static boolean hayVotantes(Connection conn){
        String sentenciaSQL = "SELECT NOMBRE FROM TBL_VOTANTES";
        boolean hay = false;
        try{
            Statement statement = conn.createStatement();
            ResultSet resultado = statement.executeQuery(sentenciaSQL);
            if(resultado.next()){
                hay = true;
            }
            resultado.close();
        }catch(SQLException ex){
            System.err.println(
                        "Error de Base de Datos tratando de saber si hay o no Votantes en el sistema.\n"+
                        "Sentencia SQL: \"" + sentenciaSQL + "\"\n" +
                        "Mensaje de error(SQL): " + ex.getMessage() + "\n"
            );
        }
        return hay;
    }
    
    public static String obtenerNombreCandidato(Connection conn, int numero){
        String sentenciaSQL = "SELECT NOMBRE FROM TBL_CANDIDATOS " + 
                              "WHERE NUMERO = " + numero;
        String candidato = null;
        try{
            Statement statement = conn.createStatement();
            ResultSet resultado = statement.executeQuery(sentenciaSQL);
            while(resultado.next()){
                candidato = resultado.getString("NOMBRE");
            }
            resultado.close();
        }catch(SQLException ex){
            System.err.println(
                        "Error de Base de Datos tratando de obtener el Nombre para el candidato numero: " + numero + ".\n"+
                        "Sentencia SQL: \"" + sentenciaSQL + "\"\n" +
                        "Mensaje de error(SQL): " + ex.getMessage() + "\n"
            );
        }
        return candidato;
    }
    
    public static boolean estaHabilitadoCandidato(Connection conn, int numero){
        String sentenciaSQL = "SELECT HABILITADO FROM TBL_CANDIDATOS " + 
                              "WHERE NUMERO = " + numero;
        boolean habilitado = false;
        try{
            Statement statement = conn.createStatement();
            ResultSet resultado = statement.executeQuery(sentenciaSQL);
            while(resultado.next()){
                if(resultado.getString("HABILITADO").equals("SI")){
                    habilitado = true;
                }
                resultado.close();
            }
        }catch(SQLException ex){
            System.err.println(
                        "Error de Base de Datos tratando de conocer si el candidato No" + numero + " está habilitado.\n"+
                        "Sentencia SQL: \"" + sentenciaSQL + "\"\n" +
                        "Mensaje de error(SQL): " + ex.getMessage() + "\n"
            );
        }
        return habilitado;
    }
    
    public static boolean hayCandidatos(Connection conn){
        String sentenciaSQL = "SELECT NOMBRE FROM TBL_CANDIDATOS";
        boolean hay = false;
        try{
            Statement statement = conn.createStatement();
            ResultSet resultado = statement.executeQuery(sentenciaSQL);
            if(resultado.next()){
                hay = true;
            }
            resultado.close();
        }catch(SQLException ex){
            System.err.println(
                        "Error de Base de Datos tratando de saber si hay o no Candidatos en el sistema.\n"+
                        "Sentencia SQL: \"" + sentenciaSQL + "\"\n" +
                        "Mensaje de error(SQL): " + ex.getMessage() + "\n"
            );
        }
        return hay;
    }
    
    public static boolean hayResultados(Connection conn){
        String sentenciaSQL = "SELECT SUM(VOTOS) AS SUMA FROM TBL_CANDIDATOS";
        int votos = 0;
        boolean hay = false;
        try{
            Statement statement = conn.createStatement();
            ResultSet resultado = statement.executeQuery(sentenciaSQL);
            while(resultado.next()){
                votos = resultado.getInt("SUMA");
            }
            resultado.close();
            if(votos > 0){
                hay = true;
            }
        }catch(SQLException ex){
            System.err.println(
                        "Error de Base de Datos tratando de saber si hay resultados"+
                        "Sentencia SQL: \"" + sentenciaSQL + "\"\n" +
                        "Mensaje de error(SQL): " + ex.getMessage() + "\n"
            );
        }
        return hay;
    }
    
    public static boolean candidatoYaExiste(Connection conn, String nombre){
        boolean existe = false;
        String sentenciaSQL = "SELECT NOMBRE FROM TBL_CANDIDATOS " + 
                              "WHERE NOMBRE = '" + nombre + "'";
        try{
            Statement statement = conn.createStatement();
            ResultSet resultado = statement.executeQuery(sentenciaSQL);
            while(resultado.next()){
                existe = true;
            }
            resultado.close();
        }catch(SQLException ex){
            System.err.println(
                        "Error de Base de Datos tratando de saber si el Candidato ya existe en la BD.\n"+
                        "Sentencia SQL: \"" + sentenciaSQL + "\"\n" +
                        "Mensaje de error(SQL): " + ex.getMessage() + "\n"
            );
        }
        return existe;
    }
    
    public static boolean hayVotoEnBlanco(Connection conn){
        boolean existe = false;
        String sentenciaSQL = "SELECT NOMBRE FROM TBL_CANDIDATOS " + 
                              "WHERE NOMBRE = 'VOTO EN BLANCO'";
        try{
            Statement statement = conn.createStatement();
            ResultSet resultado = statement.executeQuery(sentenciaSQL);
            while(resultado.next()){
                existe = true;
            }
            resultado.close();
        }catch(SQLException ex){
            System.err.println(
                        "Error de Base de Datos tratando de saber si hay opción de Voto en Blanco en la BD.\n"+
                        "Sentencia SQL: \"" + sentenciaSQL + "\"\n" +
                        "Mensaje de error(SQL): " + ex.getMessage() + "\n"
            );
        }
        return existe;
    }
    
    public static boolean registroExiste(Connection conn, String registro){
        boolean existe = false;
        String sentenciaSQL = "SELECT NOMBRE FROM TBL_VOTANTES " + 
                              "WHERE RNE = '" + registro + "'";
        try{
            Statement statement = conn.createStatement();
            ResultSet resultado = statement.executeQuery(sentenciaSQL);
            while(resultado.next()){
                existe = true;
            }
            resultado.close();
        }catch(SQLException ex){
            System.err.println(
                        "Error de Base de Datos tratando de saber si el Candidato ya existe en la BD.\n"+
                        "Sentencia SQL: \"" + sentenciaSQL + "\"\n" +
                        "Mensaje de error(SQL): " + ex.getMessage() + "\n"
            );
        }
        return existe;
    }
    
    public static boolean imagenYaExiste(Connection conn, String imagen){
        boolean existe = false;
        String sentenciaSQL = "SELECT IMAGEN FROM TBL_CANDIDATOS " + 
                              "WHERE IMAGEN = '" + imagen + "'";
        try{
            Statement statement = conn.createStatement();
            ResultSet resultado = statement.executeQuery(sentenciaSQL);
            while(resultado.next()){
                existe = true;
            }
            resultado.close();
        }catch(SQLException ex){
            System.err.println(
                        "Error de Base de Datos tratando de saber si la imagen del candidato ya existe en la BD.\n"+
                        "Sentencia SQL: \"" + sentenciaSQL + "\"\n" +
                        "Mensaje de error(SQL): " + ex.getMessage() + "\n"
            );
        }
        return existe;
    }
    
    public static String obtenerImagenCandidato(Connection conn, int numero){
        String sentenciaSQL = "SELECT IMAGEN FROM TBL_CANDIDATOS " + 
                              "WHERE NUMERO = " + numero;
        String imagen = null;
        try{
            Statement statement = conn.createStatement();
            ResultSet resultado = statement.executeQuery(sentenciaSQL);
            while(resultado.next()){
                imagen = resultado.getString("IMAGEN");
            }
            resultado.close();
        }catch(SQLException ex){
            System.err.println(
                        "Error de Base de Datos tratando de obtener la Imagen del candidato numero: " + numero + ".\n"+
                        "Sentencia SQL: \"" + sentenciaSQL + "\"\n" +
                        "Mensaje de error(SQL): " + ex.getMessage() + "\n"
            );
        }
        return imagen;
    }
    
    public static int obtenerVotosEnBlanco(Connection conn){
        String sentenciaSQL = "SELECT VOTOS FROM TBL_CANDIDATOS " + 
                              "WHERE NOMBRE = 'EN BLANCO'";
        int votos = 0;
        try{
            Statement statement = conn.createStatement();
            ResultSet resultado = statement.executeQuery(sentenciaSQL);
            while(resultado.next()){
                votos = resultado.getInt("VOTOS");
            }
            resultado.close();
        }catch(SQLException ex){
            System.err.println(
                        "Error de Base de Datos tratando de obtener los votos EN BLANCO.\n"+
                        "Sentencia SQL: \"" + sentenciaSQL + "\"\n" +
                        "Mensaje de error(SQL): " + ex.getMessage() + "\n"
            );
        }
        return votos;
    }
    
    public static String obtenerNombreCentro(Connection conn){
        String sentenciaSQL = "SELECT NOMBRE FROM TBL_CENTRO";
        String nombre = null;
        try{
            Statement statement = conn.createStatement();
            ResultSet resultado = statement.executeQuery(sentenciaSQL);
            while(resultado.next()){
                nombre = resultado.getString("NOMBRE");
            }
            resultado.close();
        }catch(SQLException ex){
            System.err.println(
                        "Error de Base de Datos tratando de obtener el Nombre del Centro.\n"+
                        "Sentencia SQL: \""+sentenciaSQL+"\"\n"+
                        "Mensaje de error(SQL): "+ex.getMessage()+"\n"
            );
        }
        return nombre;
    }
    
    public static String obtenerImagenCentro(Connection conn){
        String sentenciaSQL = "SELECT IMAGEN FROM TBL_CENTRO";
        String imagen = null;
        try{
            Statement statement = conn.createStatement();
            ResultSet resultado = statement.executeQuery(sentenciaSQL);
            while(resultado.next()){
                imagen = resultado.getString("IMAGEN");
            }
            resultado.close();
        }catch(SQLException ex){
            System.err.println(
                        "Error de Base de Datos tratando de obtener la Imagen del Centro.\n"+
                        "Sentencia SQL: \""+sentenciaSQL+"\"\n"+
                        "Mensaje de error(SQL): "+ex.getMessage()+"\n"
            );
        }
        return imagen;
    }
    
    public static boolean hayCentro(Connection conn){
        String sentenciaSQL = "SELECT NOMBRE FROM TBL_CENTRO";
        boolean hay = false;
        try{
            Statement statement = conn.createStatement();
            ResultSet resultado = statement.executeQuery(sentenciaSQL);
            if(resultado.next()){
                hay = true;
            }
            resultado.close();
        }catch(SQLException ex){
            System.err.println(
                        "Error de Base de Datos tratando de saber si hay Centro en el sistema.\n"+
                        "Sentencia SQL: \"" + sentenciaSQL + "\"\n" +
                        "Mensaje de error(SQL): " + ex.getMessage() + "\n"
            );
        }
        return hay;
    }
    
    public static String obtenerRne(Connection conn, String rne){
        String sentenciaSQL = "SELECT RNE FROM TBL_VOTANTES " + 
                              "WHERE RNE = '" + rne + "'";
        String registro = null;
        try{
            Statement statement = conn.createStatement();
            ResultSet resultado = statement.executeQuery(sentenciaSQL);
            while(resultado.next()){
                registro = resultado.getString("RNE");                
            }
            resultado.close();
        }catch(SQLException ex){
            System.err.println(
                        "Error de Base de Datos tratando de obtener el RNE para el Rne: " + rne + ".\n"+
                        "Sentencia SQL: \"" + sentenciaSQL + "\"\n" +
                        "Mensaje de error(SQL): " + ex.getMessage() + "\n"
            );
        }
        return registro;
    }
    
    public static String obtenerNombreAlumno(Connection conn, String rne){
        String sentenciaSQL = "SELECT NOMBRE FROM TBL_VOTANTES " + 
                              "WHERE RNE = '" + rne + "'";
        String votante = null;
        try{
            Statement statement = conn.createStatement();
            ResultSet resultado = statement.executeQuery(sentenciaSQL);
            while(resultado.next()){
                votante = resultado.getString("NOMBRE");                
            }
            resultado.close();
        }catch(SQLException ex){
            System.err.println(
                        "Error de Base de Datos tratando de obtener el Nombre para el Rne: " + rne + ".\n"+
                        "Sentencia SQL: \"" + sentenciaSQL + "\"\n" +
                        "Mensaje de error(SQL): " + ex.getMessage() + "\n"
            );
            votante = null;
        }
        return votante;
    }
    
    public static int obtenerCodigoVotante(Connection conn, String nombre, String rne){
        String sentenciaSQL = "SELECT CODIGO FROM TBL_VOTANTES "
                            + "WHERE NOMBRE = '" + nombre + "' "
                            + "AND RNE = '" + rne + "'";
        int codigo = 0;
        try{
            Statement statement = conn.createStatement();
            ResultSet resultado = statement.executeQuery(sentenciaSQL);
            while(resultado.next()){
                codigo = resultado.getInt("CODIGO");
            }
            resultado.close();
        }catch(SQLException ex){
            System.err.println(
                        "Error de Base de Datos tratando de obtener el codigo para el votante " + nombre + " con rne: " + rne + ".\n"+
                        "Sentencia SQL: \"" + sentenciaSQL + "\"\n" +
                        "Mensaje de error(SQL): " + ex.getMessage() + "\n"
            );
        }
        return codigo;
    }
    
    public static boolean alumnoLibre(Connection conn, String rne){
        String sentenciaSQL = "SELECT VOTO FROM TBL_VOTANTES " + 
                              "WHERE RNE = '" + rne + "'";
        boolean libre = false;
        try{
            Statement statement = conn.createStatement();
            ResultSet resultado = statement.executeQuery(sentenciaSQL);
            while(resultado.next()){
                if(resultado.getString("VOTO").equals("NO")){
                    libre = true;
                }
            }
            resultado.close();
        }catch(SQLException ex){
            System.err.println(
                        "Error de Base de Datos tratando de saber si el Alumno con RNE: " + rne + " está Libre"+
                        "Sentencia SQL: \"" + sentenciaSQL + "\"\n" +
                        "Mensaje de error(SQL): " + ex.getMessage() + "\n"
            );
        }
        return libre;
    }
    
    // ESTE METODO ME SIRVIO PARA SI LAS TABLAS DE LA BASE DE DATOS ESTABAN DESBLOQUEADAS (lo use en debuggin)
    // Muestra el estado de las tablas, aprendi a que debo cerrar los ResulSet siempre
    public static void encontrarBloqueo(Connection conn){
	String sentenciaSQL = "SELECT * FROM SYSCS_DIAG.LOCK_TABLE";
        try {
            Statement statement = conn.createStatement();
            ResultSet resultado = statement.executeQuery(sentenciaSQL);
            while (resultado.next()){
                System.out.println("\t\t\t" + resultado.getString(1) + "\t" + resultado.getString(2)+ "\t" + resultado.getString(3) + "\t" + resultado.getString(4) + "\t" + resultado.getString(5) + "\t" + resultado.getString(6) + "\t" + resultado.getString(7) + "\t" + resultado.getString(8));
            }
            resultado.close();
        } catch (SQLException ex) {
            System.err.println(
                        "\nError al tratar de obtener el estado de bloqueo de las tablas.\n" +
                        "Sentencia SQL: \"" + sentenciaSQL + "\"\n" +
                        "Mensaje de error(SQL): " + ex.getMessage() + "\n"
            );
        }
    }
    
}
