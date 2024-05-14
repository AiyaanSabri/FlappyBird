import java.awt.Graphics;
import javax.swing.JPanel;

public class Renderer extends JPanel{

    private static final long serialVerisonUID = 1L;

    protected void paintComponent(Graphics g){
        
        super.paintComponent(g);

        FlappyBird.flappyBird.repaint(g);

    }
}