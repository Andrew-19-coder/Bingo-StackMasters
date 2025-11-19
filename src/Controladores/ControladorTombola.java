/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;
import Modelo.*;
import Vista.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Font;

public class ControladorTombola {
    private JuegoFacade facade;
    private MainFrame vista;
    private Juego juego;
    private ControladorCarton controladorCarton;
    private TableroPanel tableroPanel;
    
    public ControladorTombola(JuegoFacade facade, MainFrame vista, Juego juego, 
                              ControladorCarton controladorCarton, TableroPanel tableroPanel) {
        this.facade = facade;
        this.vista = vista;
        this.juego = juego;
        this.controladorCarton = controladorCarton;
        this.tableroPanel = tableroPanel;
    }
    
    public void inicializarTombola() {
        vista.getPanelTombola().setTombola(facade.getTombola());
        
        vista.getPanelTombola().getBtnIngresar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ingresarNumeroManual();
            }
        });
        
        vista.getPanelTombola().configurarEventosConFacade(
            () -> {
                int ultimoNum = facade.getTombola().getUltimoNumeroCantado();
                juego.marcarNumero(ultimoNum);
                controladorCarton.actualizarCartonesVista();
                
                if (tableroPanel != null) {
                    tableroPanel.marcarNumero(ultimoNum);
                }
                
                vista.getLblUltimoNumero().setText("Último número: " + obtenerLetraBingo(ultimoNum) + "-" + ultimoNum);
                vista.getLblUltimoNumero().setFont(new Font("Segoe UI", Font.BOLD, 36));
                
                if (facade.hayGanador()) {
                    String tipoJugada = facade.getTipoJugadaGanadora();
                    JOptionPane.showMessageDialog(vista,
                        "¡GANADOR! " + facade.obtenerIdGanador() + "\n" +
                        "Jugada: " + tipoJugada,
                        "¡BINGO!",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            },
            () -> {}
        );
        
        configurarCambioModo();
        vista.getPanelTombola().configurarModoAutomatico();
    }
    
    private void configurarCambioModo() {
        vista.getPanelTombola().getCmbModoJuego().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int indice = vista.getPanelTombola().getCmbModoJuego().getSelectedIndex();
                
                System.out.println("ComboBox cambió a índice: " + indice);
                
                if (indice == 0) {
                    System.out.println("Activando modo automático");
                    vista.getPanelTombola().configurarModoAutomatico();
                } else if (indice == 1) {
                    System.out.println("Activando modo manual");
                    vista.getPanelTombola().configurarModoManual();
                }
            }
        });
    }
    
    private void ingresarNumeroManual() {
        String textoNumero = vista.getPanelTombola().getTxtManual().getText().trim();
        
        if (textoNumero.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Ingresa un número");
            return;
        }
        
        int numero;
        try {
            numero = Integer.parseInt(textoNumero);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "Número inválido");
            return;
        }
        
        if (numero < 1 || numero > 75) {
            JOptionPane.showMessageDialog(vista, "El número debe estar entre 1 y 75");
            return;
        }
        
        if (juego.getTombola().ingresarBola(numero)) {
            juego.marcarNumero(numero);
            controladorCarton.actualizarCartonesVista();
            vista.getLblUltimoNumero().setText("Último número: " + obtenerLetraBingo(numero) + "-" + numero);
            vista.getLblUltimoNumero().setFont(new Font("Segoe UI", Font.BOLD, 36));
            vista.getPanelTombola().actualizarInterfaz();
            vista.getPanelTombola().getTxtManual().setText("");
            
            if (tableroPanel != null) {
                tableroPanel.marcarNumero(numero);
            }
            
            if (facade.hayGanador()) {
                String tipoJugada = facade.getTipoJugadaGanadora();
                JOptionPane.showMessageDialog(vista,
                    "¡GANADOR! " + facade.obtenerIdGanador() + "\n" +
                    "Jugada: " + tipoJugada,
                    "¡BINGO!",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(vista, "Número inválido o ya cantado");
        }
    }
    
    private String obtenerLetraBingo(int numero) {
        if (numero <= 15) return "B";
        if (numero <= 30) return "I";
        if (numero <= 45) return "N";
        if (numero <= 60) return "G";
        return "O";
    }
}
