/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;
import Modelo.*;
import Vista.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.BorderLayout;

public class ControladorTablero {
    private JuegoFacade facade;
    private MainFrame vista;
    private Juego juego;
    private JDialog dialogTablero;
    private TableroPanel tableroPanel;
    private ControladorCarton controladorCarton;
    
    public ControladorTablero(JuegoFacade facade, MainFrame vista, Juego juego, 
                             TableroPanel tableroPanel, ControladorCarton controladorCarton) {
        this.facade = facade;
        this.vista = vista;
        this.juego = juego;
        this.tableroPanel = tableroPanel;
        this.controladorCarton = controladorCarton;
    }
    
    public void inicializarEventos() {
        vista.getBtnAbrirTablero().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirTablero();
            }
        });
        
        vista.getBtnDesmarcar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                desmarcarNumero();
            }
        });
    }
    
    private void abrirTablero() {
        if (dialogTablero == null) {
            dialogTablero = new JDialog(vista, "Tablero de Números - Bingo", false);
            
            sincronizarTablero();
            
            dialogTablero.add(tableroPanel);
            dialogTablero.setSize(1050, 400);
            dialogTablero.setLocationRelativeTo(vista);
            dialogTablero.setResizable(false);
        }
        dialogTablero.setVisible(true);
    }
    
    private void sincronizarTablero() {
        if (tableroPanel == null) {
            return;
        }
        
        Tombola tombola = juego.getTombola();
        for (int i = 1; i <= 75; i++) {
            if (!tombola.getBolasDisponibles().contains(i)) {
                tableroPanel.marcarNumero(i);
            }
        }
    }
    
    private void desmarcarNumero() {
        String input = JOptionPane.showInputDialog(vista,
            "Ingresa el número a desmarcar (1-75):",
            "Desmarcar Número",
            JOptionPane.QUESTION_MESSAGE);
        
        if (input == null || input.trim().isEmpty()) {
            return;
        }
        
        try {
            int numero = Integer.parseInt(input.trim());
            
            if (numero < 1 || numero > 75) {
                JOptionPane.showMessageDialog(vista,
                    "El número debe estar entre 1 y 75",
                    "Número Inválido",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            boolean desmarcado = facade.desmarcarNumero(numero);
            
            if (desmarcado) {
                if (facade.hayGanador()) {
                    juego.setCartonGanador(null);
                    juego.setPosicionesGanadoras(null);
                    juego.setTipoJugadaGanadora(null);
                }
                
                controladorCarton.actualizarCartonesVista();
                
                if (tableroPanel != null) {
                    tableroPanel.desmarcarNumero(numero);
                }
                
                JOptionPane.showMessageDialog(vista,
                    "Número " + numero + " desmarcado correctamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(vista,
                    "El número " + numero + " no estaba marcado",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            }
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista,
                "Ingresa un número válido",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public TableroPanel getTableroPanel() {
        return tableroPanel;
    }
}
