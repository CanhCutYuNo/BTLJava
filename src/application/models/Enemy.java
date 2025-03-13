package application.models;

import java.awt.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public abstract class Enemy {

    protected int currentFrame = (int) (Math.random() * 40);
    protected int frameCount = (int) (Math.random() * 120);

    private int hp;
    protected int PosX;
    protected int PosY;
//    protected boolean alive; //Ktra ga die chua
//	protected int level = 1;
    protected boolean isForward = true; // Biến để theo dõi hướng di chuyển của animation
    protected final int MODEL_WIDTH;
    protected final int MODEL_HEIGHT;
    protected static final int MAP_WIDTH = 1900;
    //protected static final int MAP_HEIGHT = 1080;
    
    public Enemy(int hp, int MODEL_WIDTH, int MODEL_HEIGHT , int PosX, int PosY) {
        this.hp = hp;       
        this.MODEL_WIDTH = MODEL_WIDTH;
        this.MODEL_HEIGHT = MODEL_HEIGHT;
        this.PosX = PosX;
        this.PosY = PosY;
//        this.alive = true;
    }

    public abstract void render(Graphics g);

    // ?
    public void nextFrame() {
        if (isForward) {
            currentFrame++;
            if (currentFrame >= 48) {
                isForward = false; // Đổi hướng khi đến cuối mảng
            }
        } else {
            currentFrame--;
            if (currentFrame <= 0) {
                isForward = true; // Đổi hướng khi về đầu mảng
            }
        }
    }

    public void takeDamage(int damage) {
        hp -= damage;
    }

    public boolean isDead() {
        return hp <= 0;
    }

    public Rectangle getBounds() {
        return new Rectangle(PosX, PosY, MODEL_WIDTH, MODEL_HEIGHT);
    }

    public int getHp() {
        return hp;
    }

    public int getPosX() {
        return PosX;
    }

    public int getPosY() {
        return PosY;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public abstract void update();
}
