// NodoCola.java

public class NodoCola{

	private int numero;
	NodoCola sig;

	public NodoCola( int valor ){
		numero = valor;
		sig = null;
	}

	public int obtenerNumero(){
		return numero;
	}

}// fin de la clase NodoCola