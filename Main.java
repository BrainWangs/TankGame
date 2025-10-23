package draw.TankGame;

import draw.TankGame.Panel.MyPanel;

import javax.swing.*;

public class Main extends JFrame {
    public static void main(String[] args) {
        new Main();
    }

    // 把游戏背景Panel加入到窗口中
    public Main() {
        MyPanel mp = new MyPanel();
        this.add(mp);
        this.addKeyListener(mp);
        this.setSize(1000, 750);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        new Thread(mp).start();
    }

}
