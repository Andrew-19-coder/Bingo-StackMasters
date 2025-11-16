/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author oscar
 */
public class CartonLlenoValidacion extends ModoJuegoDecorator {

    int minimoNumeros;

    public CartonLlenoValidacion(ModoJuego modo, int minimo) {
        super(modo);
        this.minimoNumeros = minimo;
    }

    @Override
    public boolean verificarGanador(Carton carton) {
        boolean[][] marcados = carton.getMarcados();
        int contador = 0;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (marcados[i][j]) {
                    contador++;
                }
            }
        }

        if (contador < minimoNumeros) {
            return false;
        }

        return super.verificarGanador(carton);
    }
}
