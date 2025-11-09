package draw.TankGame.Tank;

import static draw.TankGame.Panel.MyPanel.Height;
import static draw.TankGame.Panel.MyPanel.Width;

public class Tank {
    private int x;
    private int y;
    private int dir;
    private int type;
    private int speed;
    private boolean isLive = true;

    public Tank(int x, int y, int dir, int type, int speed) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.type = type;
        this.speed = speed;
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

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean getLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }


    /**
     * 封装方法: 移动坦克
     * @description 可以供给所有坦克类对象的移动.在每次移动前检测是否越界,越界则恢复原位置
     */
    public void move(int dir) {
        // 保存当前位置
        int originalX = this.x;
        int originalY = this.y;
        
        // 临时移动到新位置进行检测
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
        
        // 检查新位置是否越界，如果越界则恢复原位置
        if (checkHit()) {
            this.x = originalX;
            this.y = originalY;
        }
    }

    /**
     * 封装方法: 发射子弹
     * @description  由于每个坦克发射的子弹都不同,所以不能在Tank类中定义,需要在子类中定义,这里仅作为对子弹方向判断的封装
     * @param tank 接收坦克对象,根据dir属性创建bullet对象
     * @return 返回创建的子弹对象
     */
    public Bullet tankShot(Tank tank) {
        Bullet bullet = null;
        switch(tank.getDir()) {
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

        return bullet;
    }

    /**
     * 封装方法: 边界检测
     * @description 坦克移动时,判断是否越界,越界则返回true,否则返回false
     */
    public boolean checkHit() {
        // 根据坦克方向使用不同的尺寸进行边界检测
        switch (dir) {
            // 上下方向 (垂直) - 尺寸 40x60
            case 0:  // 上
            case 2:  // 下
                if (x < 0 || x + 40 > Width || y < 0 || y + 60 > Height) {
                    return true;
                }
                break;
            // 左右方向 (水平) - 尺寸 60x40
            case 1:  // 右
            case 3:  // 左
                if (x < 0 || x + 60 > Width || y < 0 || y + 40 > Height) {
                    return true;
                }
                break;
            default:
                break;
        }
        return false;
    }

}