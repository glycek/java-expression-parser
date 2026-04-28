package expression.exceptions;

import expression.UnifiedExpression;
import expression.AbstractBinaryOperation;
import expression.exceptions.dir.*;

public class CheckedArea extends AbstractBinaryOperation {

    protected CheckedArea(UnifiedExpression left, UnifiedExpression right) {
        super(left, right, "area");
    }

    @Override
    protected int process(int a, int b) {
        if (a < 0 || b < 0 || (a != 0 && b != 0 && a / 2 * b / b != a / 2)) {
            throw new OverflowException();
        }
        return a / 2 * b + a % 2 * b / 2;
    }

    @Override
    protected long process(long a, long b) {
        return (a * b) / 2;
    }
}
