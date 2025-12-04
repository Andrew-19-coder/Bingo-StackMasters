/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Controladores.JuegoFacade;
import Modelo.Juego;
import Modelo.ObservadorBingo;
import Vista.MainFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author itsth
 */
public class ObservadorVerificarGanador implements ObservadorBingo{
    private Juego juego;
    private JuegoFacade facade;
    private MainFrame vista;
    
    public ObservadorVerificarGanador(Juego juego, JuegoFacade facade, MainFrame vista) {
        this.juego = juego;
        this.facade = facade;
        this.vista = vista;
    }
    
    @Override
    public void onNumeroCantado(int numero) {
        juego.verificarGanadores();
        
        if (facade.hayGanador()) {
            String tipoJugada = facade.getTipoJugadaGanadora();
            JOptionPane.showMessageDialog(vista,
                "¡GANADOR! " + facade.obtenerIdGanador() + "\n" +
                "Jugada: " + tipoJugada,
                "¡BINGO!",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
    

