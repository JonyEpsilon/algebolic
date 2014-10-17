package algebolic.expression;


import java.util.List;

public abstract class JExpr {

    public abstract double evaluate(List<Double> vars);

    public abstract int size();

    /* Returns a deep copy of this expression. */
    public abstract JExpr copy();

    public JExpr subExpr(int index) {
        return new Constant(0.0);
    }

    public JExpr replaceSubExpr(int index, JExpr newExpression) {return null;}

    public int randomTerminalIndex() { return 0; }
    public int randomNonterminalIndex() { return 0; }

}
