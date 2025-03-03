package application.Views;

import application.Controllers.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GamePanel extends BasePanel {
    private final Manager gameManager;
    private final GameLoop gameLoop;
    private final MouseController mouseController;

    public GamePanel(Manager gameManager) {
        super("/asset/resources/gfx/background.png");
        this.gameManager = gameManager;

        hideCursor();
        gameManager.spawnEnemies();
        setLayout(null);
        setFocusable(true);
        setDoubleBuffered(true);
        requestFocusInWindow();

        // Khởi tạo và gán controller
        mouseController = new MouseController(this);
        addMouseListener(mouseController);
        addMouseMotionListener(mouseController);

        // Khởi động GameLoop
        gameLoop = new GameLoop(this);
        gameLoop.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        gameManager.render(g);
    }

    private void hideCursor() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image cursorImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Cursor invisibleCursor = toolkit.createCustomCursor(cursorImage, new Point(0, 0), "InvisibleCursor");
        setCursor(invisibleCursor);
    }

    public Manager getGameManager() {
        return gameManager;
    }
}
