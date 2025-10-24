package draw.TankGame.Tank;

import java.util.Vector;

public class EnemyTank extends Tank implements Runnable{

    /**
     * Vector存储每一辆敌人坦克的子弹集合
     * 注意是每个敌人坦克都有一个这样的Vector
     */
    public Vector<Bullet> enemyBullets = new Vector<>();

    public EnemyTank(int x, int y, int dir,int type, int speed) {
        super(x, y, dir, 1, speed);
    }

    /**
     * 敌人坦克发射子弹
     * 解析见heroTank的heroShot()
     */
    public void enemyShot() {
        Bullet bullet = this.tankShot(this);
        enemyBullets.add(bullet);
        new Thread(bullet).start();
    }

    public void randomTimeShot() {
        int randomTime = (int)(Math.random() * 1000);
        if (randomTime % 2 == 0) {
            enemyShot();
        }
    }

    /**
     * @description 敌人坦克随机移动方法
     *
     */
    public void enemyMove() {
        // 随机移动的步数
        int moveTimes = (int)(Math.random() * 50);
        // 随机方向
        int dir = (int)(Math.random() * 4);
        // 先按照原有方向移动, 再随机一个方向
        for (int i = 0; i < moveTimes; i++) {
            move(this.getDir());
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        // 随机数 0 - 3
        setDir(dir);
    }


    /**
     * @description 这个线程控制敌人坦克的随机移动
     */
    @Override
    public void run() {
        while (true) {
            enemyMove();
            randomTimeShot();
            try {
                // 坦克在一次转向之前的等待时间
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (!getLive()) {
                System.out.println("敌人坦克死亡 " + Thread.currentThread());
                break;
            }
        }
    }

}
