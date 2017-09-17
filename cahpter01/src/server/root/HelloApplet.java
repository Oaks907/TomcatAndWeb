import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by haifei on 2017/9/17.
 */
public class HelloApplet extends Applet implements Runnable{
    private int fontSize = 8;
    private Thread changer;
    private boolean stopFlag;
    private Button contrlButton=new Button(" 开始动态显示！");

    public void init() {
        contrlButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (stopFlag){
                    start();
                } else {
                    stop();
                }
            }
        });

        setBackground(Color.white);
        add(contrlButton);
        setSize(100, 100);
    }

    @Override
    public void start() {
        super.start();
        changer = new Thread(this);
        stopFlag = false;
        fontSize = 8;
        contrlButton.setLabel("停止动态显示！");
        changer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setFont(new Font("newFont", Font.BOLD, fontSize));
        g.drawString("Hello", 30, 80);
    }

    @Override
    public void stop() {
        super.stop();
        stopFlag = true;
        contrlButton.setLabel("开始动态显示！");
    }

    @Override
    public void run() {
        while (!stopFlag) {
            repaint();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (fontSize++> 40) {
                fontSize = 8;
            }
        }
    }
}
