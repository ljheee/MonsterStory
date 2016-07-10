package com.monitor.ui;

import java.awt.BorderLayout;
import javax.swing.JFrame;

import com.monitor.back.GameCanvas;
import com.monitor.source.Resources;



@SuppressWarnings("serial")
public class GameFrame extends JFrame {
	private GameCanvas canvas = new GameCanvas();

	public GameFrame() {
		canvas.setFocusable(true);
		canvas.requestFocus();
		this.add(canvas, BorderLayout.CENTER);
	}


}
