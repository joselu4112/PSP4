package Model;

public class FechaInvalidaException extends Exception{

	private final static String txt="La fecha debe tener formato XD-XD-XD";
	public FechaInvalidaException() {
		super(txt);
	}
	public FechaInvalidaException(String str) {
		super(str);
	}
	
}
