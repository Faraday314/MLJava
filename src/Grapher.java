import com.googlecode.charts4j.*;
import javafx.scene.chart.XYChart;

import javax.swing.*;

/**
 * Creates a simple Chart using QuickChart
 */
public class Grapher extends JFrame {

    public static String drawLineGraph() {
        // Your really great chart.
        final Plot plot = Plots.newPlot(Data.newData(0, 10.6, 20.5, 80.20, 50.50, 95.5, 92.00));
        final LineChart chart = GCharts.newLineChart(plot);
        chart.setTitle("Growth of Alibata System Inc. (Estimated Plot)");
        chart.setSize(720, 360);
        return chart.toURLString();
    }

    public static void main(String[] args) throws Exception {
        System.out.println(drawLineGraph());
    }
}