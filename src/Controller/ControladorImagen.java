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
	
		// Método para convertir una imagen a un BLOB y devolverlo
		//A este metodo se le pasa la ruta de la imagen elegida por el usuario y la conexion con la BBDD
		//Con eso transforma la imagen seleccionada en un blob para guardarlo
		//Seria: Alumno alumno=new alumno(...,ControladorImagen.convertirImagenABlob(ruta,conexion));
	
	    public static Blob convertirImagenABlob(String rutaImagen, Connection conexion) throws SQLException, IOException {
	        // Crear un objeto File con la ruta de la imagen
	        File archivoImagen = new File(rutaImagen);
	        
	        // Crear un FileInputStream para leer el archivo de la imagen
	        FileInputStream fis = new FileInputStream(archivoImagen);
	        
	        // Crear un Blob en la base de datos, usando la conexión a la base de datos
	        Blob imagenBlob = conexion.createBlob();
	        
	        // Escribir el contenido del FileInputStream en el Blob
	        try (OutputStream out = imagenBlob.setBinaryStream(1)) {
	            byte[] buffer = new byte[1024];
	            int bytesLeidos;
	            while ((bytesLeidos = fis.read(buffer)) != -1) {
	                out.write(buffer, 0, bytesLeidos);
	            }
	        }
	        
	        fis.close();  // Cerrar el FileInputStream
	        
	        return imagenBlob;  // Devolver el Blob que contiene los datos de la imagen
	    }
	    
	    // Método para convertir un BLOB a un BufferedImage para mostrarlo
	    //Para mostrar la imagen solo hay que usar este metodo antes de pasarsela a donde sea que se muestre:
	    //JLabel label = new JLabel(new ImageIcon(ControladorImagen.convertirBlobAImagen(alumno.imagen)));
	    public static BufferedImage convertirBlobAImagen(Blob blob) throws SQLException, IOException {
	        // Obtener el InputStream del BLOB
	        InputStream is = blob.getBinaryStream();

	        // Convertir el InputStream a un BufferedImage
	        BufferedImage bufferedImage = ImageIO.read(is);
	        is.close();  // Cerrar el InputStream

	        return bufferedImage;  // Devolver la imagen
	    }

  
    }


