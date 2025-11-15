/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;
import Modelo.*;
import Vista.*;
import javax.swing.*;
import java.awt.Component;
import javax.swing.JOptionPane;
/**
 *
 * @author Joan
 */
public class JuegoFacade {
   private Juego juego;
    private MainFrame vista;
    
    public JuegoFacade(MainFrame vista) {
        this.juego = Juego.getInstance();
        this.vista = vista;
        juego.setModoJuego(new ModoJuegoNormal());
    }
    
    public void crearNuevoCarton() {
        juego.crearCartonAutomatico();
        Carton nuevoCarton = juego.getCartones().get(juego.getCartones().size() - 1);
        agregarCartonAVista(nuevoCarton);
    }
    
    public int sacarBolaAutomatica() {
        int numero = juego.sacarBolaAutomatica();
        if (numero != -1) {
            actualizarVistaCompleta(numero);
        }
        return numero;
    }
    
    public boolean ingresarBolaManual(int numero) {
        if (numero < 1 || numero > 75) {
            return false;
        }
        
        boolean ingresado = juego.getTombola().ingresarBola(numero);
        if (ingresado) {
            marcarNumeroEnTodo(numero);
            actualizarVistaCompleta(numero);
        }
        return ingresado;
    }
    
    public void cambiarModoJuego(ModoJuego nuevoModo, String nombreModo) {
        juego.setModoJuego(nuevoModo);
        vista.getBtnModoJuego().setText("Modo: " + nombreModo);
    }
    
    public void reiniciarJuego() {
        juego.reiniciarJuego();
        vista.actualizarUltimoNumero(0);
        actualizarTodosLosCartones();
    }
    
    public void eliminarCarton(Carton carton) {
    juego.getCartones().remove(carton);
}
    
    public boolean hayGanador() {
        return juego.getCartonGanador() != null;
    }
    
    public String obtenerIdGanador() {
        return juego.getCartonGanador() != null ? 
               juego.getCartonGanador().getId() : "";
    }
    
    public Tombola getTombola() {
        return juego.getTombola();
    }
    
    public int getBolasRestantes() {
        return juego.getTombola().getBolasDisponibles();
    }
    
    
    private void agregarCartonAVista(Carton carton) {
        CartonPanel panel = new CartonPanel();
    panel.inicializarCarton(carton);
    
    
    panel.getBtnCerrar().addActionListener(e -> {
        int confirmacion = JOptionPane.showConfirmDialog(
            vista,
            "¿Seguro que deseas eliminar el cartón " + carton.getId() + "?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            eliminarCarton(carton);
            vista.getPanelCentral().remove(panel);
            vista.getPanelCentral().revalidate();
            vista.getPanelCentral().repaint();
        }
    });
    
    vista.getPanelCentral().add(panel);
    vista.getPanelCentral().revalidate();
    vista.getPanelCentral().repaint();
     
    }
    
    
    private void marcarNumeroEnTodo(int numero) {
        for (Carton carton : juego.getCartones()) {
            carton.marcarNumero(numero);
        }
        juego.getTablero().marcarNumero(numero);
    }
    
    private void actualizarVistaCompleta(int numero) {
        vista.actualizarUltimoNumero(numero);
        actualizarTodosLosCartones();
    }
    
    private void actualizarTodosLosCartones() {
        for (int i = 0; i < vista.getPanelCentral().getComponentCount(); i++) {
            Component comp = vista.getPanelCentral().getComponent(i);
            if (comp instanceof CartonPanel) {
                CartonPanel panel = (CartonPanel) comp;
                panel.actualizarMarcas();
                
                if (juego.getCartonGanador() != null && 
                    panel.getCarton().getId().equals(juego.getCartonGanador().getId())) {
                    panel.resaltarGanador();
                }
            }
        }
    }  
}
