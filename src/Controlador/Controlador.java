/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;
import Controlador.JuegoFacade;
import Modelo.*;
import Vista.*;
import Vista.MainFrame;
import java.awt.event.*;
import javax.swing.JOptionPane;
import java.awt.Component;
/**
 *
 * @author Joan
 */
public class Controlador {
    private JuegoFacade facade;
    private MainFrame vista;
    
    private int modoActual = 0;
    private String[] nombresModos = {"Normal", "Cuatro Esquinas", "Cartón Lleno"};
    
    public Controlador() {
        this.vista = new MainFrame();
        this.facade = new JuegoFacade(vista);
        
        inicializarEventos();
        vista.setVisible(true);
    }
    
    private void inicializarEventos() {
        vista.getBtnCrearCarton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                facade.crearNuevoCarton();
            }
        });
        
        vista.getBtnSacarBola().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sacarBola();
            }
        });
        
        vista.getBtnModoJuego().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
    
    private void sacarBola() {
        int numero = facade.sacarBolaAutomatica();
        
        if (numero == -1) {
            JOptionPane.showMessageDialog(vista, "No hay más bolas disponibles");
            return;
        }
        
        if (facade.hayGanador()) {
            JOptionPane.showMessageDialog(vista, 
                "¡GANADOR! " + facade.obtenerIdGanador(),
                "¡BINGO!",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void cambiarModoJuego() {
        modoActual = (modoActual + 1) % 3;
        
        ModoJuego modo;
        switch(modoActual) {
            case 0:
                modo = new ModoJuegoNormal();
                break;
            case 1:
                modo = new ModoJuegoCuatroEsquinas();
                break;
            case 2:
                modo = new cartonLleno();
                break;
            default:
                modo = new ModoJuegoNormal();
        }
        
        facade.cambiarModoJuego(modo, nombresModos[modoActual]);
        JOptionPane.showMessageDialog(vista, "Modo: " + nombresModos[modoActual]);
    }
    
    private void reiniciarJuego() {
        facade.reiniciarJuego();
        JOptionPane.showMessageDialog(vista, "Juego reiniciado");
    }
}