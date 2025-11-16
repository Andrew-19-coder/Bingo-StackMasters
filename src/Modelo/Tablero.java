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
   private boolean[] numerosMarcados;

    public Tablero() {
        this.numerosSalidos = new boolean[76];
         this.numerosMarcados = new boolean[75];
    }

    public void marcarNumero(int numero) {
        if (numero >= 1 && numero <= 75) {
            this.numerosSalidos[numero] = true;
        }
    }

    public void desmarcarNumero(int numero) {
    if (numero >= 1 && numero <= 75) {
        numerosMarcados[numero - 1] = false;
    }
}
    
    public void reiniciar() {
        this.numerosSalidos = new boolean[76];
    }

    public boolean[] getEstadoNumeros() {
        return numerosSalidos;
    }  
}
