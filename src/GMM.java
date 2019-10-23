import org.w3c.dom.ranges.Range;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GMM {

    public static void main(String[] args) {
        double firstClusterCenter = 1.0;
        double secondClusterCenter = 2.0;

        int k = 3;

        int cluster1Size = 10;
        int cluster2Size = 10;

        double cluster1NoiseRange = 0.5;
        double cluster2NoiseRange = 0.5;

        double cluster1Variance = 0.2;
        double cluster2Variance = 0.2;

        double[] firstCluster = new double[cluster1Size];
        double[] secondCluster = new double[cluster2Size];

        Random random = new Random();

        //Generate example data
        for (int i = 0; i < cluster1Size; i++) {
            firstCluster[i] = random.nextGaussian() * cluster1Variance + firstClusterCenter;
            firstCluster[i] = firstCluster[i] > firstClusterCenter + cluster1NoiseRange ? firstClusterCenter + cluster1NoiseRange : Math.max(firstClusterCenter - cluster1NoiseRange, firstCluster[i]);
        }
        for (int i = 0; i < cluster2Size; i++) {
            secondCluster[i] = random.nextGaussian() * cluster2Variance + secondClusterCenter;
            secondCluster[i] = secondCluster[i] > secondClusterCenter + cluster2NoiseRange ? secondClusterCenter + cluster2NoiseRange : Math.max(secondClusterCenter - cluster2NoiseRange, secondCluster[i]);
        }


        System.out.println(Arrays.toString(firstCluster));
        System.out.println(Arrays.toString(secondCluster));
    }
}
