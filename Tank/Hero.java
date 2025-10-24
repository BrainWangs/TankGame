package draw.TankGame.Tank;

import java.util.Vector;


/**
 * 玩家的坦克
 */
public class Hero extends Tank{

    public Vector<Bullet> heroBullets = new Vector<>();

    public Hero(int x, int y, int dir, int type, int speed) {
        super(x, y, dir, 0, speed);
        // setType(0); // 玩家坦克(再次声明防止数据错误)
    }


    /**
     * @description 已将发射子弹的方法封装为tankShot移动到父类Tank中,在子类中为每个子弹对象添加到集合中并启动线程
     * 发射子弹,子弹初始坐标为坦克炮管坐标
     */
    /*Bullet实例的声明应该放在方法外面作为类的全局变量,其声明周期和类的实例相同, 因为该实例需要反复调用*/
    //    public Bullet bullet = null;
    public void heroShot() {
        // 控制发射子弹数量不多于5个
        if (heroBullets.size() >= 5) {
            return;
        }
        // this为当前实例对象,this.tankShot(this)为调用tankShot()方法,传递的this就是heroTank
        // 调用父类的tankShot()方法,会隐式的加上this参数,所以这里可以不写第一个this,但是显示调用更好
        Bullet bullet = this.tankShot(this);
        heroBullets.add(bullet);
        // 启动子弹线程
        new Thread(bullet).start();
    }


}