package draw.TankGame.Tank;

import static draw.TankGame.Panel.MyPanel.Height;
import static draw.TankGame.Panel.MyPanel.Width;

/*绘制子弹的移动效果*/
public class Bullet implements Runnable{
    private int x;
    private int y;
    private int speed = 10;
    private int dir;
    private boolean isLive = true; // 子弹存活状态

    public Bullet(int x, int y, int dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public boolean getLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    /*子弹移动, 上右下左*/
    public void move(int dir) {
        switch (dir) {
            case 0:
                this.y -= speed;
                break;
            case 1:
                this.x += speed;
                break;
            case 2:
                this.y += speed;
                break;
            case 3:
                this.x -= speed;
                break;
            default:
                break;
        }
    }

    /*子弹碰撞边界检测*/
    public void checkBorderHit() {
        if (x < 0 || x >= Width || y < 0 || y >= Height) {
            isLive = false;
        }
    }

    /**
     * @description 检测子弹是否与tank碰撞
     * 共用于敌人子弹和玩家子弹的碰撞检测
     * */
    public static boolean checkHit(Bullet bullet, Tank tank) {
        if (bullet != null && tank != null && bullet.getLive()) {
            switch (tank.getDir()) {
                case 0:
                case 2:
                    if (bullet.getX() >= tank.getX() && bullet.getX() <= tank.getX() + 40 &&
                        bullet.getY() >= tank.getY() && bullet.getY() <= tank.getY() + 60) {
                            bullet.setLive(false);
                            tank.setLive(false);
                            return true;
                    }
                    break;
                case 1:
                    if (bullet.getX() >= tank.getX() && bullet.getX() <= tank.getX() + 60 &&
                        bullet.getY() >= tank.getY() && bullet.getY() <= tank.getY() + 20) {
                            bullet.setLive(false);
                            tank.setLive(false);
                            return true;
                    }
                    break;
                case 3:
                    if (bullet.getX() >= tank.getX() && bullet.getX() <= tank.getX() + 20 &&
                        bullet.getY() >= tank.getY() && bullet.getY() <= tank.getY() + 60) {
                            bullet.setLive(false);
                            tank.setLive(false);
                            return true;
                    }
                    break;
                default:
                    break;
            }
        }
        return false;
    }


    /**
     * @description 该线程只要启动bullet就会不断运行更新子弹坐标,直到死亡
     */
    @Override
    public void run() {
        while (true) {
            // 移动bullet
            move(dir);
//            System.out.println("子弹的坐标: " + x + " " + y);
            
            
            // 休眠50毫秒
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 边界检测
            checkBorderHit();
            if (!isLive) {
                System.out.println("子弹死亡 " + Thread.currentThread());
                break;
            }

        }
    }
}