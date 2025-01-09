package Controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.Alumno;
import Model.Asignatura;
import Model.NotaInvalidaException;

//11.3
public class ControladorResumen {
	 private Statement stmt;

	    // Constructor
	    public ControladorResumen(Statement stmt) {
	        this.stmt = stmt;
	    }
	    
	    // Método para obtener los datos de un alumno
	    public Alumno obtenerAlumno(int numero) throws SQLException {
	        String query = "SELECT * FROM alumno WHERE numero = " + numero;
	        ResultSet rs = stmt.executeQuery(query);
	        
	        if (rs.next()) {
	            return new Alumno(
	                rs.getInt("numero"),
	                rs.getString("usuario"),
	                rs.getString("contrasena"),
	                rs.getDate("fecha_nacimiento"),
	                rs.getFloat("nota_media"),
	                rs.getBlob("imagen")
	            );
	        } else {
	            return null; // No se encontró el alumno
	        }
	    }

	    // Método para actualizar los datos de un alumno
	    //Aqui yo he hecho que no puedan actualizar el codigo pk debe ser unico
	    //Pasar el alumno completo con la iamgen 
	    public void actualizarAlumno(Alumno alumno) throws SQLException {
	        String query = "UPDATE alumno SET usuario = ?, contrasena = ?, fecha_nacimiento = ?, nota_media = ?, imagen=? WHERE numero = ?";
	        try (PreparedStatement ps = stmt.getConnection().prepareStatement(query)) {
	            ps.setString(1, alumno.getUsuario());
	            ps.setString(2, alumno.getContrasena());
	            ps.setDate(3, new java.sql.Date(alumno.getFechaNacimiento().getTime()));
	            ps.setFloat(4, alumno.getNotaMedia());
	            ps.setInt(5, alumno.getNumero());
	            ps.setBlob(6, alumno.getImagen());
	            ps.executeUpdate();
	            
	            
	        }
	    }
	    
	    // Método para obtener todas las asignaturas de un alumno
	    public List<Asignatura> obtenerAsignaturasDeAlumno(int aluNumero) throws SQLException {
	        List<Asignatura> asignaturas = new ArrayList<>();
	        String query = "SELECT * FROM asignatura WHERE aluNumero = " + aluNumero;
	        ResultSet rs = stmt.executeQuery(query);
	        
	        while (rs.next()) {
	            try {
					asignaturas.add(new Asignatura(
					    rs.getInt("codigo"),
					    rs.getString("nombre"),
					    rs.getFloat("nota"),
					    rs.getInt("aluNumero")
					));
				} catch (NotaInvalidaException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        return asignaturas;
	    }

	    // Método para calcular la nota media de un alumno
	    public float calcularNotaMedia(int aluNumero) throws SQLException {
	        List<Asignatura> asignaturas = obtenerAsignaturasDeAlumno(aluNumero);
	        if (asignaturas.isEmpty()) return 0;

	        float sumaNotas = 0;
	        for (Asignatura asignatura : asignaturas) {
	            sumaNotas += asignatura.getNota();
	        }
	        return sumaNotas / asignaturas.size();
	    }
}
