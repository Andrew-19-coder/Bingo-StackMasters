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
            return new Carton(id);
        } else {
            throw new IllegalArgumentException("Tipo de cartón no válido: " + tipo);
        }
    }
}
