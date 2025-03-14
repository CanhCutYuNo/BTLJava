/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application.models.types;

import application.controllers.SoundController;
import application.models.Enemy;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.ImageIcon;

/**
 *
 * @author hp
 */
public abstract class ChickenEnemy extends Enemy {

    private Image spriteHeadSheet;
    private Image spriteBodySheet;
    private Image spriteWingsSheet;
    private Image blinkAnimation;
    
    protected int[] headSprite;
    protected int[] bodySprite; // Lưu tọa độ body
    protected List<int[]> wingSprites = new ArrayList<>();

    private static final int[][] SPRITE_HEAD = {{195, 93, 30, 45}};
    private static final int[][] SPRITE_BODY = {
        {1, 1, 70, 53}, {217, 1, 70, 53}, {433, 1, 70, 53},
        {217, 169, 70, 53}
    };
    private static final int[][] SPRITE_WINGS = {
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

    public ChickenEnemy(int PosX, int PosY, SoundController sound) {
        super(100, 64, 64, PosX, PosY, sound);
        
        spriteBodySheet = new ImageIcon(getClass().getResource("/asset/resources/gfx/chicken-body-stripes.png")).getImage();
        spriteWingsSheet = new ImageIcon(getClass().getResource("/asset/resources/gfx/chicken-wings.png")).getImage();
        spriteHeadSheet = new ImageIcon(getClass().getResource("/asset/resources/gfx/chicken-face.png")).getImage();
        blinkAnimation = new ImageIcon(getClass().getResource("/asset/resources/gfx/chickenBlink.png")).getImage();

        Random random = new Random();
        // Chọn ngẫu nhiên một phần của body
        this.bodySprite = SPRITE_BODY[random.nextInt(SPRITE_BODY.length)];
        this.headSprite = SPRITE_HEAD[0];

        // Thêm tất cả các frame của cánh vào danh sách
        for (int[] frame : SPRITE_WINGS) {
            wingSprites.add(frame);
        }
    }

    @Override
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
    
    @Override
    public abstract void update();
}
