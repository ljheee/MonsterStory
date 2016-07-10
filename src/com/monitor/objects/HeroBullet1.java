package com.monitor.objects;

import com.monitor.source.Resources;

public class HeroBullet1 extends FlyingObject {

	public HeroBullet1(int x, int y) {
		super(x, y, 20, 20, Resources.hbullet1PNG);

	}

	@Override
	public void move(long time) {
		if (time % 40 == 0) {
				y -= 4;
		}
	}

}
