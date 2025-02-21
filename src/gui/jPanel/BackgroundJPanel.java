/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.jPanel;

import application.GameSettings;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import javax.swing.JComponent;

/**
 *
 * @author hp
 */
public class BackgroundJPanel extends javax.swing.JPanel {

    private final GameSettings gameSettings;
    private final Thread _updateThread;
    private final Thread _drawThread;
    private final List<Star> synchronizedStars;
    private boolean isRunning = true;

    private class Star {

        public Star(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
        
        public Star(int x, int y, int edge) {
            this.x = x;
            this.y = y;
            this.width = edge;
            this.height = edge;
        }

        private final int width;
        private final int height;
        private int x;
        private int y;
    }

    public BackgroundJPanel(GameSettings gameSettings) {
        this.gameSettings = gameSettings;
        _drawThread = new Thread(() -> {
            drawLoop();
        });
        _updateThread = new Thread(() -> {
            updateLoop();
        });
        synchronizedStars = Collections.synchronizedList(new LinkedList<Star>());
        _drawThread.start();
        _updateThread.start();
    }

    public void Stop() {
        isRunning = false;
    }

    private void loop() {
        long currentTime;
        long invokeTime;
        invokeTime = currentTime = System.nanoTime();
        while (isRunning) {
            while (currentTime - invokeTime < 1000000000 / gameSettings.getFPS()) {
                currentTime = System.nanoTime();
            }
            update();
            repaint();
            invokeTime = currentTime;
        }
    }

    private void updateLoop() {
        long currentTime;
        long invokeTime;
        invokeTime = currentTime = System.nanoTime();
        while (isRunning) {
            while (currentTime - invokeTime < 1000000000 / gameSettings.getTPS()) {
                currentTime = System.nanoTime();
            }
//            System.out.println("RUNNING");
            update();
            invokeTime = currentTime;
        }
    }

    private void update() {
        Random random = new Random();
        synchronized (synchronizedStars) {
            synchronizedStars.add(new Star(random.nextInt() % gameSettings.getWidth(), gameSettings.getHeight() + 1, random.nextInt() % 2 + 2));
            //Complexity: O(n2)
            for (Iterator it = synchronizedStars.iterator(); it.hasNext();) {
                Star star = (Star) it.next();
                if (star.y < 0) {
                    synchronizedStars.remove(star);
                    it = synchronizedStars.iterator();
                    continue;
                }
                star.y = star.y - 1;
            }
        }

    }

    private void drawLoop() {
        long currentTime;
        long invokeTime;
        invokeTime = currentTime = System.nanoTime();
        while (isRunning) {
            while (currentTime - invokeTime < 1000000000 / gameSettings.getFPS()) {
                currentTime = System.nanoTime();
            }
            repaint();
            invokeTime = currentTime;
        }
    }

    @Override
    public void paint(Graphics g) {
        g.clearRect(0, 0, gameSettings.getWidth(), gameSettings.getHeight());
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, gameSettings.getWidth(), gameSettings.getHeight());
        synchronized (synchronizedStars) {
            for (Object object : synchronizedStars) {
                if (object == null) {
                    continue;
                }
                Star star = (Star) object;
                g.setColor(Color.WHITE);
                g.fillOval(star.x, star.y, star.width, star.height);
            }
        }

    }
}
