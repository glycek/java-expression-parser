package expression;

public class Divide extends AbstractBinaryOperation {
    public Divide(UnifiedExpression left, UnifiedExpression right) {
        super(left, right, "/");
    }

    @Override
    protected int process(int a, int b) {
        return a / b;
    }

    @Override
    protected long process(long a, long b) {
        return a / b;
    }
}
