import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.Timer;
import java.util.ArrayList;
import java.util.Random;

public class FlappyBird implements ActionListener, MouseListener, KeyListener {
    
    public static FlappyBird flappyBird;
    public final int width = 800, height = 800;
    public Renderer renderer;
    public Rectangle bird;
    public ArrayList <Rectangle> columns;
    public Random rand;
    public int ticks,yMotion,score;
    public boolean gameOver,started;

    public FlappyBird(){
        JFrame jFrame = new JFrame();
        Timer timer = new Timer(20,this);  
        renderer = new Renderer();
        rand = new Random();

        jFrame.add(renderer);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
        jFrame.addMouseListener(this);
        jFrame.addKeyListener(this);
        jFrame.setTitle("Flappy Bird");
        jFrame.setSize(width, height);
        jFrame.setVisible(true);

        bird = new Rectangle(width/2-10,height/2-10,20,20);
        columns = new ArrayList<Rectangle>();

        addColumns(true);
        addColumns(true);
        addColumns(true);
        addColumns(true);

        timer.start();
    }

    public void actionPerformed(ActionEvent e){
        int speed = 10;
    
        ticks++;
        if(started){
        for (int i=0; i<columns.size();i++){
            Rectangle col = columns.get(i);
            col.x -= speed;
        }

        if(ticks % 2 == 0 && yMotion<15){
            yMotion+=2;
        }

        for(int i =0; i<columns.size();i++){
            Rectangle col = columns.get(i);
            if (col.x + col.width<0){
                columns.remove(col);

                if(col.y==0){
                    addColumns(false);
                }
            }
        }

        bird.y += yMotion;

        for(Rectangle column:columns){
            if(column.y==0 && bird.x + bird.width / 2 > column.x + column.width / 2 - 10 &&  bird.x + bird.width / 2 < column.x + column.width / 2 + 10 ){
                score++;
            }
            if(column.intersects(bird)){
                gameOver = true;
                bird.x = column.x - bird.width;
            }
        }
        if(bird.y > height -120 || bird.y < 0){
            bird.y = height-120;
            gameOver = true;
        }
        if (bird.y + yMotion >= height - 120){
            bird.y = height - 120 - bird.height;
        }
    }
        renderer.repaint();
    
}

    public void repaint(Graphics g){
        g.setColor(Color.cyan);
        g.fillRect(0, 0, width, height);

        g.setColor(Color.orange);
        g.fillRect(0, height-120, width, 120);

        g.setColor(Color.green);
        g.fillRect(0, height-120, width, 20);

        g.setColor(Color.red);
        g.fillRect(bird.x, bird.y, bird.width, bird.height);

        for(Rectangle columns:columns){
            paintColumn(g, columns);
        }

        g.setColor(Color.black);
        g.setFont(new Font("Arial",1,100));
        String[] str = {"GameOver!","You Suckk!" , "Chutiyaaaa!", "Close me!"};

        if(!started){
            g.drawString("Click to Start", 75, height/2-50);
        }

        for (int i=0; i<1;i++){
            if (gameOver){
                g.drawString("GameOver!", 150, height/2 - 10);
                g.drawString("Your Score:" + String.valueOf(score), 150, height/2+80);
            }
        }
        if(!gameOver && started){
            g.drawString(String.valueOf(score), width/2-25 ,100 );
        }
    }

    public void addColumns(boolean start){
        int space = 300;
        int height2 = 50 + rand.nextInt(300);
        int width2 = 100;

        if(start){
            columns.add(new Rectangle(width2+width +columns.size() * 300, height - height2, width2, height2));
            columns.add(new Rectangle(width+width2+(columns.size()-1)*300 ,0, width2,height-height2-space));
        }else{
            columns.add(new Rectangle(columns.get(columns.size()-1).x+600,height-height2-120,width2,height2));
            columns.add(new Rectangle(columns.get(columns.size()-1).x,0,width2,height-height2-space));
        }

    }

    public void paintColumn(Graphics g, Rectangle column){
        g.setColor(Color.blue.darker());
        g.fillRect(column.x, column.y, column.width, column.height);
    }

    public void jump(){
        if(gameOver){
            bird = new Rectangle(width/2-10,height/2-10,20,20);
            columns.clear();

            addColumns(true);
            addColumns(true);
            addColumns(true);
            addColumns(true);

            gameOver = false;
            score=0;
        }
        if(!started){
            started = true;
        }
        else if (!gameOver){
            if(yMotion > 0){
                yMotion = 0;
            }
            yMotion -=10;
        }
    }

    public static void main(String[] args){
       
        flappyBird = new FlappyBird();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        jump(); 
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Implement if needed
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Implement if needed
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Implement if needed
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Implement if needed
    }

    public void keyTyped(KeyEvent e){

    }

    public void keyPressed(KeyEvent e){

    }

    public void keyReleased(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_UP){
            jump();
        }
    }

}