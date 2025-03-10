package application.controllers.levels;

import application.controllers.LevelManager;
import application.models.types.ChickenEnemy;
import java.awt.Image;

public class Level2Manager extends LevelManager {

    public Level2Manager() {
        super();
    }

    @Override
    protected void initEnemies() {
        int nums = 10;
        int centerX = 1900 / 2;
        int centerY = 1080 / 4;
        for (int i = 0; i < nums; i++) {
            double angle = 2 * Math.PI * i / nums;
            int posX = centerX + (int) (100 * Math.cos(angle));
            int posY = centerY + (int) (100 * Math.sin(angle));
            enemies.add(new ChickenEnemyLevel2(posX, posY));
        }
    }

    public class ChickenEnemyLevel2 extends ChickenEnemy {

        private int centerX, centerY;
        private int radius;
        private double theta; // góc quay chuyển động tròn

        public ChickenEnemyLevel2(int PosX, int PosY) {
            super(PosX, PosY);
            this.theta = Math.random() * 2 * Math.PI;
            this.centerX = PosX;
            this.centerY = PosY;
            this.radius = 100;
        }

        @Override
        public void update() {
            theta += 0.01;
            PosX = centerX + (int) (radius * Math.cos(theta));
            PosY = centerY + (int) (radius * Math.sin(theta));
        }

    }

}
