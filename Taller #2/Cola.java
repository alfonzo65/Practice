// Cola.java

// realizado por: 
// Jesus Morales C.I 22.376.391
// Leonardo Cueva C.I 25.342.366

public class Cola {
    
    Nodo primero;
    Nodo ultimo;
    private int cantidadDePersonas;
	
    public Cola(){
        cantidadDePersonas = 0;
		primero = null;
        ultimo = null;
    }
    
    public void insertar( int numero ){
        
        Nodo nuevo = new Nodo( numero );
        
        if( estaVacia() ){
            primero = nuevo;
            ultimo = nuevo;
            cantidadDePersonas++;
        }else{
            ultimo.sig = nuevo;
            ultimo = nuevo;
            ultimo.sig = primero;
            cantidadDePersonas++;
        }
        
    }// fin del metodo insertar

    public int papaCaliente( int papa ){
        
        Nodo actual;
        Nodo anterior;
        Nodo temp;
        
        if( estaVacia() )
            return -1;
        
        if( primero == ultimo ){
            temp = primero;
            primero = null;
            ultimo = null;
            return temp.obtenerNumero();
        }
        
        actual = primero;
        anterior = null;
        
        while( papa > 0 ){
            anterior = actual;
            actual = actual.sig;
            papa--;
        }
        
        if( cantidadDePersonas == 2 ){
            
            if( actual == primero ){

                temp = actual;
                primero = ultimo;
                cantidadDePersonas--;
                return temp.obtenerNumero();

            }else{ // de lo contrario apunta al ultimo

                temp = actual;
                ultimo = primero;
                cantidadDePersonas--;
                return temp.obtenerNumero();
            }
        }
        
        if( actual == primero ){

            temp = primero;
            primero = primero.sig;
            ultimo.sig = primero;
            cantidadDePersonas--;
            return temp.obtenerNumero();

        }// fin if
        
        if( actual == ultimo ){

            temp = ultimo;
            ultimo = anterior;
            ultimo.sig = primero;
            cantidadDePersonas--;
            return temp.obtenerNumero();

        }// fin if

        // si no es ni primero ni ultimo
        temp = actual;
        actual = actual.sig;
        anterior.sig = actual;
        cantidadDePersonas--;
        return temp.obtenerNumero();
        
    }// fin dle metodo papaCaliente
    
    public void imprimirCola(){
        
        if( estaVacia() )
            System.out.println("La cola esta Vacia!");
        else{
            
            Nodo temp = primero;
            
            System.out.print("Cola: ");
            
            while( temp != ultimo ){
                
                System.out.printf("%d -> ", temp.obtenerNumero() );
                temp = temp.sig;
                
                if( temp == ultimo ){
                    System.out.printf("%d\n", temp.obtenerNumero() );
                    break;
                }
                
            }// fin while
            
            if( primero == ultimo && cantidadDePersonas == 1 )
                System.out.printf("%d\n", temp.obtenerNumero() );
            
        }// fin else
         
    }// fin del metodo imprimirCola
    
    public boolean estaVacia(){
        return primero == null;
    }
    
    public int obtenerCantidadDePersonas(){
        return cantidadDePersonas;
    }
    
}// fin de la clase Cola