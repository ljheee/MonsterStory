package com.monitor.objects;

import com.monitor.source.Resources;

public class HeroBullet2 extends FlyingObject {

	public HeroBullet2(int x, int y) {
		super(x, y, 15, 70, Resources.hbullet2PNG);

	}

	public void move(long time, int ex, int ey) {
		if (time % 40 == 0) {
			if (ex < x)
				x -= 6;
			else
				x += 6;
		}
	}

}
