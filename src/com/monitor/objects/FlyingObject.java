package com.monitor.objects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

abstract public class FlyingObject {
	public int x;
	public int y;
	protected int width;
	protected int height;
	protected BufferedImage image;
	
	public FlyingObject(int x, int y, int width, int height, BufferedImage image) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.image = image;
	}
	public void move(long time){}
	
	public void draw(Graphics g)
	{
		g.drawImage(image, x, y, width, height, null);
	}
	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
}
