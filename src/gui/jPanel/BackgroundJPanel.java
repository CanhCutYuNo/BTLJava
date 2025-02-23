/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.jPanel;

import application.GameSettings;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

/**
 *
 * @author hp
 */
public class BackgroundJPanel extends javax.swing.JPanel {

    private final GameSettings gameSettings;
    private final Thread drawThread;
    private Image backgroundImage1;
    private Image backgroundImage2;
    private int y1;
    private int y2;
    private boolean isRunning = true;

    public BackgroundJPanel(GameSettings gameSettings) {
        this.gameSettings = gameSettings;
        backgroundImage1 = new ImageIcon(getClass().getResource("/asset/resources/backgrounds/1.png")).getImage();
        backgroundImage2 = new ImageIcon(getClass().getResource("/asset/resources/backgrounds/1.png")).getImage();
//        backgroundImage1 = backgroundImage1.getScaledInstance(gameSettings.getHeight(), gameSettings.getWidth(), Image.SCALE_AREA_AVERAGING);
//        backgroundImage2 = backgroundImage2.getScaledInstance(gameSettings.getHeight(), gameSettings.getWidth(), Image.SCALE_AREA_AVERAGING);   
        y1 = 0;
        y2 = gameSettings.getHeight();
        drawThread = new Thread(() -> {
            drawLoop();
        });
        drawThread.start();
    }

    public void stop() {
        isRunning = false;
    }

    private void drawLoop() {
        long currentTime;
        long invokeTime;
        invokeTime = currentTime = System.nanoTime();
        while (isRunning) {
            while (currentTime - invokeTime < 1000000000 / gameSettings.getFPS()) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                currentTime = System.nanoTime();
            }
            update();
            repaint();
            invokeTime = currentTime;
        }
    }

    private void update() {
        if (y1 == -gameSettings.getHeight()) {
            y1 = gameSettings.getHeight();
        }
        if (y2 == -gameSettings.getHeight()) {
            y2 = gameSettings.getHeight();
        }
        y1--;
        y2--;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(backgroundImage1, 0, y1, gameSettings.getWidth(), gameSettings.getHeight(), null);
        g.drawImage(backgroundImage2, 0, y2, gameSettings.getWidth(), gameSettings.getHeight(), null);
    }
}
