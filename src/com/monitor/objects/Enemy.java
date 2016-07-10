package com.monitor.objects;

import java.awt.image.BufferedImage;

public class Enemy extends FlyingObject{

	public Enemy(int x, int y, int width, int height, BufferedImage image) {
		super(x, y, width, height, image);
	}
	
	@Override
	public void move(long time) {
		if (time % 40 == 0) {
			y += 3;
		}
		if (time % 30 == 0) {
			int ex = (int) (4 - Math.random() * 8) + x;
			if (ex > 10 && ex < 360)
				x = ex;
		}

	}

	public EnemyBullet fire(int width, int height, BufferedImage image) {
		int x = this.x + 5;
		int y = this.y + this.height;
		EnemyBullet b = new EnemyBullet(x, y, width, height, image);
		return b;
	}
}
