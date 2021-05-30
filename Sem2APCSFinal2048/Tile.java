import java.awt.Color;
import java.util.Random;

import javax.swing.*;
public class Tile {
    private int number; 
    private Color color; 
    private int x;
    private int y; 
    public Tile(int num, Color col, int x_coord, int y_coord) {
        this.color = col;
        this.number = num;
        this.x = x_coord;
        this.y = y_coord;  
    }
    public Tile(int x_coord, int y_coord) {
        this.number = newTileValue(); 
        setColor(this.number);  
        this.x = x_coord;
        this.y = y_coord; 
    }
    public Tile() {
        this.color = Color.GRAY; 
        this.number = 0; 
    }
    public int getNumber() {
        return this.number; 
    }
    public Color getColor() {
        return this.color; 
    }
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y; 
    }
    public void setX(int x_coord) {
        this.x = x_coord; 
    }
    public void setY(int y_coord) {
        this.y = y_coord; 
    }
    public void setNumber(int num) {
        this.number = num; 
        setColor(num);
    }
    public int newTileValue() {
        Random rand = new Random(); 
        int probability = (int)(Math.random()*10) + 1; 
        if (probability == 1) {
            return 4; 
        }
        else {
            return 2; 
        }
    }
    public Color setColor(int num) {
        if (num == 2) {
            this.color = color.RED; 
            return this.color; 
        }
        else if (num == 4) {
            this.color = color.GRAY; 
            return this.color; 
        } 
        else if (num == 8) {
            this.color = color.ORANGE; 
            return this.color; 
        }
        else if (num == 16) {
            this.color = color.CYAN; 
            return this.color; 
        }
        else if (num == 32) {
            this.color = color.BLUE;
            return this.color; 
        }
        else if (num == 64) {
            this.color = color.GREEN;
        }
        else if (num == 128) {
            this.color = color.BLACK;
            return this.color; 
        }
        else if (num == 256) {
            this.color = color.pink; 
            return this.color; 
        }
        else if (num == 512) {
            this.color = color.lightGray;
            return this.color; 
        }
        else if (num == 1024) {
            this.color = color.WHITE; 
            return this.color; 
        }
        else {
            this.color = color.magenta; 
            return this.color; 
        }
        return this.color; 
    }
}