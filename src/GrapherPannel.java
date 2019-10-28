import javax.swing.*;
import java.awt.*;

public class GrapherPannel extends JPanel {

    final int circleWidth = 4;

    final int pixelsPerGroup = 4;

    int ran = 0;

    double scale = 0;
    double max;
    double kernalMax;
    double kernalMin;
    double min;

    double[] data;
    Kernel[] results;
    JFrame frame;

    int[] pointsAt;

    GrapherPannel(JFrame frame,double[] data, Kernel[] resaults){
        this.data = data;
        this.results = resaults;
        this.frame = frame;
        drawGraph();
    }

    @Override
    public void paintComponent(Graphics g){
        g.setColor(new Color(255,255,255));
        g.drawRect(0,0, results.length, HEIGHT);
        drawFunction(g);
        drawPoints(g);

    }



    public void drawGraph(){
        max = KernelMixtureModel.max(data);
        min = KernelMixtureModel.min(data);
        scale = max - min;
        pointsAt = new int[frame.getWidth() / pixelsPerGroup];
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
        g.setColor(new Color(60,60,60));
        double[] kernalOuts = new double[frame.getWidth()];
        double step = (max - min)/frame.getWidth();
        for(int i = 0; i < frame.getWidth(); i++) {
            System.out.println("KOut: " + (min + i * step) + " = " +  KernelMixtureModel.addKernels(results, min + i * step));
            kernalOuts[i] = KernelMixtureModel.addKernels(results, min + i * step);
        }
        kernalMax = KernelMixtureModel.max(kernalOuts);
        kernalMin = KernelMixtureModel.min(kernalOuts);
        for(int i = 0; i < frame.getWidth(); i++){
            g.drawRect(i,frame.getHeight(), 1, getScaledValues(results, min + i * step));
        }
    }

    private void drawPoints(Graphics g) {
        int xForPoint = 0;
        g.setColor(new Color(200,100,0));
        for(int i = 0; i < data.length; i++){
            xForPoint = getPointX(data[i]);
            pointsAt[xForPoint / pixelsPerGroup]++;
        }
        for(int i = 0; i < pointsAt.length; i++) {
            if (pointsAt[i] != 0) {
                g.fillOval(i * pixelsPerGroup, frame.getHeight() - circleWidth - 100 - (circleWidth * 2 * pointsAt[i]), circleWidth, circleWidth);
            }
        }
        g.setColor(new Color(0,0,0));
        g.drawRect(0, frame.getHeight() - circleWidth - 100,frame.getWidth(), frame.getHeight() - circleWidth - 101);
    }

    private int getPointX(double d){
        return (int) Math.round(((d - min)/(max - min)) * (frame.getWidth() - 1));
    }
    private int getScaledValues(Kernel[] d, double point){
        return (int) -Math.round(((KernelMixtureModel.addKernels(d,point) - kernalMin)/(kernalMax - kernalMin)) * (frame.getHeight()));
    }
}
