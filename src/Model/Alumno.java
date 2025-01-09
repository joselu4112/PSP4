package Model;

import java.sql.Blob;
import java.sql.Date;
import java.util.GregorianCalendar;

public class Alumno {
    private int numero;
    private String usuario;
    private String contrasena;
    private Date fechaNacimiento;
    private float notaMedia;
    private Blob imagen;
    //Todos los strings tienen 20 de longitud
    private final int longitudString=20;
    
    public Alumno() {
    	
    }
    
    
    
	public Alumno(int numero, String usuario, String contrasena, Date fechaNacimiento, float notaMedia, Blob imagen) {
		super();
		setNumero(numero);//No se puede cambiar 
		setUsuario(usuario);//Strings ajustados
		setContrasena(contrasena);//String ajustados
		setFechaNacimiento(fechaNacimiento);//Exception por si no cumple la estructura que ahora mismo ns cual es xd
		setNotaMedia(notaMedia);//Se cambia automaticamente asi que no necesita excepciones
		setImagen(imagen); //La imagen se guarda como un Blob y se gestiona para poder mostrarla y guardarla con la clase ControladorImagen
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = this.ajustarLongitudString(usuario, longitudString);
	}
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = this.ajustarLongitudString(contrasena, longitudString);
	}
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public float getNotaMedia() {
		return notaMedia;
	}
	public void setNotaMedia(float notaMedia) {
		this.notaMedia = notaMedia;
	}
	public Blob getImagen() {
		return imagen;
	}
	public void setImagen(Blob imagen) {
		this.imagen = imagen;
	}
    
    // Método para controlar la longitud de un string
    public static String ajustarLongitudString(String texto, int longitudMaxima) {
        // Verifica si el texto es más largo que la longitud máxima
        if (texto.length() > longitudMaxima) {
            // Si es más largo, corta el texto a la longitud máxima
            return texto.substring(0, longitudMaxima);
        } else {
            // Si no es más largo, devuelve el texto tal cual
            return texto;
        }
    }
}
