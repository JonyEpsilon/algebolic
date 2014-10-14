package algebolic.expression;

public class Var implements Expression {

    private int index;

    public Var(int index) {
        this.index = index;
    }

    public double evaluate(double[] vars) {
        return vars[index];
    }
}
