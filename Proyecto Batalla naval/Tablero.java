
import java.util.Random;
import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.IllegalStateException;
import java.util.NoSuchElementException;

public class Tablero{

	private Scanner entrada;
	private Random numeroAleatorio = new Random();
	private boolean iniciarJuego = true;

	// variables auxiliares
	private int fila;
	private int columna;

	// variables para trabajar con los caracteres de pivote
	private char caracterFila;
	private char caracterColumna;
	private char caracterAux;

	// arreglo de caracteres constantes para representar
	// las filas en el tablero
	static final char[] nombreFilas = { 'A' , 'B' , 'C' , 'D' , 'E' , 'F' ,
		'G' , 'H' , 'I' , 'J' };

	// barcos
	private PortaAviones portaA = new PortaAviones();
	private Acorazados acorazados = new Acorazados();
	private Destructores destructor = new Destructores();
	private Fragata fragata = new Fragata();

	// identificacion de barcos con numeros enteros
	int p = 2;
	int a = 3;
	int d = 4;
	int f = 5;

	// este constructor recibe el archivo y el tablero del usuario
	public Tablero( int[][] usuario , String archivo ){

		try{

			entrada = new Scanner( new File(archivo) );

		}catch( FileNotFoundException fileNotFoundException ){

			System.out.println("Error al Abrir el archivo!");
			iniciarJuego = false;
			System.exit(1);
		}// fin bloque try

		try{

			while( entrada.hasNext() ){

				// lectura de cada dato en el archivo
				LeerCoordenadas.establecerLetra( entrada.next().charAt( 0 ) );
				LeerCoordenadas.establecerCoordenada( entrada.next() );
				LeerCoordenadas.establecerDireccion( entrada.nextInt() );

				// variable para obtener el tamaño de las coordenadas
				String c = LeerCoordenadas.obtenerCoordenada();

				if( c.length() < 3 ){

					if( Character.isLetter( c.charAt( 0 ) ) && Character.isDigit( c.charAt( 1 ) ) ){
						caracterFila = c.charAt( 0 );
						caracterColumna = c.charAt( 1 );

						fila = DevolverValorInt.obtenerEntero_ConLetra( caracterFila );
						columna = DevolverValorInt.obtenerEntero_ConCaracterNumerico( caracterColumna );

					}else{
						System.out.println("Coordenada Invalida!! " + c );
					}
					
				}

				if( c.length() == 3 ){

					if( Character.isLetter( c.charAt( 0 ) ) && Character.isDigit( c.charAt( 1 ) ) && Character.isDigit( c.charAt( 2 ) ) ){
						caracterFila = c.charAt( 0 );

						// arreglo necesario para convertir un numero de dos digitos de char a String
						// para poder convertir el String a int
						char[] arreglo = new char[2];

						// copia los dos numeros que son caracteres 		
						c.getChars( 1 , 3 , arreglo , 0 ); 

						// crea un String para q represente el numero de dos digitos
						String numeroDeDosDigitos = new String( arreglo );

						fila = DevolverValorInt.obtenerEntero_ConLetra( caracterFila );
						columna = Integer.parseInt( numeroDeDosDigitos ); // el String se convierte a entero

						columna = columna - 1;

					}else{
						System.out.println("Secuencia de caracteres Invalida!! " + c );
					}
				}

				int validarPosicion = asignarBarco( usuario , LeerCoordenadas.obtenerLetra() , 
					fila , columna , LeerCoordenadas.obtenerDireccion() ); 

				if( validarPosicion != 0 ){

					if( validarPosicion < 2 ){
						System.out.println("Error la longitud del barco " + LeerCoordenadas.obtenerLetra() + " Sobrepasa los limites del " +
							"tablero en la coordenada " + c + " en direccion " + LeerCoordenadas.obtenerDireccion() );
						System.out.println("\n");
						iniciarJuego = false;
						break;
					}else{
						System.out.println("Error EL barco " + LeerCoordenadas.obtenerLetra() + " no puede ocupar las casillas de otro barco\n" +
							"ubicacion : " + c + " en direccion " + LeerCoordenadas.obtenerDireccion() );
						System.out.println();
						iniciarJuego = false;
						break;
					}
				}

			}// fin while

		}catch( NoSuchElementException elementException ){

         	System.err.println( "El archivo no esta bien formado." );
         	entrada.close();
         	iniciarJuego = false;
         	System.exit( 1 );

      	}catch( IllegalStateException stateException ){

         	System.err.println( "Error al leer del archivo." );
         	iniciarJuego = false;
        	System.exit( 1 );

      	} // fin de catch

	}// fin del constructor con 2 argumentos

