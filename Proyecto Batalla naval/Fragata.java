
public class Fragata{

	private int longitud = 1;
	private final char identificacion = 'F';
	private int contador;

	private int vida = 1;

	public void incrementarContador(){
		contador++;
	}

	public void decrementarContador(){
		contador--;
	}

	public int obtenerContador(){
		return contador;
	}

	public int obtenerLongitud(){
		return longitud;
	}

	public char obtenerIdentificacion(){
		return identificacion;
	}

	public int obtenerVida(){
		return vida;
	}

	public void establecerVida(){
		vida = 1;
	}

	public void decrementarVida(){
		vida--;
	}

}