package expression.exceptions;

import expression.*;
import expression.exceptions.dir.OverflowException;

public class CheckedAdd extends AbstractBinaryOperation {
    public CheckedAdd(UnifiedExpression left, UnifiedExpression right) {
        super(left, right, "+");
    }

    @Override
    protected int process(int a, int b) throws OverflowException {
        if (a >= 0) {
            if (Integer.MAX_VALUE - a < b) {
                throw new OverflowException();
            }
        } else { // a < 0
            if (b < 0) {
                if (a < Integer.MIN_VALUE - b) { // a + b < MIN
                    throw new OverflowException();
                }
            }
        }
        return a + b;
    }

    @Override
    protected long process(long a, long b) {
        return a + b;
    }
}
