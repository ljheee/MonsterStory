package com.monitor.back;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.monitor.objects.AddLife;
import com.monitor.objects.Boss;
import com.monitor.objects.Enemy;
import com.monitor.objects.EnemyBullet;
import com.monitor.objects.Hero;
import com.monitor.objects.HeroBullet1;
import com.monitor.objects.HeroBullet2;
import com.monitor.objects.Lives;
import com.monitor.source.Resources;
import com.monitor.ui.GameOver;


@SuppressWarnings("serial")
public class GameCanvas extends JPanel implements KeyListener {

	private List<Lives> lives = new ArrayList<Lives>();// 生命计数图标
	private List<AddLife> addlife = new ArrayList<AddLife>();// 生命+1道具

	// 敌淫们
	private List<Enemy> enemies1 = new ArrayList<Enemy>();
	private List<Enemy> enemies2 = new ArrayList<Enemy>();
	private List<Enemy> enemies3 = new ArrayList<Enemy>();
	// boss
	private Boss boss;
	private int bossBlood = 2000;

	// 敌方炮弹
	private List<EnemyBullet> ebul1 = new ArrayList<EnemyBullet>();
	private List<EnemyBullet> ebul2 = new ArrayList<EnemyBullet>();
	private List<EnemyBullet> ebul3 = new ArrayList<EnemyBullet>();

	// 我方
	private Hero hero;
	private List<HeroBullet1> hbul1 = new ArrayList<HeroBullet1>();// 我方炮弹
	private List<HeroBullet2> hbul2 = new ArrayList<HeroBullet2>();// 我方【导弹】

	private background Bg;
	private GameOver gameOver;// 游戏结束大贴纸

	private int life = 0;// 【损失】生命的次数（不是生命值）
	private int money = 0;
	private int score = 0;
	private int stage = 1;

	private boolean vkUp = false;// hero方向控制
	private boolean vkDown = false;
	private boolean vkLeft = false;
	private boolean vkRight = false;
	private boolean vkFire = false;
	private boolean vkLaunch = false;

	Font fn = new Font("Microsoft Yahei", Font.BOLD, 20);

