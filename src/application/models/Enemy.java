package application.models;

import java.awt.*;
import java.io.InputStream;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import application.controllers.*;
public class Enemy {

    protected Image spriteHeadSheet;
    protected Image spriteBodySheet;
    protected Image spriteWingsSheet;
    protected Image blinkAnimation;
    protected SoundController soundController;
    protected int currentFrame = (int) (Math.random() * 40);
    protected int frameCount = (int) (Math.random() * 120);
    protected int[] headSprite;
    protected int[] bodySprite; // Lưu tọa độ body
    protected List<int[]> wingSprites = new ArrayList<>();

    protected int hp;
    protected int PosX;
    protected int PosY;
    protected int speed;
//    protected boolean alive; //Ktra ga die chua
//	protected int level = 1;
    protected boolean isForward = true; // Biến để theo dõi hướng di chuyển của animation
    protected static final int MODEL_WIDTH = 64;
    protected static final int MODEL_HEIGHT = 64;
    protected static final int MAP_WIDTH = 1900;
    //protected static final int MAP_HEIGHT = 1080;

    protected static final int[][] SPRITE_HEAD = {{195, 93, 30, 45}};
    protected static final int[][] SPRITE_BODY = {
        {1, 1, 70, 53}, {217, 1, 70, 53}, {433, 1, 70, 53},
        {217, 169, 70, 53}
    };
    protected static final int[][] SPRITE_WINGS = {
        {1, 1, 126, 112}, {129, 1, 126, 112}, {257, 1, 126, 112},
        {385, 1, 126, 112}, {513, 1, 126, 112}, {641, 1, 126, 111},
        {769, 1, 126, 111}, {897, 1, 126, 111}, {1025, 1, 126, 110},
        {1153, 1, 128, 110}, {1283, 1, 128, 108}, {1413, 1, 128, 108},
        {1543, 1, 130, 107}, {1675, 1, 130, 106}, {1807, 1, 130, 106}, {1, 115, 132, 105},
        {135, 115, 132, 104}, {269, 115, 132, 103}, {403, 115, 134, 101},
        {539, 115, 136, 99}, {677, 115, 137, 98}, {817, 115, 138, 96},
        {957, 115, 140, 94}, {1099, 115, 140, 93}, {1241, 115, 142, 90},
        {1385, 115, 142, 88}, {1529, 115, 144, 86}, {1675, 115, 144, 85}, {1821, 115, 144, 83},
        {1, 223, 146, 82}, {149, 223, 148, 80}, {299, 223, 150, 79},
        {451, 223, 150, 78}, {603, 223, 152, 77}, {757, 223, 152, 77},
        {911, 223, 152, 76}, {1065, 223, 154, 75}, {1221, 223, 154, 75},
        {1377, 223, 154, 75}, {1533, 223, 154, 74}, {1689, 223, 154, 74},
        {1845, 223, 154, 74}, {1, 307, 154, 73}, {157, 307, 154, 73},
        {313, 307, 154, 73}, {469, 307, 153, 73}, {625, 307, 152, 73},
        {779, 307, 152, 73}, {933, 307, 152, 73}, {1087, 307, 153, 73},};
    
    String[] deathSounds = {
    	    "/asset/resources/sfx/chickDie3.wav",
    	    "/asset/resources/sfx/chickDie4.wav",
    	    "/asset/resources/sfx/chickDie5.wav",
    	    "/asset/resources/sfx/chickDie6.wav",
    	    "/asset/resources/sfx/chicken1a(die).wav",
    	    "/asset/resources/sfx/chicken2b(die).wav",
    	    "/asset/resources/sfx/chicken3a(die).wav"
    	};
    String[] hitSounds = {
            "/asset/resources/sfx/chicken1b1(pluck).wav",
            "/asset/resources/sfx/chicken1b2(pluck).wav",
            "/asset/resources/sfx/chicken2a1(pluck).wav",
            "/asset/resources/sfx/chicken3b1(pluck).wav",
            "/asset/resources/sfx/chicken3b2(pluck).wav",
            "/asset/resources/sfx/chicken5b(pluck).wav"
        };
        
    public Enemy(int hp, int PosX, int PosY,
            Image bodySheet, Image wingsSheet, Image headSheet, Image blinkAnimation, SoundController soundController) {
        this.hp = hp;
        this.spriteBodySheet = bodySheet;
        this.spriteWingsSheet = wingsSheet;
        this.spriteHeadSheet = headSheet;
        this.blinkAnimation = blinkAnimation;
        this.soundController = soundController;
//        this.alive = true;
        Random random = new Random();
        this.PosX = PosX;
        this.PosY = PosY;

        this.speed = 2;

        // Chọn ngẫu nhiên một phần của body
        this.bodySprite = SPRITE_BODY[random.nextInt(SPRITE_BODY.length)];
        this.headSprite = SPRITE_HEAD[0];

        // Thêm tất cả các frame của cánh vào danh sách
        for (int[] frame : SPRITE_WINGS) {
            wingSprites.add(frame);
        }
    }

