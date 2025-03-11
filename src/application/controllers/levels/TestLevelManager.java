package application.controllers.levels;

import application.controllers.LevelManager;
import application.models.Enemy;
import application.models.types.ChickenEnemy;
import application.models.types.SmokeTestEnemy;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class TestLevelManager extends LevelManager {

    public TestLevelManager() {
        super();
    }

    @Override
    protected void initEnemies() {
        int nums = 8;
        int spacing = 200;
        int startX = 100;
        int posY = 100;
        enemies = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < nums; j++) {
                int posX = startX + j * spacing;
                enemies.add(new SmokeTestEnemy(posX, posY));
            }
            posY += 200;
        }
    }

}
