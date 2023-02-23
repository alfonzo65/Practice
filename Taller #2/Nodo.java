// Nodo.java

// realizado por: 
// Jesus Morales C.I 22.376.391
// Leonardo Cueva C.I 25.342.366

public class Nodo {
    
    private int dato; // identificador de la persona
    Nodo sig;
	
    public Nodo( int valor ){
        dato = valor;
        sig = null;
    }

    public int obtenerNumero(){
        return dato;
    }
    
}