package application.Controllers;

import javax.swing.*;
import application.Views.GamePanel;

public class GameLoop {
    private final Timer gameTimer;
    private int frameCount = 0;
    private int fps = 0;
    private long lastTime = System.nanoTime(); // Lưu thời gian frame trước

    public GameLoop(GamePanel gamePanel) {
        gameTimer = new Timer(4, e -> {
            long currentTime = System.nanoTime();
            double deltaTime = (currentTime - lastTime) / 1_000_000_000.0; // Đổi sang giây
            lastTime = currentTime;

            gamePanel.getGameManager().update(deltaTime);
            frameCount++;
        });

        new Timer(1000, e -> {
            fps = frameCount;
            frameCount = 0;
        }).start();
    }

    public void start() {
        lastTime = System.nanoTime();
        gameTimer.start();
    }

    public void stop() {
        gameTimer.stop();
    }

    public int getFPS() {
        return fps;
    }
}