	// este constructor recibe el tablero del enemigo para realizar todas las tareas 
	// de manera aleatorio
	public Tablero( int[][] enemigo ){

		// contiene los barcos del enemigo
		char[] barco = { 'P' , 'A' , 'A' , 'A' , 'D' , 'D' , 'D' , 'F' , 'F' };

		// variable auxiliar para colocar un barco
		boolean colocar = false;

		// recorre el arreglo de caracteres barco
		for( char c : barco ){

			// generan las coordenadas donde se colocara el barco en la matrix y en que direccion
			int x = numeroAleatorio.nextInt( enemigo.length );
			int y = numeroAleatorio.nextInt( enemigo.length );
			int direccion = numeroAleatorio.nextInt( 4 );

			switch( c ){

				case 'P': // barco porta Aviones

					portaA.incrementarContador();

					colocar = verificarTableroEnemigo( enemigo , portaA.obtenerLongitud() , x , y , direccion );

					while( colocar != true ){

						x = numeroAleatorio.nextInt( enemigo.length );
						y = numeroAleatorio.nextInt( enemigo.length );
						direccion = numeroAleatorio.nextInt( 4 );

						colocar =verificarTableroEnemigo( enemigo , portaA.obtenerLongitud() , x , y , direccion );

					}// fin while

					asignar( enemigo , x , y , direccion , portaA.obtenerLongitud() , p );

					break;

				case 'A': // barco acorazado

					acorazados.incrementarContador();

					colocar = verificarTableroEnemigo( enemigo , acorazados.obtenerLongitud() , x , y , direccion );

					while( colocar != true ){

						x = numeroAleatorio.nextInt( enemigo.length );
						y = numeroAleatorio.nextInt( enemigo.length );
						direccion = numeroAleatorio.nextInt( 4 );

						colocar =verificarTableroEnemigo( enemigo , acorazados.obtenerLongitud() , x , y , direccion );

					}// fin while

					asignar( enemigo , x , y , direccion , acorazados.obtenerLongitud() , a );

					break;

				case 'D': // barco destructor

					destructor.incrementarContador();

					colocar = verificarTableroEnemigo( enemigo , destructor.obtenerLongitud() , x , y , direccion );

					while( colocar != true ){

						x = numeroAleatorio.nextInt( enemigo.length );
						y = numeroAleatorio.nextInt( enemigo.length );
						direccion = numeroAleatorio.nextInt( 4 );

						colocar =verificarTableroEnemigo( enemigo , destructor.obtenerLongitud() , x , y , direccion );

					}// fin while

					asignar( enemigo , x , y , direccion , destructor.obtenerLongitud() , d );

					break;

				case 'F': // barco fragata

					fragata.incrementarContador();

					colocar = verificarTableroEnemigo( enemigo , fragata.obtenerLongitud() , x , y , direccion );


					while( colocar != true ){

						x = numeroAleatorio.nextInt( enemigo.length );
						y = numeroAleatorio.nextInt( enemigo.length );
						direccion = numeroAleatorio.nextInt( 4 );

						colocar =verificarTableroEnemigo( enemigo , fragata.obtenerLongitud() , x , y , direccion );

					}// fin while

					asignar( enemigo , x , y , direccion , fragata.obtenerLongitud() , f );

					break;
			}
		}

	} // fin del constructor con un argumento 

	private boolean verificarTableroEnemigo( int[][] tablero , int c , int x , int y , int d ){

		int i;

		switch( d ){

			case 0:

				for( i = c ; i > 0 ; i-- ){

					if( y >= tablero[x].length )
						return false;

					if( tablero[x][y] != 0 )
						return false;

					y++;
				}
				break;

			case 1:

				for( i = c ; i > 0 ; i-- ){

					if( x >= tablero.length )
						return false;

					if( tablero[x][y] != 0 )
						return false;

					x++;
				}
				break;

			case 2:

				for( i = c ; i > 0 ; i-- ){

					if( y >= -1 )
						return false;

					if( tablero[x][y] != 0 )
						return false;

					y--;

				}
				break;

			case 3:

				for( i = c ; i > 0 ; i-- ){

					if( x >= -1 )
						return false;

					if( tablero[x][y] != 0 )
						return false;

					x--;
				}
				break;
		}

		return true;
	}

