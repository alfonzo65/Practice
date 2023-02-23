// Clase Nodo.java

public class Nodo{

	private int numero;
	private int cantidadDeHijos;
	private int profundidad;
	private int altura;
	Nodo derecho;
	Nodo izquierdo;

	public Nodo( int valor ){
		numero = valor;
		cantidadDeHijos = 1;
		profundidad = 0;
		altura = 0;
		derecho = null;
		izquierdo = null;
	}

	public int obtenerNumero(){
		return numero;
	}

	public void establecerProfundidad( int prof ){
		profundidad = prof;
	}

	public void establecerAltura( int altu ){
		altura = altu;
	}

	public void establecerNodos( int hijos ){
		cantidadDeHijos = hijos;
	}

	public int obtenerNodos(){
		return cantidadDeHijos;
	}

	public int obtenerAltura(){
		return altura;
	}

	public int obtenerProfundidad(){
		return profundidad;
	}

}// fin de la clase Nodo