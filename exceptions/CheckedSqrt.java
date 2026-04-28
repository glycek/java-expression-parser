package expression.exceptions;

import expression.AbstractUnaryOperation;
import expression.UnifiedExpression;
import expression.exceptions.dir.OverflowException;

public class CheckedSqrt extends AbstractUnaryOperation {
    public CheckedSqrt(UnifiedExpression expression) {
        super(expression, "sqrt");
    }

    @Override
    protected int process(int a) {
        if (a < 0) {
            throw new OverflowException();
        }
        return (int) Math.sqrt(a);
    }

    @Override
    protected long process(long a) {
        return 0;
    }
}
