// arbol binario de busqueda peresozo
// ArbolPerezoso.java

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.IllegalStateException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ArbolPerezoso{

	private Scanner entrada = new Scanner(System.in);
	private Nodo raiz;
	private int raiz_Antes , raiz_Despues;

	public ArbolPerezoso( String archivo ){

		raiz = null;
		raiz_Antes = 0;
		raiz_Despues = 0;

		try{
			
			entrada = new Scanner( new File(archivo) );

		}catch( FileNotFoundException fileNotFoundException ){
			System.out.println("Error al Abrir el archivo!");
			System.exit(1);
		}// fin bloque try

		// 1. fase de inicializacion
		try{

			while( entrada.hasNext() ){

				int numero = entrada.nextInt();

				// inserta y chequea 
				raiz = insertarConChequeo( raiz , numero );

			}

			// establece la profundidad de cada Nodo
			actualizarProfundidad();
			
		}catch( NoSuchElementException elementException ){

         	System.err.println( "El archivo no esta bien formado." );
         	entrada.close();
         	System.exit( 1 );

      	}catch( IllegalStateException stateException ){

         	System.err.println( "Error al leer del archivo." );
        	System.exit( 1 );

      	} // fin de catch

	}// fin del constructor

	// metodos servicios publicos
	public void imprimirArbol(){

		if( raiz != null ){
			System.out.printf("\n%5s%20s%16s\n\n", "Nodo" , "Cant. de Nodos" , 
				"Profundidad" );
			imprimirArbol( raiz );
		}
	}

	// metodos servicios privados generales
	//*********************************************************************
	private void actualizarAlturas( Nodo x ){
		if( x != null ){
			x.establecerAltura( definirAltura( x ) );
			actualizarAlturas( x.izquierdo );
			actualizarAlturas( x.derecho );
		}
	}// fin del metodo actualizarAlturas

	private void actualizarCantidadDeNodos( Nodo x ){
		if( x != null ){
			x.establecerNodos( definirHijos( x ) );
			actualizarCantidadDeNodos( x.izquierdo );
			actualizarCantidadDeNodos( x.derecho );
		}
	}// fin del metodo actualizarCanidadDeNodos

	private void actualizarProfundidad(){
		if( raiz != null )
			actualizarProfundidad( raiz , 0 );
	}// fin del metodo actualizarProfundidad

	private void imprimirSuma(){
		System.out.println("\n suma -> " + raiz_Antes + " + " + raiz_Despues + " = " + ( raiz_Antes+raiz_Despues ) );
	}
	//**********************************************************************

	private void actualizarProfundidad( Nodo r , int prof ){

		if( r != null ){
			r.establecerProfundidad( prof );
			prof++;
			actualizarProfundidad( r.izquierdo , prof );
			actualizarProfundidad( r.derecho , prof );
		}
	}

	private void actualizarAltura( Nodo x ){
		if( x != null )
			x.establecerAltura( definirAltura( x ) );
	}

	private void actualizarCantidadNodos( Nodo x ){
		if( x != null )
			x.establecerNodos( definirHijos( x ) );
	}

	private int definirAltura( Nodo x ){

		if( x == null ){
			return -1;
		}else{

			int alturaIzquierda = definirAltura( x.izquierdo );
			int alturaDerecha = definirAltura( x.derecho );

			if( alturaIzquierda > alturaDerecha )
				return alturaIzquierda + 1;
			else 
				return alturaDerecha + 1;

		}// fin else

	}// fin del metodo definirALtura

	private int definirHijos( Nodo x ){

		if( x == null ){
			return 0;
		}else{
			int hijosIzquierdos = definirHijos( x.izquierdo );
			int hijosDerechos = definirHijos( x.derecho );

			return hijosIzquierdos + hijosDerechos + 1 ;
		}
	}

	private Nodo insertarConChequeo( Nodo r , int n ){

		if( r == null )
			return new Nodo( n );
		else{

			if( n < r.obtenerNumero() ){

				r.izquierdo = insertarConChequeo( r.izquierdo , n );
				actualizarAltura( r );
				actualizarCantidadNodos( r );
				r = verificarDesbalance( r );

			}else{

				r.derecho = insertarConChequeo( r.derecho , n );
				actualizarAltura( r );
				actualizarCantidadNodos( r );
				r = verificarDesbalance( r );
				
			}

		}// fin else

		return r;

	}// fin del metodo insertar

	private Nodo insertar( Nodo r , int n ){

		if( r == null )
			return new Nodo( n );
		else{

			if( n < r.obtenerNumero() ){
				r.izquierdo = insertar( r.izquierdo , n );
			}else{
				r.derecho = insertar( r.derecho , n );
			}
		}

		return r;

	}// fin del metodo insertar

	private void imprimirArbol( Nodo r ){

		if( r != null ){
			System.out.printf("%5d%20d%16d ->H: %d\n", 
				r.obtenerNumero() , r.obtenerNodos() , r.obtenerProfundidad() ,
				r.obtenerAltura() );
			imprimirArbol( r.izquierdo );
			imprimirArbol( r.derecho );
		}
	}

	private ColaPrioritaria guardarNodosEnCola( Nodo r , ColaPrioritaria lista ){

		if( r != null ){
			lista.insertar( r.obtenerNumero() );
			lista = guardarNodosEnCola( r.izquierdo , lista );
			lista = guardarNodosEnCola( r.derecho , lista );
		}

		return lista;
	}

	private Nodo balancear( Nodo r , ColaPrioritaria lista ){

		if( lista.estaVacia() )
			return null;

		else{

			ColaPrioritaria listaIzq;  
			ColaPrioritaria listaDer;

			listaIzq = lista.parteIzquierda();
			listaDer = lista.parteDerecha();

			r = insertar( r , lista.sacarMedio() );

			r.izquierdo = balancear( r.izquierdo , listaIzq );
			r.derecho = balancear( r.derecho , listaDer );

		}

		return r;
		
	}// fin del metodo balancear

	private Nodo verificarDesbalance( Nodo r ){

		ColaPrioritaria lista = new ColaPrioritaria();

		// caso derecho null
		if( r.izquierdo != null && r.derecho == null ){

			Nodo izq = r.izquierdo;

			if( izq.obtenerNodos() > 1 ){

				if( r.obtenerAltura() > 3 ){
					raiz_Antes = r.obtenerNumero();
					lista = guardarNodosEnCola( r , lista );
					r = null;
					r = balancear( r , lista );
					actualizarAlturas( r );
					actualizarCantidadDeNodos( r );
					raiz_Despues = r.obtenerNumero();
					imprimirSuma();
					lista.vaciarCola();
					return r;
				}

			}
		
		}// fin caso derecho null

		// caso izquierdo null
		if( r.derecho != null && r.izquierdo == null ){

			Nodo der = r.derecho;

			if( der.obtenerNodos() > 1 ){

				if( r.obtenerAltura() > 3 ){
					raiz_Antes = r.obtenerNumero();
					lista = guardarNodosEnCola( r , lista );
					r = null;
					r = balancear( r , lista );
					actualizarAlturas( r );
					actualizarCantidadDeNodos( r );
					raiz_Despues = r.obtenerNumero();
					imprimirSuma();
					lista.vaciarCola();
					return r;
				}

			}
			
		}// fin caso izquierdo null

		// caso principal
		if( r.izquierdo != null && r.derecho != null ){

			Nodo nodo_izq = r.izquierdo;
			Nodo nodo_der = r.derecho;

			if( nodo_izq.obtenerNodos() >= nodo_der.obtenerNodos() * 2 ){

				if( r.obtenerAltura() > 3 ){
					raiz_Antes = r.obtenerNumero();
					lista = guardarNodosEnCola( r , lista );
					r = null;
					r = balancear( r , lista );
					actualizarAlturas( r );
					actualizarCantidadDeNodos( r );
					raiz_Despues = r.obtenerNumero();
					imprimirSuma();
					lista.vaciarCola();
					return r;
				}

			}else if( nodo_der.obtenerNodos() >= nodo_izq.obtenerNodos() * 2 ){

				if( r.obtenerAltura() > 3 ){
					raiz_Antes = r.obtenerNumero();
					lista = guardarNodosEnCola( r , lista );
					r = null;
					r = balancear( r , lista );
					actualizarAlturas( r );
					actualizarCantidadDeNodos( r );
					raiz_Despues = r.obtenerNumero();
					imprimirSuma();
					lista.vaciarCola();
					return r;
				}

			}

		}

		return r;

	}// fin del metodo verificar

}