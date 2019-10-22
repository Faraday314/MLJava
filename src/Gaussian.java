import static java.lang.Math.PI;

public class Gaussian {

    private double variance;
    private double mean;

    /**
     * Constructor for Gaussian Distribution.
     *
     * @param variance The distribution's variance.
     * @param mean The distribution's mean.
     */
    public Gaussian(double variance, double mean) {
        this.variance = variance;
        this.mean = mean;
    }

    /**
     * Does some things that will be talked about later.
     *
     * @param value
     * @return
     */
    public double calculate(double value) {
        return (1/Math.sqrt(2*PI))*Math.exp(-Math.pow(value-mean,2)/(2*Math.pow(variance,2)));
    }
}
