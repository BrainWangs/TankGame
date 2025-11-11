package draw.TankGame;

import draw.TankGame.Panel.MyPanel;
import draw.TankGame.Panel.Record;
import javax.swing.*;

public class Main extends JFrame {
    public static void main(String[] args) {

        new Main();
    }

    /**
     * @description 这里是main的构造器,在这里创建游戏窗口Panel
     */
    // 把游戏背景Panel加入到窗口中
    public Main() {
        MyPanel mp = new MyPanel();
        this.add(mp);
        this.addKeyListener(mp);
        this.setSize(1300, 750);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        new Thread(mp).start();

        // 监听窗口关闭事件
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                // 在MyPanel中传递引用给Record类,记录hero和enemyTanks的相关信息并序列化
                mp.transferToRecord();
                // 在窗口关闭时保存记录
                Record.saveRecord("record.dat");
            }
        });
    }

}
