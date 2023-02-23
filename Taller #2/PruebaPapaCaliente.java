// PruebaPapaCaliente.java

// programa para simular el juego de la Papa Caliente en una cola
// simplemente enlazada.

// realizado por: 
// Jesus Morales C.I 22.376.391
// Leonardo Cueva C.I 25.342.366

import java.util.Scanner;
import java.util.Random;

public class PruebaPapaCaliente{
    
    public static void main( String[] args ){
        
        Cola cola = new Cola();
        Scanner entrada = new Scanner(System.in);
        Random numeroAzar = new Random( 1000 );
        
        System.out.print("Ingrese la cantidad de personas que van a jugar: ");
        int personas = entrada.nextInt();
        int c = 1;
        
        // se inserta cada persona en la cola
        while( personas > 0 ){
            cola.insertar( c++ );
            personas--;
        }
        
        int personasJugando = cola.obtenerCantidadDePersonas();
        
        // comienza el juego
        while( personasJugando > 1 ){
            
            int papa = numeroAzar.nextInt( cola.obtenerCantidadDePersonas() * 3 );
            
            System.out.println("La papa exploto en la persona -> " + 
                    cola.papaCaliente(papa));
            
            cola.imprimirCola();
            
            personasJugando = cola.obtenerCantidadDePersonas();
            
        }
        
        System.out.println("El ganador del juego es la persona -> " + cola.papaCaliente(1));
        
    }

}// fin de la clase PruebaPapaCaliente