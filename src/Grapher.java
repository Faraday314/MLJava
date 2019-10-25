import com.googlecode.charts4j.*;
import javafx.scene.chart.XYChart;

import javax.swing.*;

/**
 * Creates a simple Chart using QuickChart
 */
public class Grapher {

    public static String drawLineGraph() {

        // Your really great chart.


        final XYLine line = Plots.newXYLine(Data.newData(0, 1, 2, 3, 4, 5, 6),Data.newData(1,2,4,8,16,32,64));

        final XYLineChart chart = GCharts.newXYLineChart(line);
        chart.setTitle("Growth of Alibata System Inc. (Estimated Plot)");
        chart.setSize(720, 360);
        return chart.toURLString();
    }

    public static void main(String[] args) throws Exception {
        System.out.println(drawLineGraph());
    }
}