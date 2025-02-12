/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.jPanel;

import application.GameSettings;
import java.awt.Font;
/**
 *
 * @author hp
 */
public class MainMenuJPanel extends javax.swing.JPanel {

    private final GameSettings gameSettings;

    public MainMenuJPanel(GameSettings gameSettings) {
        this.gameSettings = gameSettings;
        initComponents();
    }


    private void initComponents() {
        lineBorder1 = (javax.swing.border.LineBorder) javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0));
        jLayeredPane = new javax.swing.JLayeredPane();
//        backgroundPanel = new javax.swing.JPanel();
        backgroundPanel = new BackgroundJPanel(gameSettings);
        containerPanel = new javax.swing.JPanel();
        symbolPanel = new javax.swing.JPanel();
        icon = new javax.swing.JLabel();
        buttonPanel = new javax.swing.JPanel();
        buttonPanel1 = new javax.swing.JPanel();
        button1 = new gui.Button(300, 50);
        buttonPanel2 = new javax.swing.JPanel();
        button2 = new gui.Button(300, 50);

        setLayout(new java.awt.GridLayout(1, 1));

        jLayeredPane.setLayout(new javax.swing.OverlayLayout(jLayeredPane));

        jLayeredPane.add(backgroundPanel);

        containerPanel.setBackground(new java.awt.Color(255, 0, 0));
        containerPanel.setOpaque(false);
        containerPanel.setPreferredSize(new java.awt.Dimension(485, 410));
        containerPanel.setLayout(new java.awt.GridLayout(2, 1));

        symbolPanel.setMinimumSize(new java.awt.Dimension(0, 0));
        symbolPanel.setOpaque(false);
        symbolPanel.setLayout(new java.awt.GridBagLayout());

        icon.setBackground(new java.awt.Color(255, 255, 255));
        icon.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        icon.setForeground(new java.awt.Color(255, 255, 255));
        icon.setText("GAME");
        icon.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        symbolPanel.add(icon, new java.awt.GridBagConstraints());

        containerPanel.add(symbolPanel);

        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new java.awt.GridLayout(2, 0));

        buttonPanel1.setOpaque(false);
        buttonPanel1.setLayout(new java.awt.GridBagLayout());

        button1.setText("Play");
        button1.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        buttonPanel1.add(button1, new java.awt.GridBagConstraints());

        buttonPanel.add(buttonPanel1);

        buttonPanel2.setOpaque(false);
        buttonPanel2.setLayout(new java.awt.GridBagLayout());

        button2.setText("Settings");
        button2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        buttonPanel2.add(button2, new java.awt.GridBagConstraints());

        buttonPanel.add(buttonPanel2);

        containerPanel.add(buttonPanel);

        jLayeredPane.setLayer(containerPanel, 1);
        jLayeredPane.add(containerPanel);

        add(jLayeredPane);
    }// </editor-fold>                        

    // Variables declaration - do not modify                     
    private javax.swing.JPanel backgroundPanel;
    private gui.Button button1;
    private gui.Button button2;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JPanel buttonPanel1;
    private javax.swing.JPanel buttonPanel2;
    private javax.swing.JPanel containerPanel;
    private javax.swing.JLabel icon;
    private javax.swing.JLayeredPane jLayeredPane;
    private javax.swing.border.LineBorder lineBorder1;
    private javax.swing.JPanel symbolPanel;
    // End of variables declaration  
}
