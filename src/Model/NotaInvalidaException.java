package Model;

public class NotaInvalidaException extends Exception{

	private final static String txt="La nota debe estar entre 0 y 10.";
	public NotaInvalidaException() {
		super(txt);
	}
	public NotaInvalidaException(String str) {
		super(str);
	}
	
}
