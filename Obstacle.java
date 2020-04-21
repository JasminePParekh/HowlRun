import java.awt.image.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.lang.*;
import java.math.*;
import java.awt.image.*;
import java.applet.*;
import javax.swing.border.*;
import javax.imageio.ImageIO;
import java.net.*; 
import java.util.*;



public class Obstacle{

    private Image image;         // Image
    private int x;            // X Position
    private int y;            // Y Position
    private int width;        // Width
    private int height;       // Height
    private int id;
    private boolean bool;
    private Rectangle hitbox;  // Hitbox

    public Obstacle(Image image, boolean bool, int id, int x, int y, int width, int height) {
    	this.image = image;
    	this.id = id;
    	this.bool = bool;
    	this.x = x;
    	this.y = y;
    	this.width = width;
    	this.height = height;
        if(image!=null){
            this.hitbox = new Rectangle(x,y,width,height);
        }



    }

    // Getters & Setters
    public Image getImage() {
        return image;
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public boolean getBool(){
    	return bool;
    }
    public void setBool(boolean bool){
    	this.bool = bool; 
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public Rectangle getHitbox() {
        return hitbox;
    }
    public void setHitbox() {
        hitbox = new Rectangle(x,y,width,height);
    }
    public String toString(){
        String idd = Integer.toString(id);
        return idd;
    }


}