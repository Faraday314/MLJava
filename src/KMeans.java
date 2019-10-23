import org.w3c.dom.ranges.Range;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class KMeans {

    public static void main(String[] args) {
        double firstClusterCenter = 1.0;
        double secondClusterCenter = 2.0;

        Random random = new Random();
        
        int k = 2;

        int cluster1Size = 10;
        int cluster2Size = 10;

        double cluster1NoiseRange = 0.5;
        double cluster2NoiseRange = 0.5;

        double cluster1Variance = 0.2;
        double cluster2Variance = 0.2;

        double[] data = new double[cluster1Size+cluster2Size];
        
        //Generate example data
        for (int i = 0; i < cluster1Size; i++) {
            data[i] = random.nextGaussian() * cluster1Variance + firstClusterCenter;
            data[i] = data[i] > firstClusterCenter + cluster1NoiseRange ? firstClusterCenter + cluster1NoiseRange : Math.max(firstClusterCenter - cluster1NoiseRange, data[i]);
        }
        for (int i = cluster1Size; i < data.length; i++) {
            data[i] = random.nextGaussian() * cluster2Variance + secondClusterCenter;
            data[i] = data[i] > secondClusterCenter + cluster2NoiseRange ? secondClusterCenter + cluster2NoiseRange : Math.max(secondClusterCenter - cluster2NoiseRange, data[i]);
        }
        
        double startingPoint1Index = data[0];
        double startPoint2Index = data[data.length-1];
    }
    public double[] genExtremePoints(double[] data){
        double smallest = data[0];
        double largest = data[0];
        for(double d: data){
            if(d < smallest){
                smallest = d;
            }
            else if(d > largest){
                largest = d;
            }
        }
        return new double[]{smallest, largest};
    }

    public double min(double[] arr) {
        double minVal = Double.MAX_VALUE;
        for(double val : arr) {
            minVal = Math.min(val, minVal);
        }
        return minVal;
    }
}
