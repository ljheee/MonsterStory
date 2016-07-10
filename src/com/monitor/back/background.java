package com.monitor.back;

import java.awt.image.BufferedImage;

import com.monitor.objects.FlyingObject;

public class background extends FlyingObject {
	public background(int x, int y, BufferedImage image) {
		super(x, y, 400, 600, image);
	}

	@Override
	public void move(long time) {
		// TODO Auto-generated method stub	
	}
}