	private int asignarBarco( int[][] tablero , char b , int x , int y , int direccion ){

		int i; // contador
		int resp = 0; // variable para guardar el resultado del metodo asignar

		switch( b ){

			case 'P': // caso porta Avion
			case 'p':

				portaA.incrementarContador();
				resp = asignar( tablero , x , y , direccion , portaA.obtenerLongitud() , p );
				break;

			case 'A': // caso acorazado
			case 'a':

				acorazados.incrementarContador();
				resp = asignar( tablero , x , y , direccion , acorazados.obtenerLongitud() , a );
				break;

			case 'D': // caso destructor
			case 'd':

				destructor.incrementarContador();
				resp = asignar( tablero , x , y , direccion , destructor.obtenerLongitud() , d );
				break;

			case 'F': // caso fragata
			case 'f':

				fragata.incrementarContador();
				resp = asignar( tablero , x , y , direccion , fragata.obtenerLongitud() , f );
				break;

		}// fin del switch 

		return resp;
				
	}// fin del metodo asignar Barco

	private int asignar( int[][] tablero , int x , int y , int direccion , int contador , int identidad ){

		int i; // contador

		switch( direccion ){

					case 0: // coloca el barco en direccion hacia la derecha

						for( i = contador ; i > 0 ; i-- ){

							if( y < tablero[x].length && tablero[x][y] == 0 ){
								tablero[x][y] = identidad;
								y++;
							}else{

								if( y >= tablero[x].length )
									return 1;
								else
									return 2;
							}

						}// fin for 

						break;

					case 1: // coloca el barco en direccion hacia abajo 

						for( i = contador ; i > 0 ; i-- ){

							if( x < tablero.length && tablero[x][y] == 0 ){
								tablero[x][y] = identidad;
								x++;
							}else{
								
								if( x >= tablero.length )
									return 1;
								else
									return 2;
							}

						}// fin for

						break;

					case 2: // coloca el barco en direccion hacia la izquierda

						for(  i = contador ; i > 0 ; i-- ){

							if( y >= 0 && tablero[x][y] == 0 ){
								tablero[x][y] = identidad;
								y--;
							}else{
								
								if( y < 0 )
									return 1;
								else
									return 2;
							}

						}// fin for 

						break;

					case 3: // coloca el barco en direccion hacia arriba

						for( i = contador ; i > 0 ; i-- ){

							if( x > 0 && tablero[x][y] == 0 ){
								tablero[x][y] = identidad;
								x--;
							}else{
								
								if( tablero[x][y] != 0 )
									return 2;
								else
									return 1;
							}

						}// fin for

						break;

				}// fin del switch de destructor direccion

				return 0; // indica que realizo su trabajo correctamente

	}// fin del metodo asignar

	public static void mostrarTableros( int[][] usuario , int[][] enemigo , Tablero jugador , Tablero oponente ){

		int i = 0;
		int j;

		System.out.print("  1 2 3 4 5 6 7 8 9 10  ");

		System.out.print("||");

		System.out.print("   1 2 3 4 5 6 7 8 9 10  ");

		System.out.println("\n");

		while( i < usuario.length ){

			System.out.print( nombreFilas[i] + " ");

			for( j = 0 ; j < usuario[i].length ; j++ ){
				if( usuario[i][j] == 0 )
					System.out.print( " " + " " );
				else if( usuario[i][j] == 1 )
					System.out.print( "X" + " " );
				else if( usuario[i][j] > 0 )
					System.out.printf("%c ", jugador.obtenerLetraDelBarco( usuario[i][j] ) );
				else
					System.out.printf("%c ", Character.toLowerCase( jugador.obtenerLetraDelBarco( usuario[i][j] / -1 ) ) );	
			}// fin for

			System.out.print("  ||   ");

			for( j = 0 ; j < enemigo[i].length ; j++ ){
				
				if( enemigo[i][j] == 0 )
					System.out.print( " " + " " );
				else if( enemigo[i][j] == 1 )
					System.out.print( "X" + " " );
				else if( enemigo[i][j] > 0 )
					System.out.printf("  ", oponente.obtenerLetraDelBarco( enemigo[i][j] ) );
				else
					System.out.printf("%c ", Character.toLowerCase( oponente.obtenerLetraDelBarco( enemigo[i][j] / -1 ) ) );	
			}

			System.out.println();

			i++;
		}

	}// fin dle metodo mostrarTableros

