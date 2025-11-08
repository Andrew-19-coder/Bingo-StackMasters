/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
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

    public Tombola() {
        this.bolasDisponibles = new ArrayList<>();
        this.bolasCantadas = new ArrayList<>();
        this.random = new Random();
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
        if (bolasDisponibles.isEmpty()) {
            return -1; 
        }
        
        ultimoNumeroCantado = bolasDisponibles.remove(0);
        bolasCantadas.add(ultimoNumeroCantado);
        return ultimoNumeroCantado;
    }
    public boolean ingresarBola(int numero) {
        if (numero < 1 || numero > 75 || !bolasDisponibles.contains(numero)) {
            return false;
        }

        bolasDisponibles.remove(Integer.valueOf(numero));
        bolasCantadas.add(numero);
        ultimoNumeroCantado = numero;
        return true;
    }

    public int getUltimoNumeroCantado() {
        return ultimoNumeroCantado;
    }
}
