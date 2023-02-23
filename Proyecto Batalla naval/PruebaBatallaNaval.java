
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

public class PruebaBatallaNaval{

	static final Random numero = new Random();

	static final int HORIZONTAL = 10;
	static final int VERTICAL = 10;

	static int[][] tablero_1 = new int[HORIZONTAL][VERTICAL];
	static int[][] tablero_2 = new int[HORIZONTAL][VERTICAL];

	public static void main( String[] args ){

		Scanner entrada = new Scanner(System.in);
		Scanner entrada2 = new Scanner(System.in); // cree otro objeto Scanner por un error en tiempo de ejecucion DUDA!!

		System.out.println();

		int i; // contador de ataques

		int disparosAcertados; // contador de aciertos

		boolean acerto;

		int posicion_x = 0;
		int posicion_y = 0;

		char caracterLetra;
		char caracterNumero;
		char aux;

		inicializarMatrix( tablero_1 );
		inicializarMatrix( tablero_2 );

		Tablero usuario = new Tablero( tablero_1 , "usuario.txt" );
		Tablero enemigo = new Tablero( tablero_2 );

		Ataque player = new Ataque();
		Ataque cpu = new Ataque();

		// esta instruccion define si se puede jugar o no !!
		if( usuario.iniciarGame() != true ){

			System.out.println("No se puede Iniciar el juego!. Verifique su archivo!");

		}else{

			// establece la cantidad de ataques
			player.establecerAtaques( usuario.obtenerCantidadDeBarcos() );
			cpu.establecerAtaques( enemigo.obtenerCantidadDeBarcos() );

			//Tablero.mostrarTableros( tablero_1 , tablero_2 , usuario , enemigo );

			// 1. obtener cantidad de barcos de cada jugador
			int barcosUsuario = usuario.obtenerCantidadDeBarcos();
			int barcosEnemigo = enemigo.obtenerCantidadDeBarcos();

			// 2. elegir Quien ira primero en el turno 1-jugador , 2-enemigo
			int turno = 1 + numero.nextInt( 2 );

			// MIENTRAS que el usuario y el enemigo tengan su contador de barcos > 0
			while( barcosUsuario > 0 && barcosEnemigo > 0 ){

				disparosAcertados = 0;

				acerto = false;

				// Si va primero el usuario -->
				if( turno < 2 ){

					// 3. indicar el ataque sera automatico o manual
					System.out.print(" Seleccione su tipo de ataque:\n 1.Automatico\n 2.Manual\n\n ?: ");
					int eleccion = entrada.nextInt();

					System.out.println();

					while( eleccion < 1 || eleccion > 2 ){
						System.out.print(" Eleccion incorrecta!. porfavor intente de nuevo: ");
						eleccion = entrada.nextInt();
					}// fin while de eleccion

					// a) automatico
					if( eleccion < 2 ){

						// generar 5 posiciones aleatorias y atacar
						for( i = player.obtenerAtaques() ; i > 0 ; i-- ){

							posicion_x = numero.nextInt( HORIZONTAL );
							posicion_y = numero.nextInt( VERTICAL );

							acerto = player.acertandoBlancos( tablero_2 , posicion_x , posicion_y );

							usuario.atacarPosicion( tablero_2 , enemigo , posicion_x , posicion_y , cpu );

							if( acerto != false )
								disparosAcertados++;

						}// fin for

					}else{ 	// b) manual

						// imprimir ataques disponibles
						System.out.printf("\n Ataques Disponibles: %d\n\n", player.obtenerAtaques() ); 

						// indicar q digite las coordenadas
						System.out.print(" -> ");
						String datos = entrada2.nextLine(); // DUDA PENDIENTE con la entrada !!

						// divide las coordenadas
						String[] tokens = datos.split(" ");

						if( tokens.length < player.obtenerAtaques() ){ // cuando el numero de ataques es menor al de los disponibles

							for( i = 0 ; i < tokens.length ; i++ ){

								if( tokens[i].length() < 3 ){ // si tiene menos de 3 caracteres

									if( Character.isLetter( tokens[i].charAt(0) ) && Character.isDigit( tokens[i].charAt(1) ) ){

										caracterLetra = tokens[i].charAt(0);
										caracterNumero = tokens[i].charAt(1);

										posicion_x = DevolverValorInt.obtenerEntero_ConLetra( caracterLetra );
										posicion_y = DevolverValorInt.obtenerEntero_ConCaracterNumerico( caracterNumero );

									}else{
										System.out.println("Coordenada Invalida!! " + tokens[i] );
									}

									acerto = player.acertandoBlancos( tablero_2 , posicion_x , posicion_y );

									usuario.atacarPosicion( tablero_2 , enemigo , posicion_x , posicion_y , cpu );

									if( acerto != false )
										disparosAcertados++;

								}else if( tokens[i].length() == 3 ){ // si tiene 3 caracteres

									if( Character.isLetter( tokens[i].charAt(0) ) && Character.isDigit( tokens[i].charAt(1) ) && 
											Character.isDigit( tokens[i].charAt(2) ) ){

										caracterLetra = tokens[i].charAt(0);
										caracterNumero = tokens[i].charAt(1);
										aux = tokens[i].charAt(2);

										char[] arreglo = { caracterNumero , aux };
										String numeroDeDosDigitos = new String( arreglo );

										posicion_x = DevolverValorInt.obtenerEntero_ConLetra( caracterLetra );
										posicion_y = Integer.parseInt( numeroDeDosDigitos );

										if( posicion_y > HORIZONTAL )
											continue;
										else{

											posicion_y--;
										
											acerto = player.acertandoBlancos( tablero_2 , posicion_x , posicion_y );

											usuario.atacarPosicion( tablero_2 , enemigo , posicion_x , posicion_y , cpu );

											if( acerto != false )
												disparosAcertados++;
										}

									}else{
										System.out.println("Coordenada Invalida!! " + tokens[i] );
									}

								}else{// si tiene mas de 3 caracteres
									System.out.println("Coordenada Invalida o no esta permitida!! " + tokens[i] );
								}

							}// fin for 

						}else if( tokens.length > player.obtenerAtaques() ){ // cuando el numero de coordenadas es mayor a los ataques disponibles

							for( i = 0 ; i < player.obtenerAtaques() ; i++ ){

								if( tokens[i].length() < 3 ){ // si tiene menos de 3 caracteres

									if( Character.isLetter( tokens[i].charAt(0) ) && Character.isDigit( tokens[i].charAt(1) ) ){

										caracterLetra = tokens[i].charAt(0);
										caracterNumero = tokens[i].charAt(1);

										posicion_x = DevolverValorInt.obtenerEntero_ConLetra( caracterLetra );
										posicion_y = DevolverValorInt.obtenerEntero_ConCaracterNumerico( caracterNumero );

									}else{
										System.out.println("Coordenada Invalida!! " + tokens[i] );
									}

									acerto = player.acertandoBlancos( tablero_2 , posicion_x , posicion_y );

									usuario.atacarPosicion( tablero_2 , enemigo , posicion_x , posicion_y , cpu );

									if( acerto != false )
										disparosAcertados++;

								}else if( tokens[i].length() == 3 ){ // si tiene 3 caracteres

									if( Character.isLetter( tokens[i].charAt(0) ) && Character.isDigit( tokens[i].charAt(1) ) && 
											Character.isDigit( tokens[i].charAt(2) ) ){

										caracterLetra = tokens[i].charAt(0);
										caracterNumero = tokens[i].charAt(1);
										aux = tokens[i].charAt(2);

										char[] arreglo = { caracterNumero , aux };
										String numeroDeDosDigitos = new String( arreglo );

										posicion_x = DevolverValorInt.obtenerEntero_ConLetra( caracterLetra );
										posicion_y = Integer.parseInt( numeroDeDosDigitos );

										if( posicion_y > HORIZONTAL )
											continue;
										else{

											posicion_y--;
										
											acerto = player.acertandoBlancos( tablero_2 , posicion_x , posicion_y );

											usuario.atacarPosicion( tablero_2 , enemigo , posicion_x , posicion_y , cpu );

											if( acerto != false )
												disparosAcertados++;

										}

									}else{
										System.out.println("Coordenada Invalida!! " + tokens[i] );
									}

								}else{// si tiene mas de 3 caracteres
									System.out.println("Coordenada Invalida o no esta permitida!! " + tokens[i] );
								}

							}// fin for tokens.length > player.obtenerAtaques() OJO!!

						}else{ // cuando el numero de coordenadas es igual al ataque

							for( i = 0 ; i < tokens.length ; i++ ){

								if( tokens[i].length() < 3 ){ // si tiene menos de 3 caracteres

									if( Character.isLetter( tokens[i].charAt(0) ) && Character.isDigit( tokens[i].charAt(1) ) ){

										caracterLetra = tokens[i].charAt(0);
										caracterNumero = tokens[i].charAt(1);

										posicion_x = DevolverValorInt.obtenerEntero_ConLetra( caracterLetra );
										posicion_y = DevolverValorInt.obtenerEntero_ConCaracterNumerico( caracterNumero );

									}else{
										System.out.println("Coordenada Invalida!! " + tokens[i] );
									}

									acerto = player.acertandoBlancos( tablero_2 , posicion_x , posicion_y );

									usuario.atacarPosicion( tablero_2 , enemigo , posicion_x , posicion_y , cpu );

									if( acerto != false )
										disparosAcertados++;

								}else if( tokens[i].length() == 3 ){ // si tiene 3 caracteres

									if( Character.isLetter( tokens[i].charAt(0) ) && Character.isDigit( tokens[i].charAt(1) ) && 
											Character.isDigit( tokens[i].charAt(2) ) ){

										caracterLetra = tokens[i].charAt(0);
										caracterNumero = tokens[i].charAt(1);
										aux = tokens[i].charAt(2);

										char[] arreglo = { caracterNumero , aux };
										String numeroDeDosDigitos = new String( arreglo );

										posicion_x = DevolverValorInt.obtenerEntero_ConLetra( caracterLetra );
										posicion_y = Integer.parseInt( numeroDeDosDigitos );

										if( posicion_y > HORIZONTAL )
											continue;
										else{

											posicion_y--;
										
											acerto = player.acertandoBlancos( tablero_2 , posicion_x , posicion_y );

											usuario.atacarPosicion( tablero_2 , enemigo , posicion_x , posicion_y , cpu );

											if( acerto != false )
												disparosAcertados++;
										}

									}else{
										System.out.println("Coordenada Invalida!! " + tokens[i] );
									}

								}else{// si tiene mas de 3 caracteres
									System.out.println("Coordenada Invalida o no esta permitida!! " + tokens[i] );
								}


							}// fin for

						}// fin else 

						System.out.println();

					}// fin else b) manual 

				if( disparosAcertados > 1 )
					player.establecerPuntoExtra();

				player.establecerAtaques( usuario.obtenerCantidadDeBarcos() );

				}else{ // turno del enemigo

					// generar 5 posiciones aleatorias y atacar
					for( i = cpu.obtenerAtaques() ; i > 0 ; i-- ){

							posicion_x = numero.nextInt( HORIZONTAL );
							posicion_y = numero.nextInt( VERTICAL );

							acerto = cpu.acertandoBlancos( tablero_1 , posicion_x , posicion_y );

							enemigo.atacarPosicion( tablero_1 , usuario , posicion_x , posicion_y , player );

							if( acerto != false )
								disparosAcertados++;

						}// fin for

					// verificar si acerto mas de 1 blanco
					if( disparosAcertados > 1 )
						cpu.establecerPuntoExtra();

					// establecer ataques de nuevo para el proximo turno
					cpu.establecerAtaques( enemigo.obtenerCantidadDeBarcos() );

				}// fin else

				// ********************************************************************* //

				// obtener cantidad de barcos de cada jugador de nuevo
				barcosUsuario = usuario.obtenerCantidadDeBarcos();
				barcosEnemigo = enemigo.obtenerCantidadDeBarcos();

				if( turno > 1 )
					System.out.println("Turno: Oponente!!\n");
				else
					System.out.println("Turno: Usuario!!\n");

				// imprimir tableros
				Tablero.mostrarTableros( tablero_1 , tablero_2 , usuario , enemigo );

				// si el turno es del juegador entonces pasarlo al enemigo
				if( turno > 1 )
					turno = 1;
				else
					turno = 2;

				System.out.println();

			}// fin while

			System.out.println("  \nFin del juego!\n");

			if( usuario.obtenerCantidadDeBarcos() == 0 )
				System.out.println(" Usuario Game over!!");
			else
				System.out.println(" Usuario Winner!!");

		}// fin ELSE PRINCIPAL

	}// fin del metodo main

	public static void inicializarMatrix( int[][] m ){

		for( int i = 0 ; i < m.length ; i++ ){
			for( int j = 0 ; j < m[i].length ; j++ ){
				m[i][j] = 0;
			}// fin for
		}// fin for externo
	}// fin del metodo inicializarMatrix

}// fin de clase PruebaBatallaNaval