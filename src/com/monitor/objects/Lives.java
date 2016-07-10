package com.monitor.objects;

import com.monitor.source.Resources;

public class Lives extends FlyingObject{

	public Lives(int x, int y) {
		super(x, y, 30, 30, Resources.livesPNG);
	}
}
