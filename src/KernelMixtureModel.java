import com.googlecode.charts4j.*;
import org.w3c.dom.ranges.Range;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class KernelMixtureModel {
    static final double H = 0.5;


    public static void main(String[] args) {
        double firstClusterCenter = 0.0;
        double secondClusterCenter = 2.0;
        double thirdClusterCenter = 20.0;

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
        }

        for(int i = 0; i < data.length; i++) {
            results[i] = addKernels(kernels,data[i]);
        }

        Kernel[] kernels1 = new Kernel[1];
        kernels1[0] = kernels[0];

        List<Double> dividers = findMin(kernels,0,20,0.1,0.01);

        new DylansGrapher(data,kernels,dividers.get(0));
        new DylansGrapher(data,kernels,dividers.get(1));


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

    public static List<Double> findMin(Kernel[] kernels, double lowerBound, double upperBound, double step, double lossThreshold) {
        double currentX = lowerBound;
        double lastValue = addKernels(kernels,currentX);
        currentX += step/2;
        double currentValue = addKernels(kernels,currentX);
        double delta = currentValue-lastValue;

        while(delta == 0) {
            currentX += step;
            lastValue = currentValue;
            currentValue = addKernels(kernels,currentX);
            delta = currentValue - lastValue;
        }
        currentX += step;
        lastValue = currentValue;
        currentValue = addKernels(kernels,currentX);
        delta = currentValue - lastValue;

        int lastSgn = (int) Math.signum(delta);
        int sgn = lastSgn;

        List<MinRange> minRanges = new ArrayList<>();

        while (currentX <= upperBound){

            lastSgn = sgn;
            currentX += step;
            lastValue = currentValue;

            currentValue = addKernels(kernels,currentX);
            delta = currentValue - lastValue;
            sgn = (int) Math.signum(delta);

            if(sgn - lastSgn > 0) {
                minRanges.add(new MinRange(currentX-step,currentX));
            }
        }

        List<Double> dividers = new ArrayList<>();
        List<Double> flooredDividers = new ArrayList<>();

        boolean lastMidFloored = false;

        for(MinRange range : minRanges) {
            lastValue = addKernels(kernels,range.lower);
            double mid = (range.lower + range.upper)/2.0;
            currentValue = addKernels(kernels,mid);
            double lBound = range.lower;
            double uBound = range.upper;
            while(Math.abs(currentValue-lastValue) > lossThreshold) {
                double rmid = (mid+uBound)/2.0;
                double lmid = (lBound+mid)/2.0;

                double rmidVal = addKernels(kernels,rmid);
                double lmidVal = addKernels(kernels,lmid);

                mid = rmidVal > lmidVal ? lmid : rmid;

                System.out.println(mid);

                lastValue = currentValue;
                currentValue = addKernels(kernels,mid);
            }
            if(Math.abs(currentValue-lastValue) == 0) {
                flooredDividers.add(mid);
                lastMidFloored = true;
            }
            else if (lastMidFloored) {
                dividers.add((flooredDividers.get(flooredDividers.size()-1) + mid)/2.0);
                lastMidFloored = false;
            }
            else {
                dividers.add(mid);
            }
        }

        System.out.println(flooredDividers);
        System.out.println(dividers);

        return dividers;
    }
}
