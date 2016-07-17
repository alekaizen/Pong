/*
Created by Alexander Maggiacomo
*/
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class Start{

    JFrame frame = new JFrame("Pong");
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    
    public static void main(String[] args) throws InterruptedException {
        Start s = new Start();
    }

    public Start() {
        gameLoop();
    }

    public void gameLoop() {
        Pong pong = new Pong();       
        pong.setup();
        frame.add(pong);
        frame.addKeyListener(pong);
        frame.setBounds((screenSize.width) / 6, (screenSize.height) / 6, 1280, 720);
        pong.setBackground(new Color(192,192,192));
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        while (true) {
            pong.moveBall();
            pong.checkCollide();
            pong.repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}