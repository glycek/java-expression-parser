package expression.exceptions;

import expression.*;
import expression.exceptions.dir.OverflowException;

public class CheckedMultiply extends AbstractBinaryOperation {
    public CheckedMultiply(UnifiedExpression left, UnifiedExpression right) {
        super(left, right, "*");
    }

    @Override
    protected int process(int a, int b) {
        if (a == -1 || b == -1) {
            if (a == -1 && b == Integer.MIN_VALUE) { // -Integer.MIN_VALUE == Integer.MAX_VALUE + 1
                throw new OverflowException();
            }
            if (b == -1 && a == Integer.MIN_VALUE) { // -Integer.MIN_VALUE == Integer.MAX_VALUE + 1
                throw new OverflowException();
            }
        }
        if (b != 0 && a * b / b != a) { // a * b overflowed
            throw new OverflowException();
        }
        return a * b;
    }

    @Override
    protected long process(long a, long b) {
        return a * b;
    }
}
