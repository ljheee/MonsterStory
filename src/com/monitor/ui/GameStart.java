package com.monitor.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.monitor.source.ImageFrame;
import com.monitor.source.Resources;

public class GameStart extends ImageFrame {

	static ImageIcon back = new ImageIcon("Image/gamestart.png");
	ImageIcon icon1 = new ImageIcon("Image/buttonstart.png");
	ImageIcon icon2 = new ImageIcon("Image/buttonmodel.jpg");
	ImageIcon icon3 = new ImageIcon("Image/buttonmodels.jpg");
	// ��Ӱ�ť
	JButton gameStart, gameModel, gameFuntion;
 
	public GameStart() {
		super("��������", back, Resources.tombPNG, 400, 50);
		buttonInit();
		this.setLayout(null);
		this.add(gameStart);
		this.add(gameModel);
		this.add(gameFuntion);

		listener listen = new listener();
		gameStart.addActionListener(listen);
		gameModel.addActionListener(listen);
		gameFuntion.addActionListener(listen);
        this.setVisible(true);
	}

	public void buttonInit() {
		gameStart = new JButton();
		gameStart.setBounds(137, 144, 125, 40);
		gameStart.setIcon(icon1);

		gameStart.setToolTipText("��ʼ��ϷŶ");
		gameStart.setBorderPainted(false); // ȥ����ť�߿�
		gameModel = new JButton();

		gameModel.setBounds(140, 200, 117, 42);
		gameModel.setIcon(icon2);
		gameModel.setToolTipText("�鿴����");
		gameModel.setBorderPainted(false); // ȥ����ť�߿�
		gameFuntion = new JButton();

		gameFuntion.setBounds(142, 258, 117, 36);
		gameFuntion.setIcon(icon3);
		gameFuntion.setToolTipText("�����Ѷ�");
		gameFuntion.setBorderPainted(false); // ȥ����ť�߿�

	}

	class listener implements ActionListener

	{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == gameStart) {
				// ��ʼǰ��¼�����Ϣ
				// String inputValue =
				// JOptionPane.showInputDialog("���´����ٽ�...");
				dispose(); // �رյ�ǰ��Ϸ����
				// ������Ϸ����
				{
					GameFrame frame = new GameFrame();
					frame.setLocation(400, 50);
					frame.setSize(400, 630);
					frame.setIconImage(Resources.tombPNG);
					frame.setTitle("��������");
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setVisible(true);
				}

			} else if (e.getSource() == gameModel) {
				if (JOptionPane.showConfirmDialog(null, "��δ���Ѷ�", "��ʾ", JOptionPane.YES_NO_OPTION) == 0) {
					dispose();
				}
				;

			} else if (e.getSource() == gameFuntion) {
				
			} else {
				System.out.println("����˷����鿴");
			}

		}

	}
}
