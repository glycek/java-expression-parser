package expression.exceptions;

import expression.*;
import expression.exceptions.dir.OverflowException;

public class CheckedSubtract extends AbstractBinaryOperation {

    public CheckedSubtract(UnifiedExpression left, UnifiedExpression right) {
        super(left, right, "-");
    }

    @Override
    protected int process(int a, int b) {
        if (a >= 0) {
            if (b == Integer.MIN_VALUE) {
                throw new OverflowException();
            }
            if (Integer.MAX_VALUE - a < -b) { // -b is safe from overflow
                throw new OverflowException();
            }
        } else { // a < 0
            if (b > 0) {
                if (Integer.MIN_VALUE + b > a) { // a - b < MIN
                    throw new OverflowException();
                }
            }
        }
        return a - b;
    }

    @Override
    protected long process(long a, long b) {
        return a - b;
    }
}
