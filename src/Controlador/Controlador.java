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
    private boolean modoManual = false;
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
        vista.getPanelTombola().getBtnModoManual().addActionListener(e -> toggleModoManual());
        vista.getPanelTombola().getBtnIngresar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ingresarNumeroManual();
            }
        });

        vista.getPanelTombola().configurarEventosConFacade(
                () -> {

                    juegoIniciado = true;

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

                    int ultimoNum = facade.getTombola().getUltimoNumeroCantado();

                    if (modoManual && cartonEnEdicion != null && cartonEnEdicion.isModoManual()) {
                        cartonEnEdicion.agregarNumero(ultimoNum);
                    } else {
                        juego.marcarNumero(ultimoNum);
                    }

                    actualizarCartonesVista();
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
                }
        );
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
                facade.crearNuevoCarton();
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

    private void toggleModoManual() {
        modoManual = !modoManual;

        if (modoManual) {
            vista.getPanelTombola().habilitarModoManual();
            cartonEnEdicion = facade.crearCartonManual();
            JOptionPane.showMessageDialog(vista,
                    "Modo Manual activado");
        } else {
            vista.getPanelTombola().habilitarModoAutomatico();

            if (cartonEnEdicion != null && cartonEnEdicion.isModoManual()) {
                int confirm = JOptionPane.showConfirmDialog(vista,
                        "Hay un cartón sin completar. ¿Desea eliminarlo?",
                        "Cartón Incompleto",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    vista.getPanelCentral().remove(cartonEnEdicion);
                    vista.getPanelCentral().revalidate();
                    vista.getPanelCentral().repaint();
                }
            }

            cartonEnEdicion = null;
            JOptionPane.showMessageDialog(vista, "Modo Manual desactivado");
        }
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
                modo = new cartonLleno();
                break;
            default:
                modo = new ModoJuegoNormal();
        }

        facade.cambiarModoJuego(modo, nombresModos[modoActual]);
        JOptionPane.showMessageDialog(vista, "Modo: " + nombresModos[modoActual]);
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

        if (modoManual && cartonEnEdicion != null && cartonEnEdicion.isModoManual()) {

            if (cartonEnEdicion.agregarNumero(numero)) {
                vista.getPanelTombola().getTxtManual().setText("");
            }
        } else {

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
                            "¡GANADOR! " + facade.obtenerIdGanador() + "\n"
                            + "Jugada: " + tipoJugada,
                            "¡BINGO!",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(vista, "Número inválido o ya cantado");
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
