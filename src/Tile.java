import java.awt.Color;
import java.util.Random;

import javax.swing.*;
public class Tile {
    private int number; 
    private Color color; 
    private int x;
    private int y; 
    private long clipTime; 
    public Tile(int num, Color col, int x_coord, int y_coord, int clipTime) {
        this.color = col;
        this.number = num;
        this.x = x_coord;
        this.y = y_coord;
        this.clipTime = clipTime; 
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
    public long getClipTime() {
    	return this.clipTime; 
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
            this.color = new Color(238, 228, 218);
            return this.color; 
        }
        else if (num == 4) {
            this.color = new Color(238, 225, 201);; 
            return this.color; 
        } 
        else if (num == 8) {
            this.color = new Color(243, 178, 122);; 
            return this.color; 
        }
        else if (num == 16) {
            this.color = new Color(246, 150, 100); 
            return this.color; 
        }
        else if (num == 32) {
            this.color = new Color(247, 124, 95);
            return this.color; 
        }
        else if (num == 64) {
            this.color = new Color(247, 95, 59);
        }
        else if (num == 128) {
            this.color = new Color(237, 208, 115);
            return this.color; 
        }
        else if (num == 256) {
            this.color = new Color(237, 204, 98); 
            return this.color; 
        }
        else if (num == 512) {
            this.color = new Color(237, 201, 80);
            return this.color; 
        }
        else if (num == 1024) {
            this.color = new Color(237, 197, 63); 
            return this.color; 
        }
        else {
            this.color = new Color(237, 194, 46); 
            return this.color; 
        }
        return this.color; 
    }
    public long setClipTime(int num) {
    	 if (num == 2) {
    		 this.clipTime = 0; 
    		 return this.clipTime; 
    	 }
    	 else if (num == 4) {
    		 this.clipTime = 5000000; 
    		 return this.clipTime; 
    	 }
    	 else if (num == 8) {
    		 this.clipTime = 10000000; 
    		 return this.clipTime; 
    	 }
    	 else if (num == 16) {
    		 this.clipTime = 15000000; 
    		 return this.clipTime; 
    	 }
    	 else if (num == 32) {
    		 this.clipTime = 20000000; 
    		 return this.clipTime; 
    	 }
    	 else if (num == 64) {
    		 this.clipTime = 25000000; 
    		 return this.clipTime; 
    	 }
    	 else if (num == 128) {
    		 this.clipTime = 30000000; 
    		 return this.clipTime; 
    	 }
    	 else if (num == 256) {
    		 this.clipTime = 35000000; 
    		 return this.clipTime; 
    	 }
    	 else if (num == 512) {
    		 this.clipTime = 40000000; 
    		 return this.clipTime; 
    	 }
    	 else if (num == 1024) {
    		 this.clipTime = 45000000; 
    		 return this.clipTime; 
    	 }
    	 else {
    		 this.clipTime = 50000000;
    		 return this.clipTime; 
    	 }
    }
}