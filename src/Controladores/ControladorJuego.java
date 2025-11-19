/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;
import Modelo.*;
import Vista.*;
import javax.swing.*;
import java.awt.event.*;

public class ControladorJuego {
    private JuegoFacade facade;
    private MainFrame vista;
    private Juego juego;
    private TableroPanel tableroPanel;
    private int modoActual = 0;
    private String[] nombresModos = {"Normal", "Cuatro Esquinas", "Cartón Lleno"};
    
    public ControladorJuego(JuegoFacade facade, MainFrame vista, Juego juego, TableroPanel tableroPanel) {
        this.facade = facade;
        this.vista = vista;
        this.juego = juego;
        this.tableroPanel = tableroPanel;
    }
    
    public void inicializarEventos() {
        vista.getBtnModoJuego().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (juego.getTombola().numerosCantados() > 0) {
                    JOptionPane.showMessageDialog(vista,
                        "No se puede cambiar el modo de juego durante la partida",
                        "Acción no permitida",
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
                cambiarModoJuego();
            }
        });
        
        vista.getBtnReiniciar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reiniciarJuego();
            }
        });
    }
    
    private void cambiarModoJuego() {
        modoActual = (modoActual + 1) % 3;
        
        ModoJuego modo;
        switch (modoActual) {
            case 0:
                modo = new ModoJuegoNormal();
                break;
            case 1:
                modo = new ModoJuegoCuatroEsquinas();
                break;
            case 2:
                modo = new CartonLlenoValidacion(new cartonLleno(), 20);
                break;
            default:
                modo = new ModoJuegoNormal();
        }
        
        facade.cambiarModoJuego(modo, nombresModos[modoActual]);
        
        if (modoActual == 2) {
            JOptionPane.showMessageDialog(vista,
                "Modo: " + nombresModos[modoActual] + "\n" +
                "(Requiere mínimo 20 números marcados)",
                "Modo de Juego",
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(vista, "Modo: " + nombresModos[modoActual]);
        }
    }
    
    private void reiniciarJuego() {
        facade.reiniciarJuego();
        vista.getPanelTombola().reiniciar();
        if (tableroPanel != null) {
            tableroPanel.reiniciar();
        }
        
        JOptionPane.showMessageDialog(vista, "Juego reiniciado");
    }
}
