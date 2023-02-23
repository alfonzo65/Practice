/* Programa para leer un archivo que contiene 1000 numeros desordenados,
e ordenarlos de menor a mayor previamente con 4 metodos de ordenacion 
contando los intercambios que este realiza  */
#include <stdio.h>
#include <conio.h>
#include <string.h>
#define	 FILA 10
#define  ARCHIVO "algo.txt"

/* prototipos de metodos de ordenacion */
void ordenamientoBurbuja( int caja[] , int tamanio , int *frec );
void ordenamientoSeleccion( int caja[] , int tamanio , int *frec );
void ordenamientoInsercionbinaria( int caja[] , int tamanio , int *frec );
void ordenamientoShell( int caja[] , int tamanio , int *frec );
void ordenamientoCubeta( int caja[] , int tamanio , int *frec );

/* prototipos */
void determinar_Cantidaddenumeros( const char *archivo , int *conteo );
void copiarArreglo( int arreglo_aux[] , int arreglo[] , int tamanio );
int leerArchivo( const char *archivo , int arreglo[] , int i );
void imprimirArreglo( int arreglo[] , int tamanio );

/* la funcion main inicia la ejecucion */
int main(){
	
	int tamanio_total = 0 ;
	int conteo = 0 ;

	determinar_Cantidaddenumeros( ARCHIVO , &tamanio_total );

	int arr[tamanio_total] ;
	int aux[tamanio_total] ;
	int op = 0 ;

	leerArchivo( ARCHIVO , arr , conteo ); /* lee el contenido del archvo */
	
	copiarArreglo( aux , arr , tamanio_total );/* copia el contenido en otro arreglo */

	/* Imprime el arreglo Original */
	printf("\n\n%48s\n", "***--> Arreglo Original: <--***" );
	imprimirArreglo( aux , tamanio_total );

	printf("\n\n%48s\n", "****--> Resultados <--****" );
	
	/* primer metodo de ordenacion */
	ordenamientoBurbuja( aux , tamanio_total , &op );
	printf("\n\n%-6s%-8d\n", "-> Ordenamiento Burbuja : " , op );
	//imprimirArreglo( aux , TAMANIO );

	/* copia nuevamente los elementos del arreglo en el aux */ 
	copiarArreglo( aux , arr , tamanio_total );
	op = 0 ; /* reinicia op en o 

	/* segundo metodo de ordenacion */
	ordenamientoSeleccion( aux , tamanio_total , &op );
	printf("\n\n%-6s%-8d\n", "-> Ordenamiento Seleccion : " , op );
	//imprimirArreglo( aux , TAMANIO );

	/* copia nuevamente los elementos del arreglo en el aux */ 
	copiarArreglo( aux , arr , tamanio_total );
	op = 0 ; /* reinicia op en o 

	/* tercer metodo de ordenacion */
	ordenamientoInsercionbinaria( aux , tamanio_total , &op );
	printf("\n\n%-6s%-8d\n", "-> Ordenamiento Insercion Binaria : ", op );
	//imprimirArreglo( aux , TAMANIO );

	/* copia nuevamente los elementos del arreglo en el aux */ 
	copiarArreglo( aux , arr , tamanio_total );
	op = 0 ; /* reinicia op en o 

	/* cuarto metodo de ordenacion */
	ordenamientoShell( aux , tamanio_total , &op );
	printf("\n\n%-6s%-8d\n", "-> Ordenamiento Shell : ", op );
	//imprimirArreglo( aux , TAMANIO );

	/* copia nuevamente los elementos del arreglo en el aux */ 
	copiarArreglo( aux , arr , tamanio_total );
	op = 0 ; /* reinicia op en o 

	/* quinto metodo de ordenamiento */
	ordenamientoCubeta( aux , tamanio_total , &op );
	printf("\n\n%-6s%-8d\n", "-> Ordenamiento Cubeta : " , op );
	//imprimirArreglo( aux , TAMANIO );

	getch();
	
	return 0 ; /* indica terminacion exitosa */
	
}/* fin de la funcion main */

