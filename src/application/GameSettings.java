/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application;

/**
 *
 * @author hp
 */
public class GameSettings {

    public GameSettings(long fPS, long tPS, int width, int height) {
        this.fPS = fPS;
        this.tPS = tPS;
        this.width = width;
        this.height = height;
    }

    private long fPS;
    private long tPS;
    private int width;
    private int height;

    public long getFPS() {
        return fPS;
    }

    public void setFPS(long fPS) {
        this.fPS = fPS;
    }

    public long getTPS() {
        return tPS;
    }

    public void setTPS(long tPS) {
        this.tPS = tPS;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
