/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Joan
 */
public class PanelFondoImagen extends JPanel {

    private Image imagenFondo;

    public PanelFondoImagen(String rutaRelativa) {
        try {

            URL url = getClass().getResource(rutaRelativa);

            if (url == null) {
                throw new IOException("Error: No se pudo encontrar la imagen en la ruta: " + rutaRelativa);
            }
            imagenFondo = ImageIO.read(url);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Falló la carga de la imagen de fondo.");
            imagenFondo = null;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagenFondo != null) {
            g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
