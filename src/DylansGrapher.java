import javax.swing.*;
import java.util.Random;

public class DylansGrapher extends JFrame {
    GrapherPannel pannel;
    DylansGrapher(double[] data, double[] resaults){
        setSize(400,400);
        pannel = new GrapherPannel(this);
        setVisible(true);
        this.add(pannel);
        pannel.drawGraph(data,resaults);
    }
}
