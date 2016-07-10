package com.monitor.objects;

import java.awt.image.BufferedImage;

public class EnemyBullet extends FlyingObject{

	public EnemyBullet(int x, int y, int width, int height, BufferedImage image) {
		super(x, y, width, height, image);
	}

	@Override
	public void move(long time) {
		if (time % 20 == 0) {
				y += 10;
		}
	}
}