    public void render(Graphics g) {
        if (spriteBodySheet != null && spriteWingsSheet != null) {
            int[] wingFrame = wingSprites.get(currentFrame);
            int wingWidth = wingFrame[2];
            int wingHeight = wingFrame[3];
            int[][] wingOffsets = {
                //1-4
                {-5, -10}, {-5, -10}, {-5, -10}, {-5, -10}, {-5, -10},
                //5-9
                {-5, -10}, {-5, -10}, {-5, -10}, {-5, -9}, {-5, -9},
                //10-14
                {-5, -9}, {-5, -9}, {-5, -9}, {-5, -8}, {-5, -8},
                //15-19
                {-5, -8}, {-5, -7}, {-5, -7}, {-5, -6}, {-5, -6},
                //20-24
                {-5, -5}, {-5, -4}, {-5, -3}, {-5, -3}, {-5, -2},
                //25-29
                {-5, -1}, {-5, 0}, {-5, 0}, {-5, 1}, {-5, 2},
                //30-34
                {-5, 2}, {-5, 2}, {-5, 3}, {-5, 3}, {-5, 3},
                //35-39
                {-5, 4}, {-5, 4}, {-5, 4}, {-5, 4}, {-5, 5},
                //40-44
                {-5, 5}, {-5, 5}, {-5, 5}, {-5, 5}, {-5, 5},
                //45-49
                {-5, 5}, {-5, 5}, {-5, 5}, {-5, 5}, {-5, 5},};
            // Lấy offset của frame hiện tại
            int[] wingOffset = wingOffsets[currentFrame];
            int offsetX = wingOffset[0];
            int offsetY = wingOffset[1];
            int centerX = PosX + bodySprite[2] / 2;
            int centerY = PosY + bodySprite[3] / 2;

            // Vẽ cánh với offset
            g.drawImage(spriteWingsSheet,
                    centerX - wingWidth / 2 + offsetX, centerY - wingHeight / 2 + offsetY - 1,
                    centerX + wingWidth / 2 + offsetX, centerY + wingHeight / 2 + offsetY - 1,
                    wingFrame[0], wingFrame[1],
                    wingFrame[0] + wingWidth, wingFrame[1] + wingHeight, null);

//        	// Debug: Vẽ khung viền đỏ quanh cánh 
//        	g.setColor(Color.RED);
//        	g.drawRect(centerX - wingWidth / 2 + offsetX, centerY - wingHeight / 2 + offsetY, wingWidth, wingHeight);
//
//        	// Debug: Vẽ tâm (dấu chấm đỏ) ở trung tâm cánh
//        	g.setColor(Color.RED);
//        	g.fillOval(centerX + offsetX - 2, centerY + offsetY - 2, 4, 4);
            g.drawImage(spriteHeadSheet, PosX + 15, PosY - 50, PosX + headSprite[2] + 15, PosY + headSprite[3] - 50,
                    headSprite[0], headSprite[1], headSprite[0] + headSprite[2], headSprite[1] + headSprite[3], null);

            // Vẽ body
            g.drawImage(spriteBodySheet, PosX - 5, PosY - 10, PosX + bodySprite[2] - 5, PosY + bodySprite[3] - 10,
                    bodySprite[0], bodySprite[1], bodySprite[0] + bodySprite[2], bodySprite[1] + bodySprite[3], null);
//           g.drawImage(spriteBodySheet, PosX, PosY, PosX, PosY,
//                    bodySprite[0], bodySprite[1], bodySprite[0] + bodySprite[2], bodySprite[1] + bodySprite[3], null);
//            g.setColor(Color.RED);
//        	g.drawRect(PosX, PosY, bodySprite[2], bodySprite[3]);
            if (frameCount < 20) {
                g.drawImage(blinkAnimation, PosX + 23, PosY - 40, 50, 40, null);
            }
            frameCount++;
            if (frameCount > 120) {
                frameCount = 0;
            }

        } else {
            g.setColor(Color.RED);
            g.fillRect(PosX, PosY, MODEL_WIDTH, MODEL_HEIGHT);
        }
    }

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
        Random random = new Random();
        InputStream soundStream = getClass().getResourceAsStream(hitSounds[random.nextInt(hitSounds.length)]);
        if (soundStream == null) {
            System.err.println("Không tìm thấy file âm thanh.");
        } else {
        	 System.err.println("hit " + hitSounds[random.nextInt(hitSounds.length)]);
            soundController.playEffect(soundStream);
        }
      //  soundController.playEffect(getClass().getResource(hitSounds[random.nextInt(hitSounds.length)]).getPath());
    }

    public boolean isDead() {
        if (hp <= 0) {
            Random random = new Random();
            InputStream soundStream = getClass().getResourceAsStream(deathSounds[random.nextInt(deathSounds.length)]);
            if (soundStream == null) {
                System.err.println("Không tìm thấy file âm thanh.");
            } else {
            	System.err.println("dea  " + deathSounds[random.nextInt(deathSounds.length)]);
                soundController.playEffect(soundStream);
            }
           // soundController.playEffect(getClass().getResource(deathSounds[random.nextInt(deathSounds.length)]).getPath());
            return true;
        }
        return false;
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

    public void update() {
    }

}
