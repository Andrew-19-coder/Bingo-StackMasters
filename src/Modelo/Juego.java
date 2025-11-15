/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.ArrayList;

/**
 *
 * @author itsth
 */
public class Juego {
   private ArrayList<Carton> cartones;
    private Tombola tombola;
    private Tablero tablero;
    private ModoJuego modoActual; 
    private Carton cartonGanador; 
    private static Juego instancia;
    
    private Juego() {
        this.cartones = new ArrayList<>();
        this.tombola = new Tombola();
        this.tablero = new Tablero();
        this.modoActual = null; 
        this.cartonGanador = null;
    }

    public static Juego getInstance() {
        if (instancia == null) {
            instancia = new Juego();
        } 
        return instancia;
    }
    
    
    public void crearCartonAutomatico() {
        String id = "Carton-" + (cartones.size() + 1);
        Carton nuevoCarton = CartonFactory.crearCarton(id, "automatico");
        this.cartones.add(nuevoCarton);
    }

    public void setModoJuego(ModoJuego modo) {
        this.modoActual = modo;
    }

    public int sacarBolaAutomatica() {
        int numeroCantado = tombola.sacarBola();
        
        if (numeroCantado != -1) {
            tablero.marcarNumero(numeroCantado);
            for (Carton carton : cartones) {
                carton.marcarNumero(numeroCantado);
            }
            verificarGanadores();
        }
        return numeroCantado;
    }

    public void reiniciarJuego() {
        tombola.reiniciar();
        tablero.reiniciar();
        cartonGanador = null;
        for (Carton carton : cartones) {
            carton.reiniciarMarcas();
        }
    }

    private void verificarGanadores() {
        if (modoActual == null) {
            return; 
        }

        for (Carton carton : cartones) {
            if (modoActual.verificarGanador(carton)) {
                this.cartonGanador = carton;
                break; 
            }
        }
    }

    public void crearCartonManual() {
    String id = "Carton-" + (cartones.size() + 1);
    Carton nuevoCarton = CartonFactory.crearCarton(id, "manual");
    this.cartones.add(nuevoCarton);
}

    public boolean ingresarBolaManual(int numero) {
        boolean exito = tombola.ingresarBola(numero);
        
        if (exito) {
            tablero.marcarNumero(numero);
            for (Carton carton : cartones) {
                carton.marcarNumero(numero);
            }
            verificarGanadores();
        }
        return exito;
    }

    public void desmarcarNumeroCartones(int numero) {
        for (Carton carton : cartones) {
            carton.desmarcarNumero(numero);
        }
    }

    public void eliminarCarton(String id) {
        cartones.removeIf(carton -> carton.getId().equals(id));
    }
    
    
    public ArrayList<Carton> getCartones() {
        return cartones;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public Tombola getTombola() {
        return tombola;
    }

    public Carton getCartonGanador() {
        return cartonGanador;
    }
} 

