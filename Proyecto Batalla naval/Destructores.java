
public class Destructores{

	private int longitud = 2;
	private final char identificacion = 'D';
	private int contador;

	private int vida = 2;

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
		vida = 2;
	}

	public void decrementarVida(){
		vida--;
	}
}