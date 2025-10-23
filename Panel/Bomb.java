package draw.TankGame.Panel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * 爆炸效果类
 * 通过bomb的生命周期来判断返回图片的格式
 * 每次repaint()会减少一次life
 * 在下面使用静态方法来调用和返回图片对象, 并在Panel中绘制图片
 */

public class Bomb {
    private int x;
    private int y;
    private int life = 9;
    private boolean isLive = true;
    private static BufferedImage[] images;
    private static final int ImageNumber = 3;


    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;

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

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public boolean getLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public void lifeDown() {
        if (life > 0) {
            life--;
        } else {
            isLive = false;
        }
    }


    public static void imageInit() {
        images = new BufferedImage[ImageNumber];
        for (int i = 0; i < ImageNumber; i++) {
            try {
                // 使用类加载器获取资源路径
                String imagePath = "draw/TankGame/img/blast_" + (i+1) + ".png";
                URL imageURL = Bomb.class.getClassLoader().getResource(imagePath);
                if (imageURL != null) {
                    images[i] = ImageIO.read(imageURL);
                } else {
                    throw new IOException("无法找到图片资源: " + imagePath);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 基于生命周期返回不同的图片效果
     */
    public static BufferedImage getImage(Bomb bomb) {
        // 添加检查确保images已被初始化
        if (images == null) {
            imageInit();
        }
        if (bomb.getLife() > 6) {
            bomb.lifeDown();
            return images[0];
        } else if (bomb.getLife()> 3) {
            bomb.lifeDown();
            return images[1];
        } else {
            bomb.lifeDown();
            return images[2];
        }


    }

}