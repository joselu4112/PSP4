package Controller;

import java.sql.*;

public class ControladorValidar {
	 private Connection conexion;
	 private static int numUsuario;
	 
	    // Constructor que recibe una conexión a la base de datos
	    public ControladorValidar(Connection conexion) {
	        this.conexion = conexion;
	    }

	    // Método para validar si el usuario y la contraseña coinciden
	    public boolean validarUsuario(String usuario, String contrasena) throws SQLException {
	        // Consulta SQL para verificar si existe el usuario con la contraseña indicada
	        String query = "SELECT * FROM alumno WHERE usuario = ? AND contrasena = ?";

	        // Se prepara la consulta para evitar inyecciones SQL
	        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
	            stmt.setString(1, usuario);
	            stmt.setString(2, contrasena);

	            // Ejecutar la consulta y obtener el resultado
	            ResultSet rs = stmt.executeQuery();

	            // Si hay resultados, significa que la contraseña y el usuario son correctos
	            if (rs.next()) {
		            this.numUsuario=rs.getInt("numero");
	                return true;
	            } else {
	                // Si no hay resultados, es que no hay coincidencias
	                return false;
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	    public int getNumUsuario() {
	    		return this.numUsuario;
	    }
	    	
	    
	    
}
