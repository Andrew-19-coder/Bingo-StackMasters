/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.ArrayList;

/**
 *
 * @author oscar
 */
public abstract class ModoJuegoDecorator implements ModoJuego {
   protected ModoJuego modoDecorado;
    
    public ModoJuegoDecorator(ModoJuego modo) {
        this.modoDecorado = modo;
    }
    
    @Override
    public boolean verificarGanador(Carton carton) {
        return modoDecorado.verificarGanador(carton);
    }
    
    @Override
    public ArrayList<int[]> obtenerPosicionesGanadoras(Carton carton) {
        return modoDecorado.obtenerPosicionesGanadoras(carton);
    }
}
