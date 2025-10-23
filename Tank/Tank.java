package draw.TankGame.Tank;

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



}
