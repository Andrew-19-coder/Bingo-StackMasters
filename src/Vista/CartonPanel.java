/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Vista;

import Modelo.Carton;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Joan
 */
public class CartonPanel extends javax.swing.JPanel {

    private Carton carton;
    private JLabel[][] lblNumeros;
    private boolean modoManual = false;
    private int posicionActual = 0;
    private int[][] numerosTemporales = new int[5][5];

    /**
     * Creates new form PanelCarton
     */
    public CartonPanel() {
        initComponents();
    }

    public void inicializarCarton(Carton carton) {
        this.carton = carton;
        this.lblNumeros = new JLabel[5][5];

        lblNumeros[0][0] = lbl00;
        lblNumeros[0][1] = lbl01;
        lblNumeros[0][2] = lbl02;
        lblNumeros[0][3] = lbl03;
        lblNumeros[0][4] = lbl04;
        lblNumeros[1][0] = lbl05;
        lblNumeros[1][1] = lbl06;
        lblNumeros[1][2] = lbl07;
        lblNumeros[1][3] = lbl08;
        lblNumeros[1][4] = lbl09;
        lblNumeros[2][0] = lbl10;
        lblNumeros[2][1] = lbl11;
        lblNumeros[2][2] = lbl12;
        lblNumeros[2][3] = lbl13;
        lblNumeros[2][4] = lbl14;
        lblNumeros[3][0] = lbl15;
        lblNumeros[3][1] = lbl16;
        lblNumeros[3][2] = lbl17;
        lblNumeros[3][3] = lbl18;
        lblNumeros[3][4] = lbl19;
        lblNumeros[4][0] = lbl20;
        lblNumeros[4][1] = lbl21;
        lblNumeros[4][2] = lbl22;
        lblNumeros[4][3] = lbl23;
        lblNumeros[4][4] = lbl24;

        if (esCartonVacio(carton)) {
            inicializarVacio();
            modoManual = true;
        } else {
            cargarNumeros();
            modoManual = false;
        }

        lblIdCarton.setText(carton.getId());
    }

    private boolean esCartonVacio(Carton carton) {
        int[][] numeros = carton.getNumeros();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (i == 2 && j == 2) {
                    continue;
                }
                if (numeros[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private void inicializarVacio() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                JLabel lbl = lblNumeros[i][j];

                if (i == 2 && j == 2) {
                    lbl.setText("LIBRE");
                    lbl.setBackground(new Color(255, 235, 59));
                    lbl.setForeground(Color.BLACK);
                } else {
                    lbl.setText(" ");
                    lbl.setBackground(Color.WHITE);
                    lbl.setForeground(Color.BLACK);
                }

                lbl.setOpaque(true);
                lbl.setHorizontalAlignment(JLabel.CENTER);
                lbl.setFont(new Font("Arial", Font.BOLD, 18));
                lbl.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

                numerosTemporales[i][j] = 0;
            }
        }
    }

