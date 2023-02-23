
public class LeerCoordenadas{

	static char letra;
	static String coordenada;
	static int direccion;

	public static void establecerLetra( char l ){
		letra = l;
	}

	public static void establecerCoordenada( String posicion ){
		coordenada = posicion;
	}

	public static void establecerDireccion( int d ){
		direccion = d;
	}

	public static String obtenerCoordenada(){
		return coordenada;
	}

	public static char obtenerLetra(){
		return letra;
	}

	public static int obtenerDireccion(){
		return direccion;
	}
}