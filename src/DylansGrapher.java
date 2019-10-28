import javax.swing.*;
import java.util.Random;

public class DylansGrapher extends JFrame {
    GrapherPannel pannel;
    final static int HEIGHT = 500;
    final static int WIDTH = 1000;
    DylansGrapher(double[] data, Kernel[] resaults){
        setSize(WIDTH,HEIGHT);
        setResizable(false);
        pannel = new GrapherPannel(this, data, resaults);
        setVisible(true);
        this.add(pannel);
    }
}
