/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Joan
 */
public class Tablero {
  private boolean[] numerosSalidos;

    public Tablero() {
        this.numerosSalidos = new boolean[76]; 
    }

    public void marcarNumero(int numero) {
        if (numero >= 1 && numero <= 75) {
            this.numerosSalidos[numero] = true;
        }
    }

    public void reiniciar() {
        this.numerosSalidos = new boolean[76];
    }

    public boolean[] getEstadoNumeros() {
        return numerosSalidos;
    }  
}