	public void atacarPosicion( int[][] tabla , Tablero tablero , int x , int y , Ataque ataque ){

		int temp = 0;

		switch( tabla[x][y] ){

			case 0:
				tabla[x][y] = 1;
				break;

			case 1:
				// no realizar ninguna accion
				break;

			case 2:

				temp = tabla[x][y];

				tabla[x][y] /= -1;

				if( tablero.obtenerVidaDelBarco( temp ) > 0 )
					tablero.disminuirVida( temp );
				
				if( tablero.obtenerVidaDelBarco( temp ) == 0 ){
					tablero.destruirBarco( temp );
					ataque.decrementarAtaques();
					tablero.establecerHp( temp );
				}

				break;
				
			case 3:

				temp = tabla[x][y];

				tabla[x][y] /= -1;

				if( tablero.obtenerVidaDelBarco( temp ) > 0 )
					tablero.disminuirVida( temp );
				
				if( tablero.obtenerVidaDelBarco( temp ) == 0 ){
					tablero.destruirBarco( temp );
					ataque.decrementarAtaques();
					tablero.establecerHp( temp );
				}
				break;

			case 4:

				temp = tabla[x][y];

				tabla[x][y] /= -1;

				if( tablero.obtenerVidaDelBarco( temp ) > 0 )
					tablero.disminuirVida( temp );
				
				if( tablero.obtenerVidaDelBarco( temp ) == 0 ){
					tablero.destruirBarco( temp );
					ataque.decrementarAtaques();
					tablero.establecerHp( temp );
				}
				break;

			case 5:

				temp = tabla[x][y];

				tabla[x][y] /= -1;

				if( tablero.obtenerVidaDelBarco( temp ) > 0 )
					tablero.disminuirVida( temp );

				if( tablero.obtenerVidaDelBarco( temp ) == 0 ){
					tablero.destruirBarco( temp );
					ataque.decrementarAtaques();
					tablero.establecerHp( temp );
				}

				break;

			default :
				break;
		}// fin dle switch

	}// fin dle metodo atacaPosicion

	public void establecerHp( int barco ){

		switch( barco ){

			case 2:
				portaA.establecerVida();
				break;

			case 3:
				acorazados.establecerVida();
				break;

			case 4:
				destructor.establecerVida();
				break;

			case 5:
				fragata.establecerVida();
				break;
		}// fin dle switch
	}

	public int obtenerVidaDelBarco( int barco ){

		int vida = 0;

		switch( barco ){

			case 2:
				vida = portaA.obtenerVida();
				break;

			case 3:
				vida = acorazados.obtenerVida();
				break;

			case 4:
				vida = destructor.obtenerVida();
				break;

			case 5:
				vida = fragata.obtenerVida();
				break;

		}// fin dle switch

		return vida;

	}// fin dle metodo obtenerVidaDelBarco

	public void destruirBarco( int barco ){

		switch( barco ){

			case 2:
				portaA.decrementarContador();
				break;

			case 3:
				acorazados.decrementarContador();
				break;

			case 4:
				destructor.decrementarContador();
				break;

			case 5:
				fragata.decrementarContador(); 
				break;
		}
	}

	public void disminuirVida( int barco ){

		switch( barco ){

			case 2:
				portaA.decrementarVida();
				break;

			case 3:
				acorazados.decrementarVida();
				break;

			case 4:
				destructor.decrementarVida();
				break;

			case 5:
				fragata.decrementarVida();
				break;
		}// fin dle switch 

	}// fin del metodo disminuir vida

	// POSIBLEMENTE ESTE METODO LO USE EN EL MAIN
	public int obtenerCantidadDeBarcos(){

		return portaA.obtenerContador() + acorazados.obtenerContador() + destructor.obtenerContador() + fragata.obtenerContador();

	}// fin del metodo obtenerCantidadDeBarcos

	// devuelve la letra que identifica el barco seleccionado
	public char obtenerLetraDelBarco( int barco ){

		switch( barco ){

			case 2:
				caracterAux = portaA.obtenerIdentificacion();
				break;

			case 3:
				caracterAux = acorazados.obtenerIdentificacion();
				break;

			case 4:
				caracterAux = destructor.obtenerIdentificacion();
				break;

			case 5:
				caracterAux = fragata.obtenerIdentificacion();
				break;
		}

		return caracterAux;
	}

	// define si se puede jugar o no
	public boolean iniciarGame(){
		return iniciarJuego;
	}

}// fin de la clase Tablero

