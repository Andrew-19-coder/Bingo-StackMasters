/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Modelo.Juego;
import Modelo.ObservadorBingo;
import Vista.CartonPanel;
import Vista.MainFrame;
import java.awt.Component;

/**
 *
 * @author itsth
 */
public class ObservadorCartonesVista implements ObservadorBingo {

    private MainFrame vista;
    private Juego juego;
    private JuegoFacade facade;

    public ObservadorCartonesVista(MainFrame vista, Juego juego, JuegoFacade facade) {
        this.vista = vista;
        this.juego = juego;
        this.facade = facade;
    }

    @Override
    public void onNumeroCantado(int numero) {
        actualizarCartonesVista();
    }

    private void actualizarCartonesVista() {
        for (int i = 0; i < vista.getPanelCentral().getComponentCount(); i++) {
            Component comp = vista.getPanelCentral().getComponent(i);

            if (comp instanceof CartonPanel) {
                CartonPanel panel = (CartonPanel) comp;
                panel.actualizarMarcas();

                if (juego.getCartonGanador() != null
                        && panel.getCarton().getId().equals(juego.getCartonGanador().getId())) {
                    panel.resaltarGanador();

                    java.util.ArrayList<int[]> posicionesGanadoras = facade.getPosicionesGanadoras();
                    panel.resaltarJugadaGanadora(posicionesGanadoras);
                }
            }
        }
    }

    private void verificarGanador() {
        if (facade.hayGanador()) {
            String tipoJugada = facade.getTipoJugadaGanadora();
            javax.swing.JOptionPane.showMessageDialog(vista,
                    "¡GANADOR! " + facade.obtenerIdGanador() + "\n"
                    + "Jugada: " + tipoJugada,
                    "¡BINGO!",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
