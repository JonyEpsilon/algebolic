package algebolic.expression;

public class TestExpressionFactory {

    public Expression getTestExpression() {
        return new Times(
                new Plus(
                        new Times(new Var(0),
                                new Var(0)),
                        new Constant(10.0)),
                new Var(0));
    }

}