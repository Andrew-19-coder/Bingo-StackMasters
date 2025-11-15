/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;
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
    private Juego juego;
    private MainFrame vista;
    private Tombola tombola;
    private boolean modoManual = false;
    private int modoActual = 0; 
    private String[] nombresModos = {"Normal", "Cuatro Esquinas", "Cartón Lleno"};
    
    public Controlador() {
        this.juego = new Juego();
        this.vista = new MainFrame();
        this.tombola = new Tombola();
        juego.setModoJuego(new ModoJuegoNormal());
        
        inicializarEventos();
        vista.setVisible(true);
        inicializarTombola();
        mostrarTombola();
    }
    
    private void inicializarTombola() {
        vista.getPanelTombola().setTombola(tombola);
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
                crearCarton();
            }
        });
        
        
        vista.getBtnSacarBola().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sacarBola();
            }
        });
        
       
        vista.getBtnReiniciar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reiniciarJuego();
            }
        });
        
       
        vista.getBtnModoJuego().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarModoJuego();
            }
        });
    }
    
    private void crearCarton() {
        System.out.println("Botón presionado");
        juego.crearCartonAutomatico();
         System.out.println("2. Cartón creado en modelo");
       
        Carton nuevoCarton = juego.getCartones().get(juego.getCartones().size() - 1);
          System.out.println("3. Cartón obtenido: " + nuevoCarton.getId());
       
        CartonPanel panelCarton = new CartonPanel();
          System.out.println("4. CartonPanel creado");
        panelCarton.inicializarCarton(nuevoCarton);
        System.out.println("5. Cartón inicializado");
       
        vista.getPanelCentral().add(panelCarton);
         System.out.println("6. Panel agregado");
        vista.getPanelCentral().revalidate();
        vista.getPanelCentral().repaint();
         System.out.println("7. Panel actualizado");
    }
    
    private void cambiarModoJuego() {
    modoActual = (modoActual + 1) % 3; // Cicla entre 0, 1, 2
    
    ModoJuego modo;
    switch(modoActual) {
        case 0:
            modo = new ModoJuegoNormal();
            break;
        case 1:
            modo = new ModoJuegoCuatroEsquinas();
            break;
        case 2:
            modo = new CartonLleno();
            break;
        default:
            modo = new ModoJuegoNormal();
    }
    
    juego.setModoJuego(modo);
    vista.getBtnModoJuego().setText("Modo: " + nombresModos[modoActual]);
    JOptionPane.showMessageDialog(vista, "Modo cambiado a: " + nombresModos[modoActual]);
}
    
    
    
    
    private void sacarBola() {
        int numero = tombola.sacarBola();
    
    if (numero == -1) {
        JOptionPane.showMessageDialog(vista, "No hay más bolas disponibles");
        return;
    }
    
    // Actualizar la interfaz de la tombola
    vista.getPanelTombola().actualizarInterfaz();
    
    // Actualizar vista principal
    String letra = obtenerLetraBingo(numero);
    vista.getLblUltimoNumero().setText("Último número: " + letra + "-" + numero);
    vista.getLblUltimoNumero().setFont(new Font("Segoe UI", Font.BOLD, 36));
    
    // ⭐ MARCAR EN LOS CARTONES DEL JUEGO (MODELO)
    juego.marcarNumero(numero);
    
    // ⭐ ACTUALIZAR TODOS LOS CARTONES VISUALES
    actualizarCartonesVista();
    
    // Verificar ganador
    if (juego.getCartonGanador() != null) {
        JOptionPane.showMessageDialog(vista, 
            "¡GANADOR! " + juego.getCartonGanador().getId(),
            "¡Bingo!",
            JOptionPane.INFORMATION_MESSAGE);
    }
    }
    
     private String obtenerLetraBingo(int numero) {
        if (numero <= 15) return "B";
        if (numero <= 30) return "I";
        if (numero <= 45) return "N";
        if (numero <= 60) return "G";
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
            if (juego.getCartonGanador() != null && 
                panel.getCarton().getId().equals(juego.getCartonGanador().getId())) {
                panel.resaltarGanador();
                }
            }
        }
    }
    
    private void reiniciarJuego() {
        // Reiniciar el MODELO
        juego.reiniciarJuego();
        
        // Actualizar vista
        vista.actualizarUltimoNumero(0);
        actualizarCartonesVista();
    }
    
}