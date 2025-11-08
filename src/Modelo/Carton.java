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
public class Carton {
private String id;
private int [][] numeros;  
private boolean [][] marcados; 
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
    
    
    public int[][] getNumeros() {
        return numeros;
    }

    public boolean[][] getMarcados() {
       return marcados; 
    }

    public String getId() {
        return id;
    }
}