void determinar_Cantidaddenumeros( const char *archivo , int *conteo ){

	int numero ;

	FILE *ptrA ;

	if(( ptrA = fopen( archivo , "r" )) == NULL ){
		printf("Error al intentar leer archivo\n");
	}
	else{

		while( fscanf( ptrA , "%d" , &numero ) != EOF ){
			(*conteo)++ ;
		}

		fclose( ptrA );
	}
}

/* lee el contenido del archivo */
int leerArchivo( const char *archivo , int arreglo[] , int i ){
	
	int num ;
	
	i = 0 ;
	
	FILE *ptrF ;
	
	if( ( ptrF = fopen( archivo , "r" )) == NULL ){
		return -1 ;
	}
	else{

		while( fscanf( ptrF , "%d" , &num ) != EOF ){

			arreglo[i] = num ;
			i++ ;
		}

		fclose( ptrF ); /* cierra el archivo */
	}
	
	return 0 ;
	
}// fin del procedimiento leer archivo

/* copia el contenido del arreglo principal en otro */
void copiarArreglo( int arreglo_aux[] , int arreglo[] , int tamanio ){

 	int i ;

 	for( i = 0 ; i < tamanio ; i++ ){
 		arreglo_aux[i] = arreglo[i] ;
 	}

}// fin del procedimiento copiarArreglo

/* metodo de ordenamiento Burbuja */ 
void ordenamientoBurbuja( int caja[] , int tamanio , int *frec ){
	
	/* procedimiento exclusivo del metodo burbuja */
	void intercambia( int *ptrElemento1 , int *ptrElemento2 , int *frec_aux );

	int i ;
	int pasada ;

	for( pasada = 1 ; pasada < tamanio ; pasada++ ){

		for( i = 0 ; i < tamanio - 1 ; i++ ){
			
			if( caja[i] > caja[i+1] ){
				intercambia( &caja[i] , &caja[i+1] , frec );
			}
		}
	}

}// fin del ordenamientoBurbuja

/* metodo de ordenamiento Seleccion */
void ordenamientoSeleccion( int caja[] , int tamanio , int *frec ){
	
	/* procedimiento exclusivo del metodo de seleccion */
	void intercambia( int *ptrElemento1 , int *ptrElemento2 , int *frec_aux );
	
	int i , j ;
	int minimo ;

	for( i = 0 ; i < tamanio ; i++ ){
		
		minimo = i ;
		
			for( j = i + 1 ; j < tamanio ; j++ ){
			
				if( caja[j] < caja[minimo] ){
					minimo = j ;
				}
			}
		
		//if( minimo != i ){
			intercambia( &caja[i] , &caja[minimo] , frec );
		//}	
	}
	
}// fin del ordenamiento por seleccion

/* ordenamiento por insercion */
void ordenamientoInsercionbinaria( int caja[] , int tamanio , int *frec ){

	int i , j ; 
	int aux ;
	int izquierda , derecha , centro ;
	
	for( i = 1 ; i < tamanio ; i++ ){

		aux = caja[ i ] ;
		izquierda = 0 ;
		derecha = i - 1 ;

		while( izquierda <= derecha ){

			centro = (int) ( izquierda + derecha ) / 2 ;

			if( aux <= caja[ centro ] ){
				derecha = centro - 1 ;
			}
			else{
				izquierda = centro + 1 ;
			}

		}// fin del while

		for( j = i - 1 ; j >= izquierda ; j-- ){
			caja[ j + 1 ] = caja[ j ] ;
			(*frec)++ ;
		}//fin delfor interno

		caja[ izquierda ] = aux ;
		(*frec)++ ;

	}// fin del for externo

}// fin del ordenamiento por insercion binaria

/* metodo de ordenacion Shell */
void ordenamientoShell( int caja[] , int tamanio , int *frec ){

	/* procedimiento exclusivo del metodo de Shell */
	void intercambia( int *ptrElemento1 , int *ptrElemento2 , int *frec_aux );

	int cont , paso , temp , i ;
	
	for( cont = tamanio / 2 ; cont != 0 ; cont/=2 ){

		paso = 1 ; 
		
		while( paso != 0 ){

			paso = 0 ;

			for( i = cont ; i < tamanio ; i++ ){

				if( caja[ i - cont ] > caja[ i ] ){
					intercambia( &caja[i] , &caja[ i - cont ] , frec );
					paso++ ;
				}
			}
		}
	}

}// fin del metodo de ordenamiento Shell

