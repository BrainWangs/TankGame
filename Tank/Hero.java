package draw.TankGame.Tank;

import java.util.Vector;


/**
 * 玩家的坦克
 */
public class Hero extends Tank{
    public Vector<Bullet> heroBullet = new Vector<>();

    public Hero(int x, int y, int dir, int type, int speed) {
        super(x, y, dir, 0, speed);
        // setType(0); // 玩家坦克(再次声明防止数据错误)
    }


    /**
     * 发射子弹,子弹初始坐标为坦克炮管坐标
     */
    /*Bullet实例的声明应该放在方法外面作为类的全局变量,其声明周期和类的实例相同, 因为该实例需要反复调用*/
//    public Bullet bullet = null;



    public void heroShot() {
        Bullet bullet = null;
        switch(getDir()) {
            case 0:
                bullet = new Bullet(getX() + 20, getY(), getDir());
                break;
            case 1:
                bullet = new Bullet(getX() + 60, getY() + 20, getDir());
                break;
            case 2:
                bullet = new Bullet(getX() + 20, getY() + 60, getDir());
                break;
            case 3:
                bullet = new Bullet(getX(), getY() + 20, getDir());
                break;
            default:
                break;
        }

        heroBullet.add(bullet);
        // 启动子弹线程
        new Thread(bullet).start();
    }


}