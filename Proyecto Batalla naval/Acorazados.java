
public class Acorazados{

	private int longitud = 3;
	private final char identificacion = 'A';
	private int contador;

	private int vida = 3;

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
		vida = 3;
	}

	public void decrementarVida(){
		vida--;
	}
}