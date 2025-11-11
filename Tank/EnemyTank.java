package draw.TankGame.Tank;

import java.util.Vector;

public class EnemyTank extends Tank implements Runnable{
    public Vector<EnemyTank> enemyTanks = null;

    public Vector<EnemyTank> getEnemyTanks() {
        return enemyTanks;
    }

    /**
     * @description 为了在碰撞检测中遍历坦克集合,这里设置set方法在MyPanel中调用
     */
    public void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }

    /**
     * @description Vector存储每一辆敌人坦克的子弹集合
     * 注意是每个敌人坦克都有一个这样的Vector
     */
    public Vector<Bullet> enemyBullets = new Vector<>();

    public EnemyTank(int x, int y, int dir,int type, int speed) {
        super(x, y, dir, 1, speed);
    }

    /**
     * @description 敌人坦克发射子弹
     * 解析见heroTank的heroShot()
     */
    public void enemyShot() {
        Bullet bullet = this.tankShot(this);
        enemyBullets.add(bullet);
        new Thread(bullet).start();
    }

    /**
     * @description 敌人坦克随机发射子弹
     */
    public void randomTimeShot() {
        int randomTime = (int)(Math.random() * 1000);
        if (randomTime % 2 == 0) {
            enemyShot();
        }
    }

    /**
     * @description 敌人坦克随机移动方法,移动逻辑move()定义在父类中
     */
    public void enemyMove() {
        // 随机移动的步数
        int moveTimes = (int)(Math.random() * 50);
        // 随机方向
        int dir = (int)(Math.random() * 4);
        // 先按照原有方向移动, 再随机一个方向
        for (int i = 0; i < moveTimes; i++) {
            // 仅当未发生碰撞时移动
            if (!checkEnemyCollide()) {
                // 调用父类的move()实现移动
                move(this.getDir());
            }
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
     * @description 敌人坦克体积碰撞检测
     *
     */
    public boolean checkEnemyCollide() {
        switch (this.getDir()) {
            // 碰撞者朝上
            case 0:
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    int enemyDirection = enemyTank.getDir();
                    if (enemyTank == this) {
                        continue;
                    }
                    // 被碰撞坦克为上下方向
                    if (enemyDirection == 0 || enemyDirection == 2) {
                        // 检测左上角碰撞
                        if (this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 40
                            && this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getY() + 60) {
                            return true;
                        }
                        // 检测右上角碰撞
                        if (this.getX() + 40 >= enemyTank.getX() && this.getX() + 40 <= enemyTank.getX() + 40
                            && this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getY() + 60) {
                            return true;
                        }
                    }
                    // 被碰撞坦克为左右方向
                    else if (enemyDirection == 1 || enemyDirection == 3) {
                        // 检测左上角碰撞
                        if (this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 60
                            && this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getY() + 40) {
                            return true;
                        }
                        // 检测右上角碰撞
                        if (this.getX() + 40 >= enemyTank.getX() && this.getX() + 40 <= enemyTank.getX() + 60
                            && this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getY() + 40) {
                            return true;
                        }
                    }
                }
            // 碰撞者朝右
            case 1:
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    int enemyDirection = enemyTank.getDir();
                    if (enemyTank == this) {
                        continue;
                    }
                    // 被碰撞坦克为上下方向
                    if (enemyDirection == 0 || enemyDirection == 2) {
                        // 检测右上角
                        if (this.getX() + 60 >= enemyTank.getX() && this.getX() + 60 <= enemyTank.getX() + 40
                            && this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getY() + 60) {
                            return true;
                        }
                        // 检测右下角
                        if (this.getX() + 60 >= enemyTank.getX() && this.getX() + 60 <= enemyTank.getX() + 40
                            && this.getY() + 40 >= enemyTank.getY() && this.getY() + 40 <= enemyTank.getY() + 60) {
                            return true;
                        }
                    }
                    // 被碰撞坦克为左右方向
                    else if (enemyDirection == 1 || enemyDirection == 3) {
                        // 检测右上角
                        if (this.getX() + 60 >= enemyTank.getX() && this.getX() + 60 <= enemyTank.getX() + 60
                            && this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getY() + 40) {
                            return true;
                        }
                        // 检测右下角
                        if (this.getX() + 60 >= enemyTank.getX() && this.getX() + 60 <= enemyTank.getX() + 60
                            && this.getY() + 40 >= enemyTank.getY() && this.getY() + 40 <= enemyTank.getY() + 40) {
                            return true;
                        }
                    }
                }
            // 碰撞者朝下
            case 2:
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    int enemyDirection = enemyTank.getDir();
                    if (enemyTank == this) {
                        continue;
                    }
                    // 被碰撞坦克为上下方向
                    if (enemyDirection == 0 || enemyDirection == 2) {
                        // 检测左下角
                        if (this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 40
                        && this.getY() + 60 >= enemyTank.getY() && this.getY() + 60 <= enemyTank.getY() + 60) {
                            return true;
                        }
                        // 检测右下角
                        if (this.getX() + 40 >= enemyTank.getX() && this.getX() + 40 <= enemyTank.getX() + 40
                            && this.getY() + 60 >= enemyTank.getY() && this.getY() + 60 <= enemyTank.getY() + 60) {
                            return true;
                        }
                    }
                    // 被碰撞坦克为左右方向
                    else if (enemyDirection == 1 || enemyDirection == 3) {
                        // 检测左下角
                        if (this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 60
                        && this.getY() + 60 >= enemyTank.getY() && this.getY() + 60 <= enemyTank.getY() + 40) {
                            return true;
                        }
                        // 检测右下角
                        if (this.getX() + 40 >= enemyTank.getX() && this.getX() + 40 <= enemyTank.getX() + 60
                        && this.getY() + 60 >= enemyTank.getY() && this.getY() + 60 <= enemyTank.getY() + 40) {
                            return true;
                        }
                    }
                }

            // 碰撞者朝左
            case 3:
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    int enemyDirection = enemyTank.getDir();
                    if (enemyTank == this) {
                        continue;
                    }
                    // 被碰撞坦克为上下方向
                    if (enemyDirection == 0 || enemyDirection == 2) {
                        // 检测左上角
                        if (this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 40
                        && this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getY() + 60) {
                            return true;
                        }
                        // 检测左下角
                        if (this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 40
                        && this.getY() + 40 >= enemyTank.getY() && this.getY() + 40 <= enemyTank.getY() + 60) {
                            return true;
                        }
                    }
                    // 被碰撞坦克为左右方向
                    else if (enemyDirection == 1 || enemyDirection == 3) {
                        // 检测左上角
                        if (this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 60
                        && this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getY() + 40) {
                            return true;
                        }
                        // 检测左下角
                        if (this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 60
                        && this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getY() + 40) {
                            return true;
                        }
                    }
                }
        }
        return false;
    }


    /**
     * @description 敌人坦克的线程
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
