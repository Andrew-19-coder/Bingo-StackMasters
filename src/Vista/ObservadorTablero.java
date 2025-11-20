/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Modelo.ObservadorBingo;

/**
 *
 * @author itsth
 */
public class ObservadorTablero implements ObservadorBingo{
    private TableroPanel tableroPanel;
    
    public ObservadorTablero(TableroPanel tableroPanel) {
        this.tableroPanel = tableroPanel;
    }

    @Override
    public void onNumeroCantado(int numero) {
        if (tableroPanel != null) {
            tableroPanel.marcarNumero(numero);
        }
    }
    
}
