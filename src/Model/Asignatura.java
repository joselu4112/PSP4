package Model;

public class Asignatura {
	 	private int codigo;
	    private String nombre;
	    private float nota;
	    private int aluNumero;
	    //Todos los strings tienen 20 de longitud
	    private final int longitudString=20;
	    
	    public Asignatura() {
	    	
	    }
	    
	    
		public Asignatura(int codigo, String nombre, float nota, int aluNumero) throws NotaInvalidaException {
			super();
			setCodigo(codigo);//No se puede cambiar asi q no tiene q tener exception
			setNombre(nombre);//Controlo la longitud con un metodo
			setNota(nota);//Excepcion pk debe estar entre 0 y 10
			setAluNumero(aluNumero);//No se puede cambiar asi q no necesita exception
		}
		public int getCodigo() {
			return codigo;
		}
		public void setCodigo(int codigo) {
			this.codigo = codigo;
		}
		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = this.ajustarLongitudString(nombre, longitudString);
		}
		public float getNota() {
			return nota;
		}
		public void setNota(float nota) throws NotaInvalidaException {
			if(0<nota && nota<10) {
				this.nota = nota;
			}else {
				throw new NotaInvalidaException();
			}
			
		}
		public int getAluNumero() {
			return aluNumero;
		}
		public void setAluNumero(int aluNumero) {
			this.aluNumero = aluNumero;
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
