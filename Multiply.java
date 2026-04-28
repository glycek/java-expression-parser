package expression;

public class Multiply extends AbstractBinaryOperation {
    public Multiply(UnifiedExpression left, UnifiedExpression right) {
        super(left, right, "*");
    }

    @Override
    protected int process(int a, int b) {
        return a * b;
    }

    @Override
    protected long process(long a, long b) {
        return a * b;
    }
}
