
public class DevolverValorInt{

	public static int obtenerEntero_ConLetra( char letra ){

		int numero = 0;

		switch( letra ){

			case 'A':
				numero = 0;
				break;

			case 'B':
				numero = 1;
				break;

			case 'C':
				numero = 2;
				break;

			case 'D':
				numero = 3;
				break;

			case 'E':
				numero = 4;
				break;

			case 'F':
				numero = 5;
				break;

			case 'G':
				numero = 6;
				break;

			case 'H':
				numero = 7;
				break;

			case 'I':
				numero = 8;
				break;

			case 'J':
				numero = 9;
				break;

		}// fin switch 

		return numero;
	}

	public static int obtenerEntero_ConCaracterNumerico( char caracter ){

		// solo acepta un valor de caracter de 0 a 9

		int n = 0;

		switch( caracter ){

			case '1':
				n = 0;
				break;

			case '2':
				n = 1;
				break;

			case '3':
				n = 2;
				break;

			case '4':
				n = 3;
				break;

			case '5':
				n = 4;
				break;

			case '6':
				n = 5;
				break;

			case '7':
				n = 6;
				break;

			case '8':
				n = 7;
				break;

			case '9':
				n = 8;
				break;

		}// fin del switch

		return n;
	}
}