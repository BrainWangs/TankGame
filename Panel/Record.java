package draw.TankGame.Panel;

import draw.TankGame.Tank.EnemyTank;
import draw.TankGame.Tank.Hero;

import java.io.*;
import java.util.Vector;

/**
 * @description 创建记录类(静态工具类),记录玩家成绩和坐标信息
 * score 玩家分数,每次击中后更新
 * hero 记录当前玩家坦克的坐标信息,在Panel初始化时若该指针不为空,则从此处加载并初始化玩家坦克
 *
 */
public class Record {
    private static int score = 0;
    private static int destroyedNum = 0;
    private static Hero hero = null;
    private static Vector<EnemyTank> enemyTanks = null;

    public Record() {
    }

    public static int getDestroyedNum() {
        return destroyedNum;
    }

    public static void setHero(Hero hero) {
        Record.hero = hero;
    }

    public static Hero getHero() {
        return hero;
    }

    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Record.enemyTanks = enemyTanks;
    }

    public static Vector<EnemyTank> getEnemyTanks() {
        return enemyTanks;
    }

    public static void addScore() {
        score += 10;
        System.out.println("当前分数为:" + score);
    }

    public static void addDestroyedEnemyNum() {
        destroyedNum++;
        System.out.println("已消灭敌人数量为:" + destroyedNum);
    }
    
    // 添加使用对象处理流将字段写入文件的方法
    public static void saveRecord(String filePath) {
        // 如果文件不存在,则创建文件
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("创建文件时出错: " + e.getMessage());
                e.printStackTrace();
            }
        }
        // 写入文件
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            // 写入分数
            oos.writeInt(score);
            // 写入已消灭敌人数量
            oos.writeInt(destroyedNum);
            // 写入英雄坦克信息
            oos.writeObject(hero);
            // 写入敌人坦克信息
            oos.writeObject(enemyTanks);
            System.out.println("记录已保存到文件: " + filePath);
        } catch (IOException e) {
            System.err.println("保存记录时出错: " + e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * @description 读取文件
     */
    public static void readRecord(String filePath) {
        if (!new File(filePath).exists()) {
            System.out.println("文件不存在");
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            // 读取文件
            score = ois.readInt();
            destroyedNum = ois.readInt();
            hero = (Hero) ois.readObject();
            enemyTanks = (Vector<EnemyTank>) ois.readObject();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Record.score);
        System.out.println(Record.destroyedNum);
        System.out.println(Record.hero);
        System.out.println(Record.enemyTanks);
    }

    /**
     * @description 打印信息(测试用)
     */
    @Deprecated
    public void printRecord() {
        System.out.println(Record.score);
        System.out.println(Record.destroyedNum);
        System.out.println(Record.hero);
        System.out.println(Record.enemyTanks);
    }

}