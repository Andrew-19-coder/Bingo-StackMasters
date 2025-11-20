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
    private ArrayList<int[]> posicionesGanadoras;
    private String tipoJugadaGanadora;
    private static Juego instancia;
    private boolean juegoTerminado;

    private Juego() {
        this.cartones = new ArrayList<>();
        this.tombola = new Tombola();
        this.tablero = new Tablero();
        this.modoActual = null;
        this.cartonGanador = null;
        this.juegoTerminado = false;
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
    posicionesGanadoras = null;
    tipoJugadaGanadora = null;
    juegoTerminado = false;
    
    for (Carton carton : cartones) {
        carton.reiniciarMarcas();
    }
}


    public void verificarGanadores() {
    if (modoActual == null) {
        return;
    }
    for (Carton carton : cartones) {
        if (modoActual.verificarGanador(carton)) {
            this.cartonGanador = carton;
            this.posicionesGanadoras = modoActual.obtenerPosicionesGanadoras(carton);
            this.tipoJugadaGanadora = determinarTipoJugada(posicionesGanadoras);
            this.juegoTerminado = true; 
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

    private String determinarTipoJugada(ArrayList<int[]> posiciones) {
        if (posiciones == null || posiciones.isEmpty()) {
            return "Jugada desconocida";
        }

        int cantidad = posiciones.size();

        if (cantidad == 25) {
            return "Cartón Lleno";
        }

        if (cantidad == 4) {
            return "Cuatro Esquinas";
        }

        if (cantidad == 5) {
            int[] primera = posiciones.get(0);
            int[] segunda = posiciones.get(1);

            if (primera[0] == segunda[0]) {
                return "Línea Horizontal";
            }

            if (primera[1] == segunda[1]) {
                return "Línea Vertical";
            }

            return "Diagonal";
        }

        return "Jugada desconocida";
    }

    public void marcarNumero(int numero) {
        for (Carton carton : cartones) {
            carton.marcarNumero(numero);
        }
        verificarGanadores();
    }

    public void desmarcarNumeroCartones(int numero) {
        for (Carton carton : cartones) {
            carton.desmarcarNumero(numero);
        }
    }

    public void eliminarCarton(String id) {
        cartones.removeIf(carton -> carton.getId().equals(id));
    }

    public ArrayList<int[]> getPosicionesGanadoras() {
        return posicionesGanadoras;
    }

    public ArrayList<Carton> getCartones() {
        return cartones;
    }

    public String getTipoJugadaGanadora() {
        return tipoJugadaGanadora;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public Tombola getTombola() {
        return tombola;
    }

    public boolean isJuegoTerminado() {
        return juegoTerminado;
    }

    public void setJuegoTerminado(boolean terminado) {
        this.juegoTerminado = terminado;

    }

    public Carton getCartonGanador() {
        return cartonGanador;
    }

    public void setCartonGanador(Carton carton) {
        this.cartonGanador = carton;
        if (carton != null) {
            this.juegoTerminado = true;
        }

    }

    public void setPosicionesGanadoras(ArrayList<int[]> posiciones) {
        this.posicionesGanadoras = posiciones;
    }

    public void setTipoJugadaGanadora(String tipo) {
        this.tipoJugadaGanadora = tipo;
    }

}
