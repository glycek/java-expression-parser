package expression.exceptions;

import expression.AbstractBinaryOperation;
import expression.UnifiedExpression;
import expression.exceptions.dir.OverflowException;

public class CheckedPerimeter extends AbstractBinaryOperation{
    protected CheckedPerimeter(UnifiedExpression left, UnifiedExpression right) {
        super(left, right, "perimeter");
    }

    @Override
    protected int process(int a, int b) {
        if (a >= 0) {
            if (Integer.MAX_VALUE - a < b) {
                throw new OverflowException();
            }
            if (b < 0) {
                throw new OverflowException();
            }
        } else {
            throw new OverflowException();
        }
        if (2 * (a + b) / 2 != a + b) {
            throw new OverflowException();
        }

        return 2 * (a + b);
    }

    @Override
    protected long process(long a, long b) {
        return 0;
    }
}
