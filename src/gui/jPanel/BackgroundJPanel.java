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
        
        public Star(int speed, int x, int y) {
            this.speed = speed;
            this.x = x;
            this.y = y;
        }
        
        private int speed;

        /**
         * Get the value of speed
         *
         * @return the value of speed
         */
        public int getSpeed() {
            return speed;
        }

        /**
         * Set the value of speed
         *
         * @param speed new value of speed
         */
        public void setSpeed(int speed) {
            this.speed = speed;
        }
        
        private int x;

        /**
         * Get the value of x
         *
         * @return the value of x
         */
        public int getX() {
            return x;
        }

        /**
         * Set the value of x
         *
         * @param x new value of x
         */
        public void setX(int x) {
            this.x = x;
        }
        
        private int y;

        /**
         * Get the value of y
         *
         * @return the value of y
         */
        public int getY() {
            return y;
        }

        /**
         * Set the value of y
         *
         * @param y new value of y
         */
        public void setY(int y) {
            this.y = y;
        }
        
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
            synchronizedStars.add(new Star((random.nextInt() % 5), random.nextInt() % gameSettings.getWidth(), gameSettings.getHeight() + 1));
            //Complexity: O(n2)
            for (Iterator it = synchronizedStars.iterator(); it.hasNext();) {
                Star star = (Star) it.next();
                if (star.y < 0) {
                    synchronizedStars.remove(star);
                    it = synchronizedStars.iterator();
                    continue;
                }
                star.y = star.y - Math.abs(star.speed) - 1;
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
                g.fillOval(star.x, star.y, 3, 3);
            }            
        }
        
    }
}
