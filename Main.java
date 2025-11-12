package draw.TankGame;

import draw.TankGame.Panel.MyPanel;
import draw.TankGame.Panel.Record;
import javax.swing.*;
import java.util.Scanner;

public class Main extends JFrame {
    public static void main(String[] args) {

        new Main();
    }

    /**
     * @description 这里是main的构造器,在这里创建游戏窗口Panel
     */
    // 把游戏背景Panel加入到窗口中
    public Main() {
        // 获取输入,选择新游戏或继续游戏
        Scanner scan = new Scanner(System.in);
        System.out.println("Select NewGame or Continue (input 1 or 2)");
        int select = scan.nextInt();
        // 创建MyPanel
        MyPanel mp = new MyPanel(select);
        // 将MyPanel加入到JFrame中
        this.add(mp);
        // 添加键盘监听器
        this.addKeyListener(mp);
        // 设置窗口大小
        this.setSize(1300, 750);
        // 关闭窗口时退出程序
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置窗口可见性
        this.setVisible(true);
        // 启动MyPanel的线程
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
