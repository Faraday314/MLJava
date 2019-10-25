import javax.swing.*;
import java.awt.*;

public class GrapherPannel extends JPanel {

    final int HEIGHT = 500;
    final int circleWidth = 5;

    double[] data;
    double[] resaults;
    JFrame frame;

    GrapherPannel(JFrame frame){
        this.frame = frame;
    }

    @Override
    public void paintComponent(Graphics g){
        frame.setSize(resaults.length, HEIGHT);
        g.setColor(new Color(255,255,255));
        g.drawRect(0,0, resaults.length, HEIGHT);
        drawFunction(g);
        drawPoints(g);
    }

    

    public void drawGraph(double[] data, double[] resaults){
        this.data = data;
        this.resaults = resaults;
        repaint();
    }

    public static double returnLargest(double[] array){
        double largest = array[0];
        for(double d: array){
            if(d > largest){
                largest = d;
            }
        }
        return largest;
    }

    private void drawFunction(Graphics g){
        g.setColor(new Color(255,0,0));
        for(int i = 0; i < resaults.length; i++){
            g.drawRect(i,HEIGHT, 1,(int) Math.round(resaults[i]/HEIGHT));
        }
    }
    
    private void drawPoints(Graphics g) {
        g.setColor(new Color(200,100,0));
        for(int i = 0; i < resaults.length; i++){
            g.drawOval((int) Math.round(data[i]), circleWidth + 5, circleWidth, circleWidth);

        }
    }
}
