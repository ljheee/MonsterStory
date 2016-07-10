package com.monitor.objects;

import com.monitor.source.Resources;

public class Hero extends FlyingObject {

	public Hero(int x, int y) {
		super(x, y, 40, 60, Resources.heroPNG);
	}

	public HeroBullet1 fire() {
		HeroBullet1 b1 = new HeroBullet1(this.x + this.width / 2, this.y - 20);
		return b1;
	}

	public HeroBullet2 launch() {
		HeroBullet2 b2 = new HeroBullet2(this.x + this.width / 2, this.y - 20);
		return b2;
	}

	@Override
	public void move(long time) {
	}

	public void moveUp(int d) {
		y -= 10d;
		if (y < 0)
			y += 10d;
	}

	public void moveDown(int d) {
		y += 10d;
		if (y > 600 - 70)
			y -= 10d;
	}

	public void moveLeft(int d) {
		x -= 10d;
		if (x < 0)
			x += 10d;
	}

	public void moveRight(int d) {
		x += 10d;
		if (x > 400 - 45)
			x -= 10d;
	}

}
