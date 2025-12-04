/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;
import Modelo.*;
import Vista.*;
import javax.swing.*;
import java.awt.event.*;

public class ControladorCarton {
    private JuegoFacade facade;
    private MainFrame vista;
    private Juego juego;
    
    public ControladorCarton(JuegoFacade facade, MainFrame vista, Juego juego) {
        this.facade = facade;
        this.vista = vista;
        this.juego = juego;
    }
    
    public void inicializarEventos() {
        vista.getBtnCrearCarton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarOpcionesCrearCarton();
            }
        });
    }
    
    private void mostrarOpcionesCrearCarton() {
        String[] opciones = {"Automático", "Manual"};
        
        int seleccion = JOptionPane.showOptionDialog(
            vista,
            "¿Cómo deseas crear el cartón?",
            "Crear Cartón",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[0]
        );
        
        if (seleccion == 0) {
            facade.crearNuevoCarton();
        } else if (seleccion == 1) {
            crearCartonManual();
        }
    }
    
    private void crearCartonManual() {
        CartonPanel cartonPanel = facade.crearCartonManual();
        
        JOptionPane.showMessageDialog(vista,
            "Ingresa los 24 números del cartón.\n\n" +
            "Columna B: 1-15\n" +
            "Columna I: 16-30\n" +
            "Columna N: 31-45\n" +
            "Columna G: 46-60\n" +
            "Columna O: 61-75",
            "Cartón Manual",
            JOptionPane.INFORMATION_MESSAGE);
        
        llenarCartonManualmente(cartonPanel);
    }
    
    private void llenarCartonManualmente(CartonPanel cartonPanel) {
        int numerosIngresados = 0;
        
        while (numerosIngresados < 24) {
            String input = JOptionPane.showInputDialog(vista,
                "Número " + (numerosIngresados + 1) + " de 24\n" +
                "Ingresa el número:",
                "Llenar Cartón",
                JOptionPane.QUESTION_MESSAGE);
            
            if (input == null) {
                int confirmar = JOptionPane.showConfirmDialog(vista,
                    "¿Cancelar y eliminar este cartón?",
                    "Cancelar",
                    JOptionPane.YES_NO_OPTION);
                
                if (confirmar == JOptionPane.YES_OPTION) {
                    vista.getPanelCentral().remove(cartonPanel);
                    juego.getCartones().remove(cartonPanel.getCarton());
                    vista.getPanelCentral().revalidate();
                    vista.getPanelCentral().repaint();
                    return;
                }
                continue;
            }
            
            try {
                int numero = Integer.parseInt(input.trim());
                
                if (cartonPanel.agregarNumero(numero)) {
                    numerosIngresados++;
                }
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista,
                    "Número inválido",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
        
        JOptionPane.showMessageDialog(vista,
            "¡Cartón completado!",
            "Éxito",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void actualizarCartonesVista() {
        for (int i = 0; i < vista.getPanelCentral().getComponentCount(); i++) {
            java.awt.Component comp = vista.getPanelCentral().getComponent(i);
            
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
}
