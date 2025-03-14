package application.controllers.levels;

import application.controllers.LevelManager;
import application.controllers.SoundController;
import application.models.Enemy;
import application.models.types.ChickenEnemy;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Level1Manager extends LevelManager {

	SoundController sound;
	
    public Level1Manager(SoundController sound) {
        super();
        this.sound = sound;
        initEnemies();
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
                enemies.add(new ChickenEnemyLvl1(posX, posY, sound));
            }
            posY += 200;
        }
    }

    private class ChickenEnemyLvl1 extends ChickenEnemy {
        private boolean movingRight;
        private int speed;

        public ChickenEnemyLvl1(int PosX, int PosY, SoundController sound) {
            super(PosX, PosY, sound);
            movingRight = true;
            speed = 2;
        }

        @Override
        public void update() {
            if (movingRight) {
                PosX += speed;
                if (PosX >= MAP_WIDTH - MODEL_WIDTH) {
                    movingRight = false;
                }
            } else {
                PosX -= speed;
                if (PosX <= 0) {
                    movingRight = true;
                }
            }
        }
    }

}
