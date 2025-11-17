/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Modelo;

import java.util.ArrayList;

/**
 *
 * @author Joan
 */
public interface ModoJuego {
  public boolean verificarGanador(Carton carton);  
   ArrayList<int[]> obtenerPosicionesGanadoras(Carton carton);
}
