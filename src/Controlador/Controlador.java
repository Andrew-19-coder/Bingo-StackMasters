/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Controlador.JuegoFacade;
import Modelo.*;
import Vista.*;
import Vista.MainFrame;
import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.JOptionPane;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JDialog;

/**
 *
 * @author Joan
 */
public class Controlador {

    private JuegoFacade facade;
    private MainFrame vista;
    private Tombola tombola;
    private boolean modoManual = false;
    private int modoActual = 0;

    private String[] nombresModos = {"Normal", "Cuatro Esquinas", "Cartón Lleno"};
    private TableroPanel tablero;
    private Juego juego;

    public Controlador() {
        this.tablero = new TableroPanel();
        this.juego = Juego.getInstance();
        this.vista = new MainFrame();
        juego.setModoJuego(new ModoJuegoNormal());
        this.facade = new JuegoFacade(vista);

        inicializarEventos();
        vista.setVisible(true);
        inicializarTombola();
        mostrarTombola();
        tablero.setVisible(true);
    }

    private void inicializarTombola() {
        vista.getPanelTombola().setTombola(facade.getTombola());
         vista.getPanelTombola().configurarEventosConFacade(
        () -> {
            int ultimoNum = facade.getTombola().getUltimoNumeroCantado();
            
            juego.marcarNumero(ultimoNum);
            actualizarCartonesVista();
            vista.getLblUltimoNumero().setText("Último número: " + obtenerLetraBingo(ultimoNum) + "-" + ultimoNum);
            vista.getLblUltimoNumero().setFont(new Font("Segoe UI", Font.BOLD, 36));
            actualizarCartonesVista();
            
            if (facade.hayGanador()) {
                JOptionPane.showMessageDialog(vista, 
                    "¡GANADOR! " + facade.obtenerIdGanador(),
                    "¡BINGO!",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        },
        () -> {
           int ultimoNum = facade.getTombola().getUltimoNumeroCantado();
             juego.marcarNumero(ultimoNum);
             actualizarCartonesVista();
           
            vista.getLblUltimoNumero().setText("Último número: " + obtenerLetraBingo(ultimoNum) + "-" + ultimoNum);          
            vista.getLblUltimoNumero().setFont(new Font("Segoe UI", Font.BOLD, 36));
            actualizarCartonesVista();
            
            if (facade.hayGanador()) {
                JOptionPane.showMessageDialog(vista, 
                    "¡GANADOR! " + facade.obtenerIdGanador(),
                    "¡BINGO!",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        }
    );
    }

    private void mostrarTombola() {
        JDialog dialogTombola = new JDialog(vista, "Tombola", false);
        dialogTombola.setLayout(new BorderLayout());
        dialogTombola.add(vista.getPanelTombola());
        dialogTombola.pack();
        dialogTombola.setLocationRelativeTo(vista);
        dialogTombola.setVisible(true);
    }

    private void inicializarEventos() {
        vista.getBtnCrearCarton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                facade.crearNuevoCarton();
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
        vista.getPanelTombola () 
        .actualizarInterfaz();

        String letra = obtenerLetraBingo(numero);
        vista.getLblUltimoNumero().setText("Último número: " + letra + "-" + numero);
        vista.getLblUltimoNumero().setFont(new Font("Segoe UI", Font.BOLD, 36));

        juego.marcarNumero(numero);

        actualizarCartonesVista();

        if (juego.getCartonGanador() != null) {
            JOptionPane.showMessageDialog(vista,
                    "¡GANADOR! " + juego.getCartonGanador().getId(),
                    "¡Bingo!",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private String obtenerLetraBingo(int numero) {
        if (numero <= 15) {
            return "B";
        }
        if (numero <= 30) {
            return "I";
        }
        if (numero <= 45) {
            return "N";
        }
        if (numero <= 60) {
            return "G";
        }
        return "O";
    }

    private void actualizarCartonesVista() {
        System.out.println("Actualizando cartones en vista...");

        for (int i = 0; i < vista.getPanelCentral().getComponentCount(); i++) {
            Component comp = vista.getPanelCentral().getComponent(i);

            if (comp instanceof CartonPanel) {
                CartonPanel panel = (CartonPanel) comp;
                System.out.println("Actualizando CartonPanel: " + panel.getCarton().getId());
                panel.actualizarMarcas();

                // Si es el ganador, resaltarlo
                if (juego.getCartonGanador() != null
                        && panel.getCarton().getId().equals(juego.getCartonGanador().getId())) {
                    panel.resaltarGanador();
                }
            }
        }
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
         vista.getPanelTombola().reiniciar();
        JOptionPane.showMessageDialog(vista, "Juego reiniciado");
    }
}
