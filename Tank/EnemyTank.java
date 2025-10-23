package draw.TankGame.Tank;

import java.util.Vector;

public class EnemyTank extends Tank{

    /**
     * Vector存储每一辆敌人坦克的子弹集合
     * 注意是每个敌人坦克都有一个这样的Vector
     */
    public Vector<Bullet> enemyBullet = new Vector<>();


    public EnemyTank(int x, int y, int dir,int type, int speed) {
        super(x, y, dir, 1, speed);
    }




}
