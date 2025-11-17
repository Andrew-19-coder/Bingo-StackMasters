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
import java.util.ArrayList;
import javax.swing.JDialog;

/**
 *
 * @author Joan
 */
public class Controlador {

    private JuegoFacade facade;
    private MainFrame vista;
    private Tombola tombola;
    private int modoActual = 0;
    private JDialog dialogTablero;
    private TableroPanel tableroPanel;
    private JDialog dialogTombola;
    private boolean juegoIniciado = false;

    private String[] nombresModos = {"Normal", "Cuatro Esquinas", "Cartón Lleno"};
    private TableroPanel tablero;
    private Juego juego;
    private CartonPanel cartonEnEdicion = null;

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
                    actualizarCartonesVista();

                    if (tableroPanel != null) {
                        tableroPanel.marcarNumero(ultimoNum);
                    }

                    vista.getLblUltimoNumero().setText("Último número: " + obtenerLetraBingo(ultimoNum) + "-" + ultimoNum);
                    vista.getLblUltimoNumero().setFont(new Font("Segoe UI", Font.BOLD, 36));

                    if (facade.hayGanador()) {
                        String tipoJugada = facade.getTipoJugadaGanadora();
                        JOptionPane.showMessageDialog(vista,
                                "¡GANADOR! " + facade.obtenerIdGanador() + "\n"
                                + "Jugada: " + tipoJugada,
                                "¡BINGO!",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                },
                () -> {

                }
        );

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

       
        vista.getPanelTombola().configurarModoAutomatico();
    }

    private void mostrarTombola() {
        dialogTombola = new JDialog(vista, "Tombola", false);
        dialogTombola.setLayout(new BorderLayout());
        dialogTombola.add(vista.getPanelTombola());
        dialogTombola.pack();
        dialogTombola.setLocationRelativeTo(vista);
        dialogTombola.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        dialogTombola.setVisible(true);
    }

    private void inicializarEventos() {
        vista.getBtnCrearCarton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               mostrarOpcionesCrearCarton();
            }
        });

        vista.getBtnAbrirTombola().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirTombola();
            }
        });
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

    

    private void sacarBola() {
        int numero = facade.sacarBolaAutomatica();

        if (numero == -1) {
            JOptionPane.showMessageDialog(vista, "No hay más bolas disponibles");
            return;
        }
        if (facade.hayGanador()) {
            String tipoJugada = facade.getTipoJugadaGanadora();
            JOptionPane.showMessageDialog(vista,
                    "¡GANADOR! " + facade.obtenerIdGanador() + "\n"
                    + "Jugada: " + tipoJugada,
                    "¡BINGO!",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        vista.getPanelTombola()
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

                if (juego.getCartonGanador() != null
                        && panel.getCarton().getId().equals(juego.getCartonGanador().getId())) {
                    panel.resaltarGanador();

                    ArrayList<int[]> posicionesGanadoras = facade.getPosicionesGanadoras();
                    panel.resaltarJugadaGanadora(posicionesGanadoras);
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
                modo = new CartonLlenoValidacion(new cartonLleno(), 20);
                break;
            default:
                modo = new ModoJuegoNormal();
        }

        facade.cambiarModoJuego(modo, nombresModos[modoActual]);

        if (modoActual == 2) {
            JOptionPane.showMessageDialog(vista,
                    "Modo: " + nombresModos[modoActual] + "\n"
                    + "(Requiere mínimo 20 números marcados)",
                    "Modo de Juego",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(vista, "Modo: " + nombresModos[modoActual]);

        }
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
        actualizarCartonesVista();
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

                actualizarCartonesVista();

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

    private void abrirTablero() {
        if (dialogTablero == null) {
            dialogTablero = new JDialog(vista, "Tablero de Números - Bingo", false);
            tableroPanel = new TableroPanel();

            sincronizarTablero();

            dialogTablero.add(tableroPanel);
            dialogTablero.setSize(1050, 400);
            dialogTablero.setLocationRelativeTo(vista);
            dialogTablero.setResizable(false);
        }
        dialogTablero.setVisible(true);
    }

    private void abrirTombola() {
        if (dialogTombola != null) {
            dialogTombola.setVisible(true);
        }
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
    
    private void reiniciarJuego() {
        facade.reiniciarJuego();
        vista.getPanelTombola().reiniciar();
        if (tableroPanel != null) {
            tableroPanel.reiniciar();
        }
        juegoIniciado = false;

        JOptionPane.showMessageDialog(vista, "Juego reiniciado");
    }
}
