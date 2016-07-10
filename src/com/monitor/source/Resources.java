package com.monitor.source;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Resources {
 
	static {
		try {
			enemy1PNG = ImageIO.read(Resources.class.getResourceAsStream("Image/en1.png"));
			enemy2PNG = ImageIO.read(Resources.class
					.getResourceAsStream("/en2.png"));
			enemy3PNG = ImageIO.read(Resources.class
					.getResourceAsStream("/en3.png"));
			startPNG = ImageIO.read(Resources.class
					.getResourceAsStream("/gamestart.png"));
			bossPNG = ImageIO.read(Resources.class
					.getResourceAsStream("/boss.png"));

			ebullet1PNG = ImageIO.read(Resources.class
					.getResourceAsStream("/ebul1.png"));
			ebullet2PNG = ImageIO.read(Resources.class
					.getResourceAsStream("/ebul2.png"));
			ebullet3PNG = ImageIO.read(Resources.class
					.getResourceAsStream("/ebul3.png"));

			heroPNG = ImageIO.read(Resources.class
					.getResourceAsStream("/hero.png"));
			hbullet1PNG = ImageIO.read(Resources.class
					.getResourceAsStream("/hbul1.png"));
			hbullet2PNG = ImageIO.read(Resources.class
					.getResourceAsStream("/hbul2.png"));

			livesPNG = ImageIO.read(Resources.class
					.getResourceAsStream("/lives.png"));
			addlifePNG = ImageIO.read(Resources.class
					.getResourceAsStream("/addlife.png"));

			background1JPG = ImageIO.read(Resources.class
					.getResourceAsStream("/background1.jpg"));
			background2JPG = ImageIO.read(Resources.class
					.getResourceAsStream("/background2.jpg"));
			background3JPG = ImageIO.read(Resources.class
					.getResourceAsStream("/background3.jpg"));

			tombPNG = ImageIO.read(Resources.class
					.getResourceAsStream("/tomb.png"));
			gameoverPNG = ImageIO.read(Resources.class
					.getResourceAsStream("/gameover.png"));
			congratulationPNG = ImageIO.read(Resources.class
					.getResourceAsStream("/congratulation.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static BufferedImage enemy1PNG;
	public static BufferedImage enemy2PNG;
	public static BufferedImage enemy3PNG;
	public static BufferedImage startPNG;
	public static BufferedImage bossPNG;

	public static BufferedImage ebullet1PNG;
	public static BufferedImage ebullet2PNG;
	public static BufferedImage ebullet3PNG;

	public static BufferedImage heroPNG;
	public static BufferedImage hbullet1PNG;
	public static BufferedImage hbullet2PNG;

	public static BufferedImage livesPNG;
	public static BufferedImage addlifePNG;

	public static BufferedImage background1JPG;
	public static BufferedImage background2JPG;
	public static BufferedImage background3JPG;

	public static BufferedImage tombPNG;
	public static BufferedImage gameoverPNG;
	public static BufferedImage congratulationPNG;
}
