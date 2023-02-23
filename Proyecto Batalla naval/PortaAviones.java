
public class PortaAviones{

	private int longitud = 4;
	private final char identificacion = 'P';
	private int contador;

	private int vida = 4;

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
		vida = 4;
	}

	public void decrementarVida(){
		vida--;
	}

}