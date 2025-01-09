package Controller;

import java.sql.*;

//11.1
public class ConexionDB {

	private static final String URL = "jdbc:mysql://localhost:3306/alumnosDB";
    private static final String USER = "root";  // 
    private static final String PASSWORD = "";  // 

    // Método para obtener la conexión
    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Método para cerrar una conexión
    public static void cerrarConexion(Connection conn) throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    // Método para obtener Statement para "detalle" y "resumen"
    public static Statement obtenerStatementDetalle(Connection conn) throws SQLException {
        Statement stmtDetalle = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        return stmtDetalle;
    }
    public static Statement obtenerStatementResumen(Connection conn) throws SQLException {
        Statement stmtResumen = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
        return  stmtResumen;
    }

}
