package algebolic.expression;

public class Constant implements Expression {
    private double c;

    public Constant(double c) {
        this.c = c;
    }

    public double evaluate(double[] vars) {
        return c;
    }
}
