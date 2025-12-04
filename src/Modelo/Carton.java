/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.Random;

/**
 *
 * @author Joan
 */
public class Carton implements ObservadorBingo{

    private String id;
    private int[][] numeros;
    private boolean[][] marcados;
    private Random random;

    public Carton(String id) {
        this.id = id;
        this.numeros = new int[5][5];
        this.marcados = new boolean[5][5];
        this.random = new Random();

        generarNumeros();

        this.marcados[2][2] = true;
    }

    private void generarNumeros() {
        for (int j = 0; j < 5; j++) {
            int min = (j * 15) + 1;
            int max = (j + 1) * 15;

            for (int i = 0; i < 5; i++) {

                if (i == 2 && j == 2) {
                    this.numeros[i][j] = 0;
                    continue;
                }
                int numeroGenerado;
                boolean esRepetido;

                do {
                    esRepetido = false;
                    numeroGenerado = random.nextInt(max - min + 1) + min;

                    for (int k = 0; k < i; k++) {
                        if (this.numeros[k][j] == numeroGenerado) {
                            esRepetido = true;
                            break;
                        }
                    }
                } while (esRepetido);

                this.numeros[i][j] = numeroGenerado;
            }
        }
    }

    public boolean marcarNumero(int numero) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (this.numeros[i][j] == numero) {
                    this.marcados[i][j] = true;
                    return true;
                }
            }
        }
        return false;
    }

    public void reiniciarMarcas() {
        this.marcados = new boolean[5][5];
        this.marcados[2][2] = true;
    }

    public Carton(String id, int[][] numerosManuales) {
        this.id = id;
        this.numeros = numerosManuales;
        this.marcados = new boolean[5][5];
        this.random = new Random();

        this.marcados[2][2] = true;
    }

    public void desmarcarNumero(int numero) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (this.numeros[i][j] == numero) {
                    if (i != 2 || j != 2) {
                        this.marcados[i][j] = false;
                    }
                    return;
                }
            }
        }
    }

    public static boolean validarNumerosManuales(int[][] numeros) {
        if (numeros == null || numeros.length != 5) {
            return false;
        }

        for (int j = 0; j < 5; j++) {
            int min = (j * 15) + 1;
            int max = (j + 1) * 15;
            boolean[] numerosColumna = new boolean[76];

            for (int i = 0; i < 5; i++) {
                if (numeros[i].length != 5) {
                    return false;
                }

                int num = numeros[i][j];

                if (i == 2 && j == 2) {
                    if (num != 0) {
                        return false;
                    }
                    continue;
                }

                if (num < min || num > max) {
                    return false;
                }
                if (numerosColumna[num]) {
                    return false;
                }
                numerosColumna[num] = true;
            }
        }
        return true;
    }

    public int[][] getNumeros() {
        return numeros;
    }

    public void setNumeros(int[][] numeros) {
        this.numeros = numeros;
    }

    public boolean[][] getMarcados() {
        return marcados;
    }

    public String getId() {
        return id;
    }

    @Override
    public void onNumeroCantado(int numero) {
        marcarNumero(numero);
    }
}
