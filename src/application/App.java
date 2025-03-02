/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application;

import application.Controllers.Controller;
import application.Controllers.GameLoop;
import application.Controllers.Manager;
import application.Views.*;
import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author hp
 */
public class App {

    JFrame frame;
//    CardLayout cardLayout;
    JPanel mainPanel;
    Manager gameManager;
    GamePanel gamePanel;
    BackgroundPanel backgroundPanel;
    GameLoop gameLoop;

    public App() {
        frame = new JFrame("Chicken Invaders");
        frame.setUndecorated(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        gameManager = new Manager(this, mainPanel);
        gamePanel = new GamePanel(gameManager);

        backgroundPanel = new BackgroundPanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        frame.addKeyListener(new Controller(frame, this, mainPanel));
        frame.setFocusable(true);
        frame.requestFocus();

        gameLoop = new GameLoop(gamePanel);
        gameLoop.start();

        navigateToMainMenu();
    }

    public void setPanel(JPanel p) {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(p);
        frame.revalidate();
        frame.repaint();
    }

    public void navigateToMainMenu() {
        setPanel(new MainMenuPanel(() -> {
            setPanel(gamePanel);
        }, () -> {
            navigateToSetting();
        }, backgroundPanel));
    }

    public void navigateToSetting() {
        setPanel(new SettingPanel(() -> {
            navigateToMainMenu();
        }, backgroundPanel));
    }

    public static void main(String args[]) {
        new App();
    }

}
