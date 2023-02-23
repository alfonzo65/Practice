
public class PruebaABBP{
	
	public static void main( String[] args ){


		try{

			if( args.length < 2 ){
			ArbolPerezoso arbol = new ArbolPerezoso( args[0] );
			System.out.println("\n -> Arbol: ");
			arbol.imprimirArbol();
			}else{
				System.out.println("\nError no se proporciono el archivo" + 
					" por linea de comandos!\n o hay mas de un argumento!");
			}

		}catch( Exception excepion ){
			System.out.println("Error!!\n No se proporciono ningun argumento por linea de comandos");
		}
		

	}
}