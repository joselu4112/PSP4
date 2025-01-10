package Controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.*;

import javax.imageio.ImageIO;

public class ControladorImagen {

	    
	    public static void actualizarImagenAlumno(int idAlumno, File archivoImagen, Connection conn) throws SQLException, IOException {
	        String sql = "UPDATE alumno SET imagen = ? WHERE numero = ?";
	        try (PreparedStatement stmt = conn.prepareStatement(sql);
	             FileInputStream fis = new FileInputStream(archivoImagen)) {
	            stmt.setBinaryStream(1, fis, (int) archivoImagen.length());
	            stmt.setInt(2, idAlumno);
	            stmt.executeUpdate();
	        }
	    }
	    
	    public static Blob obtenerImagenAlumno(int idAlumno, Connection conn) throws SQLException {
	        String sql = "SELECT imagen FROM alumno WHERE numero = ?";
	        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setInt(1, idAlumno);
	            try (ResultSet rs = stmt.executeQuery()) {
	                if (rs.next()) {
	                    return rs.getBlob("imagen");
	                }
	            }
	        }
	        return null; // Retorna null si no se encuentra la imagen
	    }



  
    }


