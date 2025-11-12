package draw.TankGame.Panel;

import draw.TankGame.Tank.Bullet;
import draw.TankGame.Tank.EnemyTank;
import draw.TankGame.Tank.Hero;
import draw.TankGame.Tank.Tank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Vector;

public class MyPanel extends JPanel implements KeyListener , Runnable{
    public static int Width = 1000;
    public static int Height = 750;
    public static int enemyTankNum = 5;

    public Vector<EnemyTank> enemyTanks = new Vector<>();
    public Hero hero = null;
    public ArrayList<Bomb> bombs = new ArrayList<>();

    /**
     * 在构造器内初始化后面会用到的一些集合
     */
    public MyPanel(int select) {
        // Test
        Record.readRecord("record.dat");

        // 初始化坦克的方法最开始放在这里,后续封装到InitMyPanel()方法中
        // 在main中获取输入选择开始新游戏或读取记录进行游戏
        switch (select) {
            case 2:
                initMyPanelByRecord();
                break;
            default:
                initMyPanel();
                break;
        }
    }

    /**
     * @description 用于MyPanel构造器初始化的方法
     */
    // 默认初始化
    public void initMyPanel() {
        // 创建玩家坦克
        hero = new Hero(300, 300, 0, 0, 10);
        // 创建敌人坦克
        EnemyTank enemyTank = null;
        // 利用循环创建敌人坦克, 加入到敌人坦克集合中
        for (int i = 0; i < enemyTankNum; i++) {
            enemyTank = new EnemyTank(100 * (i + 1), 100, 2, 1, 10);
            enemyTanks.add(enemyTank);
            // 启动敌人坦克线程
            new Thread(enemyTank).start();
            // 启动敌人坦克子弹线程....
            /*@说明: 此块原本逻辑是在创建敌人坦克时立刻启动子弹线程, 后续改进为在enemyTank实例线程中随机发射子弹*/
            // 设置enemyTanks对象
            enemyTank.setEnemyTanks(enemyTanks);
            // 初始化爆炸图片
            Bomb.imageInit();
        }
    }

