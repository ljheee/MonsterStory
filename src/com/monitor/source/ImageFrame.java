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
 * ����:���� �������ÿ�����Ϊ����ͼƬ���࣬�����б���ͼƬ�ͳ�ʼ��λ�õ㣬�߿�Ϊ����ͼƬ�ĸ߿�
 **/

public class ImageFrame extends JFrame {

	public JPanel topPanel;

	// �����ֲ����Ĺ����� ������ ����ͼ ͼ�� ��ʼ�����꣩
	public ImageFrame(String title, ImageIcon backImage, Image icon, int x, int y) {
		// ���ͼƬ��frame�ĵڶ���
		JLabel label = new JLabel(backImage);
		label.setBounds(0, 0, backImage.getIconWidth(), backImage.getIconHeight());
		this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
		// ��ȡframe�����ϲ����Ϊ�������䱳����ɫ��JPanel������͸���ķ�����
		JPanel jp = (JPanel) this.getContentPane();
		jp.setOpaque(false);// ����͸��
		this.setTitle(title);
		this.setIconImage(icon);
		this.setBounds(x, y, backImage.getIconWidth(), backImage.getIconHeight());
		this.setVisible(false);
		this.setResizable(false);
		// �����õ�JPanel
		topPanel = new JPanel();
		topPanel.setOpaque(false);
		// ҲҪ����͸��
		topPanel.setLayout(null);

		// ������������
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

	// ͼƬ���صĹ�����
	public static Image getImage(String path) {
		return Toolkit.getDefaultToolkit().getImage(path);
	}

}
