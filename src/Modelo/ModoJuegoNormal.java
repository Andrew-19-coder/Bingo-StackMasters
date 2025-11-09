/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

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
    
    private boolean cuatroEsquinas(boolean[][]m){
        return m[0][0]
            && m[0][4]
            && m[4][0]
            && m[4][4];
    }
    
    private boolean lineaHorizontal(boolean m[][]){
        for(int f = 0; f < 5; f++){
            boolean completa = true;
            
            for(int c = 0; c < 5; c++){
                if(!m[f][c]){
                    completa = false;
                    break;
                }
            }
            if (completa) return true;
        }
        return false;
    }
    
    private boolean lineaVertical(boolean m[][]){
        for(int c = 0; c < 5; c++){
            boolean completa = true;
            
            for(int f = 0; f < 5; f++){
                if(!m[f][c]){
                    completa = false;
                    break;
                }
            }
            if (completa) return true;
        }
        return false;
    }
    
    private boolean diagonal(boolean m[][]){
        boolean d1 = true;
        boolean d2 = true;
        
        for(int i = 0; i < 5; i++){
            if(!m[i][i]) d1 = false;
            if(!m[i][4 - i]) d2 = false;
        }
        return d1 || d2;
    }
}
