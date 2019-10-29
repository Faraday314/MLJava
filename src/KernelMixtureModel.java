import com.googlecode.charts4j.*;
import org.w3c.dom.ranges.Range;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class KernelMixtureModel {
    static final double H = 0.25;


    public static void main(String[] args) {
        double firstClusterCenter = 1.0;
        double secondClusterCenter = 2.0;
        double thirdClusterCenter = 3.0;

        Random random = new Random();

        int cluster1Size = 250;
        int cluster2Size = 250;
        int cluster3Size = 250;

        double cluster1NoiseRange = 0.5;
        double cluster2NoiseRange = 0.5;
        double cluster3NoiseRange = 0.5;

        double cluster1Variance = 0.2;
        double cluster2Variance = 0.2;
        double cluster3Variance = 0.2;

        double[] data = new double[cluster1Size+cluster2Size+cluster3Size];

        //Generate example data
        for (int i = 0; i < cluster1Size; i++) {
            data[i] = Math.max(0,random.nextGaussian() * cluster1Variance + firstClusterCenter);
            data[i] = data[i] > firstClusterCenter + cluster1NoiseRange ? firstClusterCenter + cluster1NoiseRange : Math.max(firstClusterCenter - cluster1NoiseRange, data[i]);
        }
        for (int i = cluster1Size; i < cluster1Size+cluster2Size; i++) {
            data[i] = Math.max(0,random.nextGaussian() * cluster2Variance + secondClusterCenter);
            data[i] = data[i] > secondClusterCenter + cluster2NoiseRange ? secondClusterCenter + cluster2NoiseRange : Math.max(secondClusterCenter - cluster2NoiseRange, data[i]);
        }
        for (int i = cluster1Size+cluster2Size; i < data.length; i++) {
            data[i] = Math.max(0,random.nextGaussian() * cluster3Variance + thirdClusterCenter);
            data[i] = data[i] > thirdClusterCenter + cluster3NoiseRange ? thirdClusterCenter + cluster3NoiseRange : Math.max(thirdClusterCenter - cluster3NoiseRange, data[i]);
        }


        double[] results = new double[data.length];
        Kernel[] kernels = genKernels(data);
        for(int i = 0; i < data.length; i++) {
            results[i] = addKernels(kernels,data[i]);
            System.out.println(results[i]);
        }

        for(int i = 0; i < data.length; i++) {
            results[i] = addKernels(kernels,data[i]);
            System.out.println(results[i]);
        }

        Kernel[] kernels1 = new Kernel[1];
        kernels1[0] = kernels[0];

        new DylansGrapher(data,kernels);

    }

    public static List<Double> array2List(double[] array) {
        List<Double> output = new ArrayList<>();
        for(double d : array) {
            output.add(d);
        }
        return output;
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
            if(Math.abs(k.getU(value)) < 1)
            sum += k.calcValue(value);
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
    public static double max(double[] arr) {
        double maxVal = Double.MIN_VALUE;
        for(double val : arr) {
            maxVal = Math.max(val, maxVal);
        }
        return maxVal;
    }

    public static double findMin(Kernel[] kernels, double lowerBound, double upperBound, double step) {
        /*
        1. find whether function is increasing or decreasing at starting point.
        2. If increasing, x = x + step
            2a. If new x value is increasing as well, keep adding steps until it starts decreasing
            2b. Add more steps until it goes from decreasing to increasing

         */

        double min = Double.MAX_VALUE;
        double lastMin = 0;
        double lastValue = addKernels(kernels,lowerBound);
        double currentValue = Double.MAX_VALUE;
        double currentX = lowerBound;

        return 0;
    }
}