    // 根据record记录的信息进行初始化
    public void initMyPanelByRecord() {
        if (Record.getHero() != null && Record.getEnemyTanks() != null) {
            hero = Record.getHero();
            enemyTanks = Record.getEnemyTanks();
        }
        else {
            initMyPanel();
            return;
        }
        // 通过读取Record方式初始化时,需要手动启动坦克的线程
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            new Thread(enemyTank).start();
            enemyTank.setEnemyTanks(enemyTanks);
            enemyTank.clearAllBullets();
        }
        // 清空集合内子弹,否则上一局的子弹仍然停留在原地
        hero.clearAllBullet();
        Bomb.imageInit();
    }

    /**
     * @description paint()用于绘制图形,窗口中所有图形的生成都基于此方法
     *
     */
    @Override
    public void paint(Graphics g) {
        // 绘制窗口
        super.paint(g);
        // 设置窗口大小
        g.fillRect(0, 0, Width, Height);
        // 设置背景颜色
        g.setColor(Color.BLACK);
        // 绘制相关信息
        showInfo(g);
        // 通过创建对象生成玩家坦克
        // 只有当玩家坦克存活时才绘制
        if (hero.getLive()) {
            drawTank(hero.getX(), hero.getY(), g, hero.getDir(), hero.getType());
        }
        // 遍历绘制敌人坦克集合
        for (int i = 0; i < enemyTanks.size(); i++) {
            drawTank(enemyTanks.get(i).getX(), enemyTanks.get(i).getY(), g, enemyTanks.get(i).getDir(), 1);
        }
        // 绘制玩家坦克的子弹
        for (int i = 0; i < hero.heroBullets.size(); i++) {
            if (hero.heroBullets.get(i).getLive()) {
                g.fillRect(hero.heroBullets.get(i).getX(), hero.heroBullets.get(i).getY(), 2, 2);
            } else {
                // 当子弹死亡时, 移除该子弹
                hero.heroBullets.remove(i);
            }
        }
        // 绘制敌人坦克的子弹
        for (int i = 0; i < enemyTanks.size(); i++) {
            if (enemyTanks.get(i).getLive()) {
                for (int j = 0; j < enemyTanks.get(i).enemyBullets.size(); j++) {
                    if (enemyTanks.get(i).enemyBullets.get(j).getLive()) {
                        g.fillRect(enemyTanks.get(i).enemyBullets.get(j).getX(), enemyTanks.get(i).enemyBullets.get(j).getY(), 2, 2);
                    }
                }
            } else {
                // 当敌人坦克死亡时, 移除该坦克
                enemyTanks.remove(i);
            }
        }
        // 绘制爆炸图片
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);
            if (bomb.getLive()) {
                g.drawImage(Bomb.getImage(bomb), bomb.getX(), bomb.getY(), 60, 60, this);
            } else {
                bombs.remove(i);
            }
        }
    }

    /**
     * @description 编写生成坦克图案的方法
     */
    public void drawTank(int x, int y, Graphics g, int direction, int type) {
        // 绘制坦克颜色
        switch (type) {
            case 0:
                g.setColor(Color.CYAN);
                break;
            case 1:
                g.setColor(Color.YELLOW);
                break;
        }

        // 绘制坦克图形
        switch (direction) {
            case 0:
                g.fill3DRect(x, y, 10, 60, false); // left
                g.fill3DRect(x+30, y, 10, 60, false); // right
                g.fill3DRect(x+10, y+10, 20, 40, false); // body
                g.fillOval(x+10, y+20, 20, 20); // circle
                g.drawLine(x+20, y+30, x+20, y);
                break;

            case 1:
                g.fill3DRect(x, y, 60, 10, false);
                g.fill3DRect(x, y+30, 60, 10, false);
                g.fill3DRect(x+10, y+10, 40, 20, false);
                g.fillOval(x+20, y+10, 20, 20);
                g.drawLine(x+30, y+20, x+60, y+20);
                break;
            case 2:
                g.fill3DRect(x, y, 10, 60, false);
                g.fill3DRect(x+30, y, 10, 60, false);
                g.fill3DRect(x+10, y+10, 20, 40, false);
                g.fillOval(x+10, y+20, 20, 20);
                g.drawLine(x+20, y+30, x+20, y+60);
                break;

            case 3:
                g.fill3DRect(x, y, 60, 10, false);
                g.fill3DRect(x, y+30, 60, 10, false);
                g.fill3DRect(x+10, y+10, 40, 20, false);
                g.fillOval(x+20, y+10, 20, 20);
                g.drawLine(x+30, y+20, x, y+20);
                break;

            default:
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (hero.getLive()) {
            // 顺序是上右下左 0123
            switch (e.getKeyChar()) {
                case 'w':
                    hero.heroMove(0);
                    hero.setDir(0);
                    break;
                case 'd' :
                    hero.heroMove(1);
                    hero.setDir(1);
                    break;
                case 's':
                    hero.heroMove(2);
                    hero.setDir(2);
                    break;
                case 'a':
                    hero.heroMove(3);
                    hero.setDir(3);
                    break;
                default:
                    break;
            }
            // 键盘按下空格键发射子弹
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                hero.heroShot();
            }
        }
        this.repaint();
        /*注意repaint()方法会重新运行paint()方法*/

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }


    /**
     * @description 封装发射子弹的方法(未使用)
     */
    @Deprecated
    public void bulletShot(Tank tank) {
        Bullet bullet = new Bullet(tank.getX(), tank.getY(), tank.getDir());
        if (tank instanceof EnemyTank) {
            ((EnemyTank)tank).enemyBullets.add(bullet);
        }
        if (tank instanceof Hero) {
            ((Hero)tank).heroBullets.add(bullet);
        }
        new Thread(bullet).start();

    }

    /**
     * 判断是否击中敌人的方法
     * @description 判断我方子弹是否击中敌人坦克(双重for, 效率低)
     * @thought 遍历所有玩家坦克的子弹, 遍历所有敌人坦克, 检测子弹坐标是否在敌人坦克区域内
     */
    public void hitEnemyTank() {
        for (int i = 0; i < hero.heroBullets.size(); i++) {
            Bullet bullet = hero.heroBullets.get(i);
            for (int j = 0; j < enemyTanks.size(); j++) {
                EnemyTank enemyTank = enemyTanks.get(j);
                if (bullet.getLive() && enemyTank.getLive()) {
                    // 检测碰撞的静态方法封装在Bullet类中
                    if (Bullet.checkHit(bullet, enemyTank)) {
                        // 从集合中删除这个被击中的坦克对象
                        enemyTanks.remove(j);
                        // 击中坦克的子弹设置为死亡(false)在下一次绘制时,从集合中删除该子弹对象
                        bullet.setLive(false);
                        // 添加bomb对象 绘制爆炸效果
                        bombs.add(new Bomb(enemyTank.getX(), enemyTank.getY()));
                        // 添加记录信息
                        Record.addDestroyedEnemyNum();
                        Record.addScore();

                    }
                }
            }
        }
    }

    /**
     * @description 检测敌人子弹是否击中玩家坦克
     * @thought 遍历所有敌人坦克的子弹, 遍历所有玩家坦克, 检测子弹坐标是否在玩家坦克区域内
     */
    public void hitHeroTank() {
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            for (int j = 0; j < enemyTank.enemyBullets.size(); j++) {
                Bullet bullet = enemyTank.enemyBullets.get(j);
                if (bullet.getLive() && hero.getLive()) {
                    if (Bullet.checkHit(bullet, hero)) {
                        hero.setLive(false);
                        bullet.setLive(false);
                        bombs.add(new Bomb(hero.getX(), hero.getY()));
                        System.out.println("游戏结束");
                    }
                }
            }
        }
    }

    /**
     * @description 创建一个记录窗口,保存成绩
     */
    public void showInfo(Graphics g) {

        g.setColor(Color.BLACK);
        g.setFont(new Font("宋体", Font.BOLD, 25));

        g.drawString("累积击毁的坦克数量", 1020, 30);
        // 在左边画一个黄色坦克作为计数图标
        drawTank(1020, 60, g, 0, 1);
        g.setColor(Color.BLACK);
        // 数字记录显式
        g.drawString(" " + Record.getDestroyedNum(), 1080, 100);
    }

    /**
     * @description 传递引用给Record类,记录hero和enemyTanks的相关信息并序列化
     * 仅当关闭窗口时调用此方法,记录最后一次坐标信息
     */
    public void transferToRecord() {
        Record.setHero(hero);
        Record.setEnemyTanks(enemyTanks);
    }

    /**
     * 在main中启动MyPanel这个线程, 刷新画面
     * @description 包括重绘界面、检测子弹是否击中敌人等功能
     */
    @Override
    public void run() {
        while (true) {
            // 刷新画面
            this.repaint();
            // 检测子弹是否击中敌人坦克
            hitEnemyTank();
            // 检测敌人子弹是否击中玩家坦克
            hitHeroTank();
            // 休眠20ms, 1000/休眠时间 = 刷新率
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}