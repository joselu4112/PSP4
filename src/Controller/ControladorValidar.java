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
		            System.out.println(this.numUsuario);
	                return true;
	            } else {
	                // Si no hay resultados, es que no hay coincidencias
	                return false;
	            }
	        } catch (SQLException e) {
	            // Manejo de errores si ocurre alguna excepción al ejecutar la consulta
	            e.printStackTrace();
	            return false;
	        }
	    }
	    public int getNumUsuario() {
	    	System.out.println(this.numUsuario);
	    		return this.numUsuario;
	    }
	    	
	    
	    /*EJEMPLO DE USO
	public class Main {
    public static void main(String[] args) {
        // Establecer la conexión con la base de datos
        try (Connection conexion = ConexionDB.obtenerConexion() {
            // Crear el controlador
            ControladorValidar controlador = new ControladorValidar(conexion);

            // Validar el usuario y la contraseña
            String usuario = "usuarioEjemplo";
            String contrasena = "contrasenaEjemplo";

            if (controlador.validarUsuario(usuario, contrasena)) {
                System.out.println("¡Usuario y contraseña válidos!");
            } else {
                System.out.println("Usuario o contraseña incorrectos.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/
}
