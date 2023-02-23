
public class ColaPrioritaria{

	private NodoCola primero;
	private NodoCola ultimo;
	private NodoCola medio;
	private int cantidad;

	public ColaPrioritaria(){
		primero = null;
		ultimo = null;
		medio = null;
		cantidad = 0;
	}

	public void insertar( int dato ){

		NodoCola nuevo = new NodoCola( dato );

		if( estaVacia() ){
			primero = nuevo;
			ultimo = nuevo;
			medio = nuevo;
			cantidad++;
		}else{

			if( dato < primero.obtenerNumero() ){
				nuevo.sig = primero;
				primero = nuevo;
			}else{

				NodoCola actual = primero;
				NodoCola anterior = null;

				while( actual != null && dato > actual.obtenerNumero() ){
					anterior = actual;
					actual = actual.sig;
				}// fin while

				if( actual == null ){
					ultimo.sig = nuevo;
					ultimo = nuevo;
				}else{
					anterior.sig = nuevo;
					nuevo.sig = actual;
				}

			}// fin else

			// incrementa en uno la cantidad de elementos
			cantidad++;

			if( obtenerLongitud() % 2 == 0 )
				establecerReferenciaMedio( ( obtenerLongitud() / 2 ) - 1 );
			else
				establecerReferenciaMedio( obtenerLongitud() / 2 );

		}// fin else

	}// fin del metodo insertar

	public ColaPrioritaria parteIzquierda(){

		ColaPrioritaria lista = new ColaPrioritaria();

		if( estaVacia() ){
			return lista;
		}else{

			if( obtenerLongitud() > 0 && obtenerLongitud() <= 2 )
				return lista;
			else{

				NodoCola actual = primero;

				while( actual != medio ){
					lista.insertar( actual.obtenerNumero() );
					actual = actual.sig;
				}

				return lista;
			}

		}// fin else
	}// fin metodo parteIzquierda

	public ColaPrioritaria parteDerecha(){

		ColaPrioritaria lista = new ColaPrioritaria();

		if( estaVacia() )
			return lista;
		else{

			if( obtenerLongitud() == 1 )
				return lista;
			else{

				NodoCola actual = medio.sig;

				while( actual != null ){
					lista.insertar( actual.obtenerNumero() );
					actual = actual.sig;
				}

				return lista;
			}
		}
	}// fin de metodo parteDerecha

	private void establecerReferenciaMedio( int mitad ){

		NodoCola temp = primero;
		int contador = 0;

		while( contador < mitad ){
			temp = temp.sig;
			contador++;
		}

		medio = temp;
	}

	public boolean estaVacia(){
		return primero == null;
	}

	public int obtenerLongitud(){
		return cantidad;
	}

	public int sacarMedio(){

		int temp = 0;

		if( obtenerLongitud() > 0 && obtenerLongitud() < 3 ){

			if( obtenerLongitud() == 1 ){
				temp = medio.obtenerNumero();
				vaciarCola();
				return temp;
			}else{
				temp = medio.obtenerNumero();
				medio = null;
				primero = primero.sig;
				return temp;
			}

		}else{

			NodoCola actual = primero;
			NodoCola anterior = null;

			while( actual != medio ){
				anterior = actual;
				actual = actual.sig;
			}

			temp = medio.obtenerNumero();
			actual = actual.sig;
			anterior = actual;
			medio = null;
			return temp;

		}

	}

	public void vaciarCola(){
		primero = null;
		ultimo = null;
	}

}// fin de la clase ColaPrioritaria