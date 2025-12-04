/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Joan
 */
public class CartonFactory {
     public static Carton crearCarton(String id, String tipo) {
        if (tipo.equalsIgnoreCase("automatico")) {
            return new Carton(id);
        } else if (tipo.equalsIgnoreCase("manual")) {
            int[][] numerosVacios = new int[5][5];
            return new Carton(id, numerosVacios);
        } else {
            throw new IllegalArgumentException("Tipo de cartón no válido: " + tipo);
        }
    }
}
