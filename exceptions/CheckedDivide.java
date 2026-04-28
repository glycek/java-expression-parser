package expression.exceptions;

import expression.*;
import expression.exceptions.dir.DBZException;
import expression.exceptions.dir.OverflowException;

public class CheckedDivide extends AbstractBinaryOperation {

    public CheckedDivide(UnifiedExpression left, UnifiedExpression right) {
        super(left, right, "/");
    }

    @Override
    protected int process(int a, int b) {
        if (b == 0) {
            throw new DBZException();
        }
        if (a == Integer.MIN_VALUE && b == -1) { // -Integer.MIN_VALUE == Integer.MAX_VALUE + 1
            throw new OverflowException();
        }
        return a / b;
    }

    @Override
    protected long process(long a, long b) {
        return a / b;
    }
}
