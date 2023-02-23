/* figura 12.3: fig12_03.c
   Operacion y mantenimiento de una lista */
#include <stdio.h>
#include <stdlib.h>

/* estructura auto_referenciada */
struct nodoLista{
	char dato; /* cada nodoLista contiene un caracter */
	struct nodoLista *ptrSiguiente; /* apuntador al siguiente nodo */
};// fin de la estructura nodoLista 

typedef struct nodoLista NodoLista ;	/* sinonimo para la estructura nodoLista */
typedef NodoLista *ptrNodoLista ;		/* sinonimo para de NodoLista */

/* prototipos */
void insertar( ptrNodoLista *ptrS , char valor );
char eliminar( ptrNodoLista *ptrS , char valor );
int estaVacia( ptrNodoLista ptrS );
void imprimeLista( ptrNodoLista ptrActual );
void instrucciones( void );

int main(){

	ptrNodoLista ptrInicial = NULL; /* inicialmente no existen nodos */
	int eleccion; /* eleccion del usuario */
	char elemento; /* caracter introducido por el usuario */
	
	instrucciones(); /* despliega el menu */
	printf("? ");
	scanf("%d", &eleccion );

	/* repite mientras el usuario no elija 3 */
	while( eleccion != 3 ){

		switch( eleccion ){

			case 1:
				printf("Introduzca un caracter: ");
				scanf("\n%c", &elemento );

				insertar( &ptrInicial , elemento ); /* inserta el elemento en la lista */
				imprimeLista( ptrInicial );
				break ;

			case 2:

				/* si la lista no esta vacia */
				if( !estaVacia( ptrInicial) ){
					printf("Introduzca un caracter para eliminar: ");
					scanf("\n%c", &elemento );

					/* si encuentra el caracter, lo remueve */
					if( eliminar( &ptrInicial , elemento ) ){
						/* elimina elemento */
						printf("caracter %c eliminado.\n", elemento );
						imprimeLista( ptrInicial );
					}
					else{
						printf("no se encuentra el caracter %c.\n\n", elemento );
					}
				}
				else{
					printf("La lista esta vacia.\n\n");
				}

				break ;

			default:
				printf("Opcion invalida. \n\n");
				instrucciones();
				break ;

		}// fin del switch

			printf("? ");
			scanf("%d", &eleccion );

	}// fin del while

	printf("Fin de la ejecucion.\n");

	return 0 ;
}// fin main

/* despliega las instruccion del programa para el usuario */
void instrucciones( void ){

	printf("Introduzca su eleccion:\n"
		"    1 para insertar un elemento en la lista.\n"
		"    2 para eliminar un elemento de la lista.\n"
		"    3 para terminar.\n" );
}// fin de la funcion instrucciones

/* inserta un nuevo valor dentro de la lista en orden */
void insertar( ptrNodoLista *ptrS , char valor ){

	ptrNodoLista ptrNuevo;		/* apuntador a un nuevo nodo */
	ptrNodoLista ptrAnterior;	/* apuntador a un nodo previo de la lista */
	ptrNodoLista ptrActual;		/* apuntador al nodo actual de la lista */

	ptrNuevo = malloc( sizeof( NodoLista ) ); /* crea un nodo */

	if( ptrNuevo != NULL ){ 	/* es espacio disponible */
		ptrNuevo->dato = valor; /* coloca el valor en el nodo */
		ptrNuevo->ptrSiguiente = NULL; /* el nodo no se liga a otro */

		ptrAnterior = NULL ;
		ptrActual = *ptrS ;
		
		/* ciclo para localizar la ubicacion correcta en la lista */
		while( ptrActual != NULL && valor > ptrActual->dato ){
			ptrAnterior = ptrActual;				/* entra al ... */
			ptrActual = ptrActual->ptrSiguiente;	/* ... siguiente nodo */
		}// fin while

		/* inserta un nuevo nodo al principio de la lista */
		if( ptrAnterior == NULL ){
			ptrNuevo->ptrSiguiente = *ptrS;
			*ptrS = ptrNuevo;
		}
		else{// inserta un nuevo nodo entre ptrAnterior y ptrActual
			ptrAnterior->ptrSiguiente = ptrNuevo;
			ptrNuevo->ptrSiguiente = ptrActual;
		}
	}
	else{
		printf("No se inserto %c. No hay memoria disponible.\n", valor );
	}

}// fin de la funcion insertar

/* Elimina un elemento de la lista */
char eliminar( ptrNodoLista *ptrS , char valor ){

	ptrNodoLista ptrAnterior;	/* apuntador a un nodo previo de la lista */
	ptrNodoLista ptrActual;		/* apuntador al nodo actual de la lista */
	ptrNodoLista tempPtr;		/* apuntador a un nodo temporal */

	/* elimina el primer nodo */
	if( valor == ( *ptrS )->dato ){
		tempPtr = *ptrS;	/* almacena el nodo a eliminar */
		*ptrS = ( *ptrS )->ptrSiguiente;	/* desata el nodo */
		free( tempPtr );	/* libera el nodo desatado */
		return valor ;
	}
	else{
		ptrAnterior = *ptrS;
		ptrActual = ( *ptrS )->ptrSiguiente;

		/* ciclo para localizar la ubicacion correcta en la lista */
		while( ptrActual != NULL && ptrActual->dato != valor ){
			ptrAnterior = ptrActual;			 /* entra al ... */
			ptrActual = ptrActual->ptrSiguiente; /* ... siguiente nodo */
		}// fin while

		/* elimina el nodo de ptrActual */
		if( ptrActual != NULL ){
			tempPtr = ptrActual;
			ptrAnterior->ptrSiguiente = ptrActual->ptrSiguiente;
			free( tempPtr );
			return valor;
		}
	}

	return '\0' ;

}// fin de la funcion eliminar

/* Devuelve 1 si la lista esta vacia, de lo contrario, 0 */
int estaVacia( ptrNodoLista ptrS ){
	return ptrS == NULL ;
}// fin de la funcion estaVacia

/* Imprime la lista */
void imprimeLista( ptrNodoLista ptrActual ){

	/* si la lista esta vacia */
	if( ptrActual == NULL ){
		printf("La lista esta vacia.\n\n");
	}
	else{

		printf("La lista es:\n");

		/* mientras no sea el final de la lista */
		while( ptrActual != NULL ){
			printf("%c -> ", ptrActual->dato );
			ptrActual = ptrActual->ptrSiguiente;
		}// fin while

		printf("NULL\n\n");
	}

}// fin de la funcion imprimeLista
