import org.w3c.dom.ranges.Range;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class KMeans {
    static final double H = 1;


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

        System.out.println(Arrays.toString(genKernels(data)));
    }

    public static Kernel[] genKernels(double[] data){
        Kernel[] kernels = new Kernel[data.length];
        for(int i = 0; i < data.length; i++){
            kernels[i] = new Kernel(Kernel.Type.Epanechnikov, data[i], H);
        }
        return kernels;
    }

    public static double addKernels(Kernel[] kernels, double value){
        double sum = 0;
        for(Kernel k: kernels){
            sum += k.calcValue(value);
        }
        return sum/kernels.length;
    }

    public static double addKernels(Kernel[] kernels, double valueRange, double increment){
        double sum = 0;
        for(Kernel k: kernels){
            sum += k.calcValue(increment);
        }
        return sum/kernels.length;
    }

    public static double min(double[] arr) {
        double minVal = Double.MAX_VALUE;
        for(double val : arr) {
            minVal = Math.min(val, minVal);
        }
        return minVal;
    }
}