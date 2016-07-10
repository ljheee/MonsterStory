package com.monitor.source;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 作者:廖涛 本类作用可以作为背景图片父类，并带有标题图片和初始化位置点，高宽为带入图片的高宽
 **/

public class ImageFrame extends JFrame {

	public JPanel topPanel;

	// 带各种参数的构造器 （标题 背景图 图标 起始点坐标）
	public ImageFrame(String title, ImageIcon backImage, Image icon, int x, int y) {
		// 添加图片到frame的第二层
		JLabel label = new JLabel(backImage);
		label.setBounds(0, 0, backImage.getIconWidth(), backImage.getIconHeight());
		this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
		// 获取frame的最上层面板为了设置其背景颜色（JPanel有设置透明的方法）
		JPanel jp = (JPanel) this.getContentPane();
		jp.setOpaque(false);// 设置透明
		this.setTitle(title);
		this.setIconImage(icon);
		this.setBounds(x, y, backImage.getIconWidth(), backImage.getIconHeight());
		this.setVisible(false);
		this.setResizable(false);
		// 测试用的JPanel
		topPanel = new JPanel();
		topPanel.setOpaque(false);
		// 也要让他透明
		topPanel.setLayout(null);

		// 参数构建方法
		// ImageIcon bg = new ImageIcon("picture/small_back1.jpg");
		// Image ico =
		// Toolkit.getDefaultToolkit().getImage("picture/small_back1.jpg");

	}

	public ImageFrame(String title, Image icon, int x, int y, int cx, int cy) {

		this.setTitle(title);
		this.setIconImage(icon);
		this.setBounds(x, y, cx, cy);
		this.setVisible(true);
		this.setResizable(false);
	}

	public ImageFrame() {
	}

	// 图片加载的工具类
	public static Image getImage(String path) {
		return Toolkit.getDefaultToolkit().getImage(path);
	}

}
