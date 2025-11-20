/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Modelo.ObservadorBingo;
import Vista.MainFrame;
import java.awt.Font;

/**
 *
 * @author itsth
 */
public class ObservadorUltimoNumero implements ObservadorBingo{
    private MainFrame vista;
    
    public ObservadorUltimoNumero(MainFrame vista) {
        this.vista = vista;
    }

    @Override
    public void onNumeroCantado(int numero) {
        String letra = obtenerLetraBingo(numero);
        vista.getLblUltimoNumero().setText("Último número: " + letra + "-" + numero);
        vista.getLblUltimoNumero().setFont(new Font("Segoe UI", Font.BOLD, 36));
    }
    
    private String obtenerLetraBingo(int numero) {
        if (numero >= 1 && numero <= 15) return "B";
        if (numero >= 16 && numero <= 30) return "I";
        if (numero >= 31 && numero <= 45) return "N";
        if (numero >= 46 && numero <= 60) return "G";
        if (numero >= 61 && numero <= 75) return "O";
        return "";
    }
}
