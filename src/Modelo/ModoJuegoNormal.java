/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.ArrayList;

/**
 *
 * @author itsth
 */
public class ModoJuegoNormal implements ModoJuego{
    
    @Override
    public boolean verificarGanador(Carton carton) {
        boolean m[][] = carton.getMarcados();
        
        return cuatroEsquinas(m)
                || lineaHorizontal(m)
                || lineaVertical(m)
                || diagonal(m);
    }
    
    @Override
    public ArrayList<int[]> obtenerPosicionesGanadoras(Carton carton) {
        boolean m[][] = carton.getMarcados();
        
       
        ArrayList<int[]> posiciones;
        
        posiciones = obtenerCuatroEsquinas(m);
        if (posiciones != null) return posiciones;
        
        posiciones = obtenerLineaHorizontal(m);
        if (posiciones != null) return posiciones;
        
        posiciones = obtenerLineaVertical(m);
        if (posiciones != null) return posiciones;
        
        posiciones = obtenerDiagonal(m);
        if (posiciones != null) return posiciones;
        
        return null;
    }
    
    private boolean cuatroEsquinas(boolean[][] m) {
        return m[0][0] && m[0][4] && m[4][0] && m[4][4];
    }
    
    private ArrayList<int[]> obtenerCuatroEsquinas(boolean[][] m) {
        if (!cuatroEsquinas(m)) return null;
        
        ArrayList<int[]> posiciones = new ArrayList<>();
        posiciones.add(new int[]{0, 0});
        posiciones.add(new int[]{0, 4});
        posiciones.add(new int[]{4, 0});
        posiciones.add(new int[]{4, 4});
        return posiciones;
    }
    
    private boolean lineaHorizontal(boolean m[][]) {
        for (int f = 0; f < 5; f++) {
            boolean completa = true;
            for (int c = 0; c < 5; c++) {
                if (!m[f][c]) {
                    completa = false;
                    break;
                }
            }
            if (completa) return true;
        }
        return false;
    }
    
    private ArrayList<int[]> obtenerLineaHorizontal(boolean m[][]) {
        for (int f = 0; f < 5; f++) {
            boolean completa = true;
            for (int c = 0; c < 5; c++) {
                if (!m[f][c]) {
                    completa = false;
                    break;
                }
            }
            if (completa) {
                ArrayList<int[]> posiciones = new ArrayList<>();
                for (int c = 0; c < 5; c++) {
                    posiciones.add(new int[]{f, c});
                }
                return posiciones;
            }
        }
        return null;
    }
    
    private boolean lineaVertical(boolean m[][]) {
        for (int c = 0; c < 5; c++) {
            boolean completa = true;
            for (int f = 0; f < 5; f++) {
                if (!m[f][c]) {
                    completa = false;
                    break;
                }
            }
            if (completa) return true;
        }
        return false;
    }
    
    private ArrayList<int[]> obtenerLineaVertical(boolean m[][]) {
        for (int c = 0; c < 5; c++) {
            boolean completa = true;
            for (int f = 0; f < 5; f++) {
                if (!m[f][c]) {
                    completa = false;
                    break;
                }
            }
            if (completa) {
                ArrayList<int[]> posiciones = new ArrayList<>();
                for (int f = 0; f < 5; f++) {
                    posiciones.add(new int[]{f, c});
                }
                return posiciones;
            }
        }
        return null;
    }
    
    private boolean diagonal(boolean m[][]) {
        boolean d1 = true;
        boolean d2 = true;
        
        for (int i = 0; i < 5; i++) {
            if (!m[i][i]) d1 = false;
            if (!m[i][4 - i]) d2 = false;
        }
        return d1 || d2;
    }
    
    private ArrayList<int[]> obtenerDiagonal(boolean m[][]) {
        boolean d1 = true;
        boolean d2 = true;
        
        for (int i = 0; i < 5; i++) {
            if (!m[i][i]) d1 = false;
            if (!m[i][4 - i]) d2 = false;
        }
        
        ArrayList<int[]> posiciones = new ArrayList<>();
        
        if (d1) {
            for (int i = 0; i < 5; i++) {
                posiciones.add(new int[]{i, i});
            }
            return posiciones;
        }
        
        if (d2) {
            for (int i = 0; i < 5; i++) {
                posiciones.add(new int[]{i, 4 - i});
            }
            return posiciones;
        }
        
        return null;
    }
}
