package com.monitor.objects;

import com.monitor.source.Resources;

public class AddLife extends FlyingObject{

	public AddLife(int x, int y) {
		super(x, y, 30, 30, Resources.addlifePNG);
	}
	
	@Override
	public void move(long time) {
		if (time % 40 == 0) {
			y += 4;
		}
		if (time % 40 == 0) {
			int ax = (int) (4 - Math.random() * 8) + x;
			if (ax > 10 && ax < 400 - 50)
				x = ax;
		}

	}
}
