package com.monitor.objects;

import com.monitor.source.Resources;

public class Boss extends FlyingObject {

	public Boss(int x, int y) {
		super(x, y, 90, 90, Resources.bossPNG);
	}

	public void move(long time) {
		if (time % 40 == 0) {
			y += 2;
		}
		if (time % 30 == 0) {
			int ex = (int) (4 - Math.random() * 8) + x;
			if (ex > 10 && ex < 300)
				x = ex;
		}
	}
}
