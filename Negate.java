package expression;

public class Negate extends AbstractUnaryOperation {
    public Negate(UnifiedExpression expression) {
        super(expression, "-");
    }

    @Override
    protected int process(int a) {
        return -a;
    }

    @Override
    protected long process(long a) {
        return -a;
    }
}