    private void cargarNumeros() {
        int[][] numeros = carton.getNumeros();
        boolean[][] marcados = carton.getMarcados();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                JLabel lbl = lblNumeros[i][j];

                if (i == 2 && j == 2) {
                    lbl.setText("LIBRE");
                    lbl.setBackground(new Color(255, 235, 59));
                } else {
                    lbl.setText(String.valueOf(numeros[i][j]));
                }

                lbl.setOpaque(true);
                lbl.setHorizontalAlignment(JLabel.CENTER);
                lbl.setFont(new Font("Arial", Font.BOLD, 18));
                lbl.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                lbl.setPreferredSize(new java.awt.Dimension(50, 50));
                if (marcados[i][j]) {
                    lbl.setBackground(new Color(76, 175, 80));
                    lbl.setForeground(Color.WHITE);
                } else {
                    lbl.setBackground(Color.WHITE);
                    lbl.setForeground(Color.BLACK);
                }
            }
        }
    }

    public boolean agregarNumero(int numero) {
        if (posicionActual >= 24) {
            return false;
        }

        int col = posicionActual / 5;
        int fila = posicionActual % 5;

        if (fila == 2 && col == 2) {
            posicionActual++;
            if (posicionActual < 24) {
                col = posicionActual / 5;
                fila = posicionActual % 5;
            }
        }

        int min = col * 15 + 1;
        int max = (col + 1) * 15;

        if (numero < min || numero > max) {
            String[] letras = {"B", "I", "N", "G", "O"};
            JOptionPane.showMessageDialog(this,
                    "El número debe estar entre " + min + "-" + max + " (columna " + letras[col] + ")");
            return false;
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (numerosTemporales[i][j] == numero) {
                    JOptionPane.showMessageDialog(this, "El número " + numero + " ya existe");
                    return false;
                }
            }
        }

        lblNumeros[fila][col].setText(String.valueOf(numero));
        lblNumeros[fila][col].setForeground(Color.BLACK);
        numerosTemporales[fila][col] = numero;
        posicionActual++;

        if (posicionActual == 24) {
            carton.setNumeros(numerosTemporales);
            modoManual = false;
            JOptionPane.showMessageDialog(this, "¡Cartón " + carton.getId() + " completado!");
        }

        return true;
    }

    public boolean isModoManual() {
        return modoManual;
    }

    public void actualizarMarcas() {
        boolean[][] marcados = carton.getMarcados();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (marcados[i][j]) {
                    lblNumeros[i][j].setBackground(new Color(76, 175, 80));
                    lblNumeros[i][j].setForeground(Color.WHITE);
                } else if (i == 2 && j == 2) {
                    lblNumeros[i][j].setBackground(new Color(255, 235, 59));
                } else {
                    lblNumeros[i][j].setBackground(Color.WHITE);
                    lblNumeros[i][j].setForeground(Color.BLACK);
                }
            }
        }
    }

    public void resaltarGanador() {
        setBorder(BorderFactory.createLineBorder(Color.RED, 5));
    }

    public JButton getBtnCerrar() {
        return BtnCerrar;
    }

    public Carton getCarton() {
        return carton;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblIdCarton = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblB = new javax.swing.JLabel();
        lblI = new javax.swing.JLabel();
        lblN = new javax.swing.JLabel();
        lblG = new javax.swing.JLabel();
        lblO = new javax.swing.JLabel();
        lbl00 = new javax.swing.JLabel();
        lbl01 = new javax.swing.JLabel();
        lbl02 = new javax.swing.JLabel();
        lbl03 = new javax.swing.JLabel();
        lbl04 = new javax.swing.JLabel();
        lbl05 = new javax.swing.JLabel();
        lbl06 = new javax.swing.JLabel();
        lbl07 = new javax.swing.JLabel();
        lbl08 = new javax.swing.JLabel();
        lbl09 = new javax.swing.JLabel();
        lbl10 = new javax.swing.JLabel();
        lbl11 = new javax.swing.JLabel();
        lbl12 = new javax.swing.JLabel();
        lbl13 = new javax.swing.JLabel();
        lbl14 = new javax.swing.JLabel();
        lbl16 = new javax.swing.JLabel();
        lbl17 = new javax.swing.JLabel();
        lbl18 = new javax.swing.JLabel();
        lbl19 = new javax.swing.JLabel();
        lbl20 = new javax.swing.JLabel();
        lbl21 = new javax.swing.JLabel();
        lbl22 = new javax.swing.JLabel();
        lbl23 = new javax.swing.JLabel();
        lbl24 = new javax.swing.JLabel();
        lbl15 = new javax.swing.JLabel();
        BtnCerrar = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        lblIdCarton.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblIdCarton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIdCarton.setText("Carton-1");
        add(lblIdCarton, java.awt.BorderLayout.PAGE_START);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        lblB.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblB.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblB.setText("B");

        lblI.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblI.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblI.setText("I");

        lblN.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblN.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblN.setText("N");

        lblG.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblG.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblG.setText("G");

        lblO.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblO.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblO.setText("O");

        lbl00.setBackground(new java.awt.Color(0, 0, 0));
        lbl00.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl00.setText("jLabel2");

        lbl01.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl01.setText("jLabel3");

        lbl02.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl02.setText("jLabel4");

        lbl03.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl03.setText("jLabel5");

        lbl04.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl04.setText("jLabel6");

        lbl05.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl05.setText("jLabel7");

        lbl06.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl06.setText("jLabel8");

        lbl07.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl07.setText("jLabel9");

        lbl08.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl08.setText("jLabel10");

        lbl09.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl09.setText("jLabel11");

        lbl10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl10.setText("jLabel12");

        lbl11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl11.setText("jLabel13");

        lbl12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl12.setText("jLabel14");

        lbl13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl13.setText("jLabel15");

        lbl14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl14.setText("jLabel16");

        lbl16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl16.setText("jLabel17");

        lbl17.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl17.setText("jLabel18");

        lbl18.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl18.setText("jLabel19");

        lbl19.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl19.setText("jLabel20");

        lbl20.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl20.setText("jLabel21");

        lbl21.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl21.setText("jLabel22");

        lbl22.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl22.setText("jLabel23");

        lbl23.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl23.setText("jLabel24");

        lbl24.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl24.setText("jLabel25");

        lbl15.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl15.setText("jLabel26");

        BtnCerrar.setForeground(new java.awt.Color(255, 0, 0));
        BtnCerrar.setText("✖");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(lblB, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblI, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(lblN, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblG, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lbl17, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbl16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbl18, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbl19, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbl01, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                            .addComponent(lbl00, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl02, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl03, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl04, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(55, 55, 55)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(242, 242, 242)
                                .addComponent(lbl15, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lbl06, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl05, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl07, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl08, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl09, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(41, 41, 41)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl10, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl11, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl12, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl13, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl14, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lbl20, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                        .addComponent(lbl21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(lblO, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(69, 69, 69))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(BtnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(BtnCerrar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblB, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblI, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblN, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblG, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblO, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl00)
                    .addComponent(lbl05)
                    .addComponent(lbl10)
                    .addComponent(lbl15)
                    .addComponent(lbl20))
                .addGap(58, 58, 58)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl11)
                    .addComponent(lbl01)
                    .addComponent(lbl06)
                    .addComponent(lbl16)
                    .addComponent(lbl21))
                .addGap(63, 63, 63)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl02)
                    .addComponent(lbl07)
                    .addComponent(lbl22)
                    .addComponent(lbl17)
                    .addComponent(lbl12))
                .addGap(62, 62, 62)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl23)
                    .addComponent(lbl03)
                    .addComponent(lbl13)
                    .addComponent(lbl18)
                    .addComponent(lbl08))
                .addGap(58, 58, 58)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl19)
                    .addComponent(lbl04)
                    .addComponent(lbl24)
                    .addComponent(lbl14, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl09))
                .addContainerGap(63, Short.MAX_VALUE))
        );

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnCerrar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbl00;
    private javax.swing.JLabel lbl01;
    private javax.swing.JLabel lbl02;
    private javax.swing.JLabel lbl03;
    private javax.swing.JLabel lbl04;
    private javax.swing.JLabel lbl05;
    private javax.swing.JLabel lbl06;
    private javax.swing.JLabel lbl07;
    private javax.swing.JLabel lbl08;
    private javax.swing.JLabel lbl09;
    private javax.swing.JLabel lbl10;
    private javax.swing.JLabel lbl11;
    private javax.swing.JLabel lbl12;
    private javax.swing.JLabel lbl13;
    private javax.swing.JLabel lbl14;
    private javax.swing.JLabel lbl15;
    private javax.swing.JLabel lbl16;
    private javax.swing.JLabel lbl17;
    private javax.swing.JLabel lbl18;
    private javax.swing.JLabel lbl19;
    private javax.swing.JLabel lbl20;
    private javax.swing.JLabel lbl21;
    private javax.swing.JLabel lbl22;
    private javax.swing.JLabel lbl23;
    private javax.swing.JLabel lbl24;
    private javax.swing.JLabel lblB;
    private javax.swing.JLabel lblG;
    private javax.swing.JLabel lblI;
    private javax.swing.JLabel lblIdCarton;
    private javax.swing.JLabel lblN;
    private javax.swing.JLabel lblO;
    // End of variables declaration//GEN-END:variables
}
