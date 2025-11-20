/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author Joan
 */
public class Tombola {

    private ArrayList<Integer> bolasDisponibles;
    private ArrayList<Integer> bolasCantadas;
    private int ultimoNumeroCantado;
    private Random random;
    private List<ObservadorBingo> observadores;

    public Tombola() {
        this.bolasDisponibles = new ArrayList<>();
        this.bolasCantadas = new ArrayList<>();
        this.random = new Random();
        this.observadores = new ArrayList<>();
        reiniciar();
    }

    public void reiniciar() {
        bolasDisponibles.clear();
        bolasCantadas.clear();
        ultimoNumeroCantado = 0;
        for (int i = 1; i <= 75; i++) {
            bolasDisponibles.add(i);
        }
        Collections.shuffle(bolasDisponibles, random);
    }

    public int sacarBola() {
        if (Juego.getInstance().isJuegoTerminado()) {
            return -1;
        }

        if (bolasDisponibles.isEmpty()) {
            return -1;
        }

        ultimoNumeroCantado = bolasDisponibles.remove(0);
        bolasCantadas.add(ultimoNumeroCantado);
        notificarObservadores(ultimoNumeroCantado);
        return ultimoNumeroCantado;
    }

    public boolean ingresarBola(int numero) {
        if (numero < 1 || numero > 75 || !bolasDisponibles.contains(numero)) {
            return false;
        }

        bolasDisponibles.remove(Integer.valueOf(numero));
        bolasCantadas.add(numero);
        ultimoNumeroCantado = numero;
        notificarObservadores(numero);
        return true;
    }

    public boolean validarNumeroParaCarton(int numero) {
        return numero >= 1 && numero <= 75;
    }

    public int getUltimoNumeroCantado() {
        return ultimoNumeroCantado;
    }

    public void devolverBola(int numero) {
        if (numero >= 1 && numero <= 75) {
            bolasCantadas.remove(Integer.valueOf(numero));
            if (!bolasDisponibles.contains(numero)) {
                bolasDisponibles.add(numero);
                Collections.shuffle(bolasDisponibles, random);
            }
        }
    }

    public int numerosCantados() {
        return bolasCantadas.size();
    }

    public void agregarObservador(ObservadorBingo observador) {
        if (!observadores.contains(observador)) {
            observadores.add(observador);
        }
    }

    public void removerObservador(ObservadorBingo observador) {
        observadores.remove(observador);
    }

    private void notificarObservadores(int numero) {
        for (ObservadorBingo observador : observadores) {
            observador.onNumeroCantado(numero);
        }
    }

    public ArrayList<Integer> getBolasDisponibles() {
        return bolasDisponibles;
    }
}
