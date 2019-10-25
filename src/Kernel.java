import java.util.function.BiFunction;
import java.util.function.Function;

public class Kernel {

    public enum Type {

        Epanechnikov((Double x, Double datapoint, Double h) -> (1/h)*(3/4.0)*(1 - Math.pow((x-datapoint)/h,2)));
        protected final TriFunction<Double,Double,Double,Double> funct;
        Type(TriFunction<Double,Double,Double,Double> funct) {
            this.funct = funct;
        }
    }

    public Type type;
    public double datapoint;
    public double h;
    public Kernel(Type type, double dataPoint, double h) {
        this.type = type;
        this.datapoint = dataPoint;
        this.h = h;
    }

    public double calcValue(double x) {
        return type.funct.apply(x,datapoint,h);
    }

    @Override
    public String toString() {
        return 1/h+"*(3/4)*(1-((x-"+datapoint+")/"+h+")**2";
    }
}
