
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

public class Pong extends JPanel implements KeyListener {

    int ballX,ballY = 200;

    int mod,count = 0;

    int playerScore,aiScore = 0;

    int lPaddleX,rPaddleX = 50;
    int lPaddleY,rPaddleY = 280;

    int aiX = 1200;
    int aiY = ballY;

    int aiMod ,counter = 0;

    boolean rWall,bWall,failure,aiFailure = false;
    
    public static AudioClip paddle, wall;


    public void setup() {
        paddle = Applet.newAudioClip(ClassLoader.getSystemResource("Sounds/paddle.wav"));
      wall = Applet.newAudioClip(ClassLoader.getSystemResource("Sounds/wall.wav"));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
//        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.BLACK);
        //The ball
        g2d.fillRect(ballX, ballY, 30, 30);
        //Player paddle
        g2d.fillRect(rPaddleX, rPaddleY, 30, 125);
        //ai paddle
        g2d.fillRect(aiX, aiY, 30, 125);
    }

    public void moveBall() {

        if (ballX < 1230 && !rWall) {
            ballX += 5 + mod;
        }
        if (ballX >= 1230) {
            aiFailure = true;
        }
        if (ballY < 640 && !bWall) {
            ballY += 5 + mod;
        } else if (ballY >= 640 && !bWall) {
            wall.play();
            bWall = true;
        }
        if (rWall) {
            ballX -= 5 + mod;
            if (ballX <= 0) {
                failure = true;
            }
        }
        if (bWall) {
            ballY -= 5 + mod;
            if (ballY <= 0) {
                wall.play();
                bWall = false;
            }
        }
        failure();
    }

    public void checkCollide() {
        
        aiY = ballY - aiMod;
        //Player
        if (ballX == rPaddleX || ballX == rPaddleX - 1 || ballX == rPaddleX - 2 || ballX == rPaddleX - 3 || ballX == rPaddleX - 4) {

            if (ballY >= rPaddleY && ballY <= rPaddleY + 100) {
                if (count == 3) {
                    count = 0;
                    mod += 1;
                }
                count++;
                paddle.play();
                ai();
                rWall = false;
            } else {
                if (ballY + 30 >= rPaddleY && ballY - 30 <= rPaddleY + 100) {
                    if (count == 3) {
                        count = 0;
                        mod++;
                    }
                    count++;
                    paddle.play();
                    ai();
                    rWall = false;
                }
            }
        }
        //ai
        if (ballX == aiX || ballX == aiX - 1 || ballX == aiX - 2 || ballX == aiX - 3 || ballX == aiX - 4) {

            if (ballY >= aiY && ballY <= aiY + 100) {
                rWall = true;
                paddle.play();
            } else {
                if (ballY + 30 >= aiY && ballY - 30 <= aiY + 100) {
                    rWall = true;
                    paddle.play();
                }
            }
        }
    }

    public void failure() {

        if (failure) {
            aiScore++;
            mod = 0;
            ballX = ballY = 200;
            rWall = failure = false;
            ai();
            dispScore();
        }
        if (aiFailure) {
            playerScore++;
            mod = 0;
            ballX = ballY = 200;
            rWall = aiFailure = false;
            ai();
            dispScore();
        }
    }

    public void dispScore() {
        System.out.println(playerScore + " | " + aiScore);
    }

    public void ai() {
        int temp,temp2 = 0;
        temp = (int) (Math.random() * 10);
        temp2 = (int) (Math.random() * 10);
        aiMod = (temp * 10) + (temp2 * 10);
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_W) {
            rPaddleY -= 30;
        }
        if (ke.getKeyCode() == KeyEvent.VK_S) {
            rPaddleY += 30;
        }
        if (rPaddleY >= 620) {
            rPaddleY = 620;
        }
        if (rPaddleY <= 0) {
            rPaddleY = 0;
        }
        if (ke.getKeyCode() == KeyEvent.VK_R) {
            ballX = ballY = 200;
        }
    }
    @Override
    public void keyReleased(KeyEvent ke) {
    }
}