/* metodo de ordenamiento Cubeta */
void ordenamientoCubeta( int caja[] , int tamanio , int *frec ){
	
	int i , ubi = 0 ;	/* contadores para el posicionado */
	int f , c ;			/* contadores para las filas y columnas */
	int p ;				/* contador para la asignacion en el arreglo principal */
	
	int proceso ;	  	/* valor centinela del do while principal */
		
	int temp ;			/* variable de almacenamiento temporal */
	int mayor ;			/* variable para almacenar el numero mayor del arreglo */
	
	int mod = 10 ;		/* necesarios para romper el ciclo del Ordenamiento por Cubeta */
	int div_mod = 1 ; 
	
	int sub_arreglo[ FILA ][ tamanio ] ; /* declara un arreglo de doble subindice */
	
	/* inicializa los elementos del sub_arreglo en 0 */
	memset( sub_arreglo , 0 , sizeof( sub_arreglo ) );
	
	mayor = caja[0] ;	/* declara mayor como el primer elemento del arreglo */

	/* determina el numero mayor del arreglo principal */ 
	for( i = 0 ; i < tamanio ; i++ ){
			
		if( caja[i] > mayor ){
			mayor = caja[i] ;
		}
	}
	
	/* inicio del metodo ordenamiento Cubeta */
	do{
		
		/* recorre el arreglo caja y coloca cada uno de sus 
		elementos en las filas del sub_arreglo basandose en sus
		digitos de derecha a izquierda en cada ciclo */  
		for( i = 0 ; i < tamanio ; i++ ){
				
			temp = ( ( caja[i] % mod ) / div_mod ) ; /* calcula su primer digito comenzando por la 
														derecha hasta la izquierda en cada ciclo */
			
			ubi = 0 ; // reinicia la columna cada vez que rompe el ciclo 
			
			while( sub_arreglo[temp][ubi] != 0 ){
                   ubi++ ;
            }
                   
			sub_arreglo[temp][ubi] = caja[i] ;
			
		}/* fin del for */
 
 		// variable aux para posicionar los elementos en el arreglo
		p = 0 ; 
		
		/* recorre el sub_arreglo y coloca en el arreglo original 
		cada numero que va consigiendo desde el inicio hasta el fin */
		for( f = 0 ; f < FILA ; f++ ){
			
			for( c = 0 ; c < tamanio ; c++ ){
				
				if( sub_arreglo[f][c] != 0 ){
					caja[p] = sub_arreglo[f][c] ;
					p++ ;
					(*frec)++ ;
				}
			}
		}
		
		/* reinicia en 0 todos los elementos del 
		sub_arreglo */
		memset( sub_arreglo , 0 , sizeof( sub_arreglo ) );
			
		proceso	= (float) mayor / div_mod  ;
		
		/* condicion para romper el proceso de ordenado */
		if( proceso == 0. ){
			proceso = -1 ;
		}
		
		mod *= 10 ;
		div_mod *= 10 ;
		
	}while( proceso != -1 ); /* fin del do while principal */
}// fin del ordenamiento por Cubeta

/* procedimiento para realizar el intercambio de elementos 
exclusivo de burbuja , seleccion y Shell */
void intercambia( int *ptrElemento1 , int *ptrElemento2 , int *frec_aux ){
	
	int temp ;
	
	temp = *ptrElemento1 ;
	*ptrElemento1 = *ptrElemento2 ;
	*ptrElemento2 = temp ;
	(*frec_aux)+=2 ;

}// fin de la funcion intercambia

/* procedimiento para imprimir un arreglo */
void imprimirArreglo( int arreglo[] , int tamanio ){

	int i ; /* contador */

	for( i = 0 ; i < tamanio ; i++ ){

		if( i % 10 == 0 ){
			printf("\n");
		}

		printf("  %4d ", arreglo[i] );
	}

}// fin de inprimirArreglo
