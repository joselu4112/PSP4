package Controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.Asignatura;
import Model.NotaInvalidaException;


public class ControladorDetalle {
	private Statement stmt;

    // Constructor
    public ControladorDetalle(Statement stmt) {
        this.stmt = stmt;
    }

    // Método para obtener todas las asignaturas de un alumno
    public List<Asignatura> obtenerAsignaturasDeAlumno(int aluNumero) throws SQLException, NotaInvalidaException {
        List<Asignatura> asignaturas = new ArrayList<>();
        String query = "SELECT * FROM asignatura WHERE aluNumero = " + aluNumero;
        ResultSet rs = stmt.executeQuery(query);
        
        while (rs.next()) {
				asignaturas.add(new Asignatura(
				    rs.getInt("codigo"),
				    rs.getString("nombre"),
				    rs.getFloat("nota"),
				    rs.getInt("aluNumero")
				));
		
        }
        rs.first();
        return asignaturas;
    }

    // Método para avanzar al siguiente registro de asignatura
    public Asignatura siguienteAsignatura(ResultSet rs) throws SQLException {
        if (rs.next()) {
            try {
				return new Asignatura(
				    rs.getInt("codigo"),
				    rs.getString("nombre"),
				    rs.getFloat("nota"),
				    rs.getInt("aluNumero")
				);
			} catch (NotaInvalidaException e) {
				return null;
			} 
        } else {
            return null; // Fin de los registros
        }
    }

    // Método para retroceder al registro anterior de asignatura
    public Asignatura anteriorAsignatura(ResultSet rs) throws SQLException {
        if (rs.previous()) {
            try {
				return new Asignatura(
				    rs.getInt("codigo"),
				    rs.getString("nombre"),
				    rs.getFloat("nota"),
				    rs.getInt("aluNumero")
				);
			} catch (NotaInvalidaException  e) {
				return null;
			}
        } else {
            return null; // Ya está en el primer registro
        }
    }
    
 // Método para ir al primer registro de asignatura
    public Asignatura primerAsignatura(ResultSet rs) throws SQLException {
        if (rs.first()) {
            try {
				return new Asignatura(
				    rs.getInt("codigo"),
				    rs.getString("nombre"),
				    rs.getFloat("nota"),
				    rs.getInt("aluNumero")
				);
			} catch (NotaInvalidaException  e) {
				return null;
			}
        } else {
            return null; // No hay registros
        }
    }

    // Método para ir al último registro de asignatura
    public Asignatura ultimoAsignatura(ResultSet rs) throws SQLException {
        if (rs.last()) {
            try {
				return new Asignatura(
				    rs.getInt("codigo"),
				    rs.getString("nombre"),
				    rs.getFloat("nota"),
				    rs.getInt("aluNumero")
				);
			} catch (NotaInvalidaException e) {
				return null;
			}
        } else {
            return null; // No hay registros
        }
    }

    // Método para actualizar la nota de una asignatura
    //Solo se puede actualizar la nota, busca a la asignatura por codigo pero hay q pasarle un objeto Asignatura
    public void actualizarNota(Asignatura asignatura) throws SQLException {
        String query = "UPDATE asignatura SET nota = ? WHERE codigo = ?";
        try (PreparedStatement ps = stmt.getConnection().prepareStatement(query)) {
            ps.setFloat(1, asignatura.getNota());
            ps.setInt(2, asignatura.getCodigo());
            ps.executeUpdate();
        }
    }
}