	public GameCanvas() {
		this.addKeyListener(this);
		// boss score>2000时会刷出来 bossBlood<=0就消失
		boss = new Boss(150, 50);

		// 不同关卡（1-3 ）不同背景
		hero = new Hero(200, 500);
		if (score < 200)
			Bg = new background(0, 0, Resources.background1JPG);
		else if (score < 600)
			Bg = new background(0, 0, Resources.background2JPG);
		else
			Bg = new background(0, 0, Resources.background3JPG);

		// 游戏开始 放enemies1
		for (int i = 0; i < 3; i++) {
			Enemy e = new Enemy((int) (Math.random() * 360), 0, 30, 30,
					Resources.enemy1PNG);
			enemies1.add(e);
		}
		// 游戏开始 放lives
		for (int i = 0; i < 5 - life; i++) {
			lives.add(new Lives(10 + i * 30, 10));
		}
		// 线程部分
		Thread animate = new Thread() {
			public void run() {
				long time = 0;
				while (true) {
					if (score < 200)
						stage = 1;
					else if (score < 800)
						stage = 2;
					else
						stage = 3;

					if (stage == 1)
						Bg = new background(0, 0, Resources.background1JPG);
					else if (stage == 2)
						Bg = new background(0, 0, Resources.background2JPG);
					else
						Bg = new background(0, 0, Resources.background3JPG);

					// 每个enemy每隔一段时间移动一次
					EnemyMove(time);
					// enemies与hero碰撞
					for (int i = 0; i < enemies1.size(); i++) {
						Enemy1VSHero(i);
					}
					for (int i = 0; i < enemies2.size(); i++) {
						Enemy2VSHero(i);
					}
					for (int i = 0; i < enemies3.size(); i++) {
						Enemy3VSHero(i);
					}

					for (int i = 0; i < ebul1.size(); i++) {
						// ebul1每隔一段时间移动一次
						ebul1.get(i).move(time);
						// ebul1碰撞hero
						enemyBullet1VSHero(i);
					}

					for (int i = 0; i < ebul2.size(); i++) {
						// ebul2每隔一段时间移动一次
						ebul2.get(i).move(time);
						// ebul2碰撞hero
						enemyBullet2VSHero(i);
					}

					for (int i = 0; i < ebul3.size(); i++) {
						// ebul3每隔一段时间移动一次
						ebul3.get(i).move(time);
						// ebul3碰撞hero
						enemyBullet3VSHero(i);
					}

					for (int i = 0; i < hbul1.size(); i++) {
						// hbul1每隔一段时间移动一次
						hbul1.get(i).move(time);
						// hbul1碰撞enemy
						heroBullet1VSEnemy(i);
					}

					// hbul2碰撞enemy
					for (int i = 0; i < hbul2.size(); i++) {
						if (time % 10 == 0)
							hbul2.get(i).y -= 10;
						heroBullet2VSEnemy(i, time);
					}

					// 每隔一段时间 enemy fire ++
					enemyFireBullet(time);
					addNewEnemy(time);

					// 每隔一段时间 addlife道具 ++ move
					addNewLife(time);
					AddLifeMove(time);

					// addlife道具碰撞hero
					for (int i = 0; i < addlife.size(); i++) {
						addLifeVSHero(i);
					}

					if (vkLeft)
						hero.moveLeft(2);
					if (vkUp)
						hero.moveUp(2);
					if (vkRight)
						hero.moveRight(2);
					if (vkDown)
						hero.moveDown(2);
					if (vkFire && time % 120 == 0) {// 设置发射间隔0.12s
						HeroBullet1 b1 = hero.fire();
						hbul1.add(b1);
					}
					if (vkLaunch && time % 120 == 0) {
						HeroBullet2 b2 = hero.launch();
						hbul2.add(b2);
					}

					time += 40;
					// 判断游戏是否结束
					if (life >= 5) {
						try {
							repaint();
							while (life >= 5)
								sleep(40);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

					if (bossBlood < 0) {
						try {
							repaint();
							while (bossBlood < 0)
								sleep(40);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

					repaint();
					try {
						Thread.sleep(40);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			private void addNewEnemy(long time) {
				if (score < 1000 && time % 1000 == 0) {
					if (score < 200 && enemies1.size() < 7) {
						Enemy e1 = new Enemy((int) (Math.random() * 360), 0,
								30, 30, Resources.enemy1PNG);
						enemies1.add(e1);
					} else if (score < 600 && enemies2.size() < 7) {
						Enemy e2 = new Enemy((int) (Math.random() * 360), 0,
								40, 40, Resources.enemy2PNG);
						enemies2.add(e2);
					} else if (enemies3.size() < 7) {
						Enemy e3 = new Enemy((int) (Math.random() * 360), 0,
								40, 40, Resources.enemy3PNG);
						enemies3.add(e3);
					}
				} else if (score < 2000) {
					if (time % 1500 == 0) {
						Enemy e1 = new Enemy((int) (Math.random() * 360), 0,
								30, 30, Resources.enemy1PNG);
						enemies1.add(e1);
					} else if (time % 2000 == 0) {
						Enemy e2 = new Enemy((int) (Math.random() * 360), 0,
								40, 40, Resources.enemy2PNG);
						enemies2.add(e2);
					} else if (time % 2500 == 0) {
						Enemy e3 = new Enemy((int) (Math.random() * 360), 0,
								40, 40, Resources.enemy3PNG);
						enemies3.add(e3);
					}
				} else if (score >= 2000 && bossBlood >= 0) {// 放boss
					if (time % 6000 == 0) {
						boss = new Boss(150, 50);
						boss.move(time);
						// 小怪列队
						for (int i = 0; i < 3; i++) {
							Enemy e1 = new Enemy(boss.x + 50 * i, boss.y + 100,
									30, 30, Resources.enemy1PNG);
							enemies1.add(e1);
						}
						for (int i = 0; i < 3; i++) {
							Enemy e2 = new Enemy(boss.x + 50 * i, boss.y + 150,
									40, 40, Resources.enemy2PNG);
							enemies2.add(e2);
						}
						for (int i = 0; i < 3; i++) {
							Enemy e3 = new Enemy(boss.x + 50 * i, boss.y + 200,
									40, 40, Resources.enemy3PNG);
							enemies3.add(e3);
						}
					}
				}
			}

			private void addNewLife(long time) {
				if (time % 10000 == 0) {
					AddLife a = new AddLife((int) (Math.random() * 360), 0);
					addlife.add(a);
				}
			}

			private void enemyFireBullet(long time) {
				if (time % 8000 == 0) {
					for (Enemy e1 : enemies1) {
						EnemyBullet b1 = e1.fire(20, 20, Resources.ebullet1PNG);
						ebul1.add(b1);
					}
				}
				if (time % 6000 == 0) {
					for (Enemy e2 : enemies2) {
						EnemyBullet b2 = e2.fire(20, 20, Resources.ebullet2PNG);
						ebul2.add(b2);
					}
				}
				if (time % 7000 == 0) {
					for (Enemy e3 : enemies3) {
						EnemyBullet b3 = e3.fire(30, 20, Resources.ebullet3PNG);
						ebul3.add(b3);
					}
				}
			}

			private void EnemyMove(long time) {
				for (Enemy e1 : enemies1) {
					e1.move(time);
				}
				for (Enemy e2 : enemies2) {
					e2.move(time);
				}
				for (Enemy e3 : enemies3) {
					e3.move(time);
				}
			}

			private void Enemy1VSHero(int i) {
				int hx = hero.getX();
				int hy = hero.getY();
				int ex = enemies1.get(i).x;
				int ey = enemies1.get(i).y;
				if (hx - ex < 30 && ex - hx < 40 && hy - ey < 30
						&& ey - hy < 60) {
					enemies1.remove(enemies1.get(i));
					life++;
					if (5 - life >= 0 && 5 - life < lives.size())
						lives.remove(5 - life);
				}
				if (ey > 600) {
					enemies1.remove(enemies1.get(i));
				}
			}

			private void Enemy2VSHero(int i) {
				int hx = hero.getX();
				int hy = hero.getY();
				int ex = enemies2.get(i).x;
				int ey = enemies2.get(i).y;
				if (hx - ex < 40 && ex - hx < 40 && hy - ey < 40
						&& ey - hy < 60) {
					enemies2.remove(enemies2.get(i));
					life++;
					if (5 - life >= 0 && 5 - life < lives.size())
						lives.remove(5 - life);
				}
				if (ey > 600) {
					enemies2.remove(enemies2.get(i));
				}
			}

			private void Enemy3VSHero(int i) {
				int hx = hero.getX();
				int hy = hero.getY();
				int ex = enemies3.get(i).x;
				int ey = enemies3.get(i).y;
				if (hx - ex < 40 && ex - hx < 40 && hy - ey < 40
						&& ey - hy < 60) {
					enemies3.remove(enemies3.get(i));
					life++;
					if (5 - life >= 0 && 5 - life < lives.size())
						lives.remove(5 - life);
				}
				if (ey > 600) {
					enemies3.remove(enemies3.get(i));
				}
			}

			private void AddLifeMove(long time) {
				for (AddLife a : addlife) {
					a.move(time);
				}
			}

			private void enemyBullet1VSHero(int i) {
				int hx = hero.getX();
				int hy = hero.getY();
				int bx = ebul1.get(i).getX();
				int by = ebul1.get(i).getY();
				if (hx - bx < 10 && bx - hx < 30 && hy - by < 10
						&& by - hy < 50) {
					ebul1.remove(ebul1.get(i));
					life++;
					if (5 - life >= 0 && 5 - life < lives.size())
						lives.remove(5 - life);
				}
				if (by > 600) {
					ebul1.remove(ebul1.get(i));
				}
			}

			private void enemyBullet2VSHero(int i) {
				int hx = hero.getX();
				int hy = hero.getY();
				int bx = ebul2.get(i).getX();
				int by = ebul2.get(i).getY();
				if (hx - bx < 10 && bx - hx < 30 && hy - by < 10
						&& by - hy < 50) {
					ebul2.remove(ebul2.get(i));
					life++;
					if (5 - life >= 0 && 5 - life < lives.size())
						lives.remove(5 - life);
				}
				if (by > 600) {
					ebul2.remove(ebul2.get(i));
				}
			}

			private void enemyBullet3VSHero(int i) {
				int hx = hero.getX();
				int hy = hero.getY();
				int bx = ebul3.get(i).getX();
				int by = ebul3.get(i).getY();
				if (hx - bx < 20 && bx - hx < 20 && hy - by < 10
						&& by - hy < 50) {
					ebul3.remove(ebul3.get(i));
					life++;
					if (5 - life >= 0 && 5 - life < lives.size())
						lives.remove(5 - life);
				}
				if (by > 600) {
					ebul3.remove(ebul3.get(i));
				}
			}

			private void heroBullet1VSEnemy(int i) {
				int hx = hbul1.get(i).getX();
				int hy = hbul1.get(i).getY();
				if (hy < 0) {
					hbul1.remove(hbul1.get(i));
					return;
				}
				boolean isCovered = true;
				// enemy1
				for (int j = 0; j < enemies1.size(); j++) {
					int ex = enemies1.get(j).getX();
					int ey = enemies1.get(j).getY();
					if (ex - hx < 15 && hx - ex < 30 && ey - hy < 70
							&& hy - ey < 30) {
						enemies1.remove(enemies1.get(j));
						if (isCovered) {
							hbul1.remove(hbul1.get(i));
							isCovered = false;
						}
						money += 23;
						score += 20;
					}

				}
				// enemy2
				for (int j = 0; j < enemies2.size(); j++) {
					int ex = enemies2.get(j).getX();
					int ey = enemies2.get(j).getY();
					if (ex - hx < 15 && hx - ex < 40 && ey - hy < 70
							&& hy - ey < 40) {
						enemies2.remove(enemies2.get(j));
						if (isCovered) {
							hbul1.remove(hbul1.get(i));
							isCovered = false;
						}
						money += 39;
						score += 50;
					}

				}
				// enemy3
				for (int j = 0; j < enemies3.size(); j++) {
					int ex = enemies3.get(j).getX();
					int ey = enemies3.get(j).getY();
					if (ex - hx < 15 && hx - ex < 40 && ey - hy < 70
							&& hy - ey < 40) {
						enemies3.remove(enemies3.get(j));
						if (isCovered) {
							hbul1.remove(hbul1.get(i));
							isCovered = false;
						}
						money += 67;
						score += 70;
					}

				}

				// boss
				if (score > 2000 && bossBlood >= 0) {
					if (hx > 150 && hx < 240 && hy > 50 && hy < 140) {
						if (isCovered) {
							hbul1.remove(hbul1.get(i));
							isCovered = false;
						}
						money += 99;
						score += 100;
						bossBlood -= 40;
					}
				}
			}

			private void heroBullet2VSEnemy(int i, long time) {
				int hx = hbul2.get(i).getX();
				int hy = hbul2.get(i).getY();
				if (hy < 0) {
					hbul2.remove(hbul2.get(i));
					return;
				}
				// enemy1
				for (int j = 0; j < enemies1.size(); j++) {
					int ex = enemies1.get(j).getX();
					int ey = enemies1.get(j).getY();
					if (ex - hx < 25 && hx - ex < 40) {
						hbul2.get(i).move(time, ex, ey);
						if (ey - hy < 70 && hy - ey < 40) {
							enemies1.remove(enemies1.get(j));
							money += 23;
							score += 30;
						}
					}

				}
				// enemy2
				for (int j = 0; j < enemies2.size(); j++) {
					int ex = enemies2.get(j).getX();
					int ey = enemies2.get(j).getY();
					if (ex - hx < 25 && hx - ex < 50) {
						hbul2.get(i).move(time, ex, ey);
						if (ey - hy < 70 && hy - ey < 50) {
							enemies2.remove(enemies2.get(j));
							money += 39;
							score += 50;
						}
					}

				}
				// enemy3
				for (int j = 0; j < enemies3.size(); j++) {
					int ex = enemies3.get(j).getX();
					int ey = enemies3.get(j).getY();
					if (ex - hx < 25 && hx - ex < 50) {
						hbul2.get(i).move(time, ex, ey);
						if (ey - hy < 70 && hy - ey < 50) {
							enemies3.remove(enemies3.get(j));
							money += 67;
							score += 70;
						}
					}
				}

				// boss
				if (score > 2000 && bossBlood > 0) {
					if (hx > 150 && hx < 240 && hy > 50 && hy < 140) {
						hbul2.remove(hbul2.get(i));
						money += 99;
						score += 100;
						bossBlood -= 40;
					}
				}
			}

			private void addLifeVSHero(int i) {
				int hx = hero.getX();
				int hy = hero.getY();
				int ax = addlife.get(i).getX();
				int ay = addlife.get(i).getY();
				if (hx - ax < 20 && ax - hx < 40 && hy - ay < 20
						&& ay - hy < 60) {
					addlife.remove(addlife.get(i));
					score += 20;
					lives.add(new Lives(10 + lives.size() * 30, 10));
					life--;// 损失生命的次数-1
				}
				if (ay > 600) {
					addlife.remove(addlife.get(i));
				}
			}

		};
		animate.start();
	}

	public void paint(Graphics g) {
		super.paint(g);
		Bg.draw(g);

		g.setFont(fn);
		g.setColor(Color.white);
		g.drawString("分数:" + score, 10, 60);
		g.drawString("金钱:" + money, 10, 85);
		if (score < 2000)
			g.drawString("关卡:" + stage, 10, 110);
		else
			g.drawString("Boss 状态", 10, 110);

		for (Lives l : lives)
			l.draw(g);

		for (AddLife a : addlife)
			a.draw(g);

		for (Enemy e1 : enemies1)
			e1.draw(g);

		for (Enemy e2 : enemies2)
			e2.draw(g);

		for (Enemy e3 : enemies3)
			e3.draw(g);

		for (EnemyBullet e1 : ebul1)
			e1.draw(g);

		for (EnemyBullet e2 : ebul2)
			e2.draw(g);

		for (EnemyBullet e3 : ebul3)
			e3.draw(g);

		for (HeroBullet1 h1 : hbul1)
			h1.draw(g);

		for (HeroBullet2 h2 : hbul2)
			h2.draw(g);

		hero.draw(g);

		if (score >= 2000) {
			if (bossBlood >= 0) {
				boss.draw(g);
				g.setColor(Color.red);
				g.drawString("Boss血量:", 10, 135);
				g.drawString(bossBlood + "/2000", 10, 160);
			} else {
				gameOver = new GameOver(0, 0, Resources.congratulationPNG);
				gameOver.draw(g);
			}
		}

		if (life >= 5) {
			gameOver = new GameOver(0, 0, Resources.gameoverPNG);
			gameOver.draw(g);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			vkLeft = true;
			break;
		case KeyEvent.VK_RIGHT:
			vkRight = true;
			break;
		case KeyEvent.VK_UP:
			vkUp = true;
			break;
		case KeyEvent.VK_DOWN:
			vkDown = true;
			break;
		case KeyEvent.VK_SPACE:
			vkFire = true;
			break;
		case KeyEvent.VK_Z:
			if (money >= 200 && score > 2000) {
				vkLaunch = true;
				money -= 200;
			}
			break;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			vkLeft = false;
			break;
		case KeyEvent.VK_RIGHT:
			vkRight = false;
			break;
		case KeyEvent.VK_UP:
			vkUp = false;
			break;
		case KeyEvent.VK_DOWN:
			vkDown = false;
			break;
		case KeyEvent.VK_SPACE:
			vkFire = false;
			break;
		case KeyEvent.VK_Z:
			vkLaunch = false;
			break;

		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
