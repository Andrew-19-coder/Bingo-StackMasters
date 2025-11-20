/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Modelo.*;
import Vista.*;
import javax.swing.*;
import java.awt.BorderLayout;

public class Controlador {

    private JuegoFacade facade;
    private MainFrame vista;
    private Juego juego;
    private JDialog dialogTombola;
    private TableroPanel tablero;

    private ControladorCarton controladorCarton;
    private ControladorTombola controladorTombola;
    private ControladorJuego controladorJuego;
    private ControladorTablero controladorTablero;

    public Controlador() {
        this.tablero = new TableroPanel();
        this.juego = Juego.getInstance();
        this.vista = new MainFrame();
        juego.setModoJuego(new ModoJuegoNormal());
        this.facade = new JuegoFacade(vista);

        registrarObservadores();

        this.controladorCarton = new ControladorCarton(facade, vista, juego);
        this.controladorTablero = new ControladorTablero(facade, vista, juego, tablero, controladorCarton);
        this.controladorTombola = new ControladorTombola(facade, vista, juego, controladorCarton, tablero);
        this.controladorJuego = new ControladorJuego(facade, vista, juego, tablero);

        inicializarTodos();

        vista.setVisible(true);
        mostrarTombola();
        tablero.setVisible(true);

    }

    private void inicializarTodos() {
        controladorCarton.inicializarEventos();
        controladorJuego.inicializarEventos();
        controladorTablero.inicializarEventos();
        controladorTombola.inicializarTombola();

        vista.getBtnAbrirTombola().addActionListener(e -> abrirTombola());
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

    private void registrarObservadores() {
        Tombola tombola = juego.getTombola();
        ObservadorTablero obsTablero = new ObservadorTablero(tablero);
        tombola.agregarObservador(obsTablero);
        ObservadorUltimoNumero obsUltimoNumero = new ObservadorUltimoNumero(vista);
        tombola.agregarObservador(obsUltimoNumero);
        ObservadorCartonesVista obsCartonesVista = new ObservadorCartonesVista(vista, juego, facade);
        tombola.agregarObservador(obsCartonesVista);
        ObservadorVerificarGanador obsVerificarGanador = new ObservadorVerificarGanador(juego, facade, vista);
        tombola.agregarObservador(obsVerificarGanador);
    }

    private void abrirTombola() {
        if (dialogTombola != null) {
            dialogTombola.setVisible(true);
        }
    }
}
