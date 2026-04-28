package expression.exceptions;

import expression.*;
import expression.exceptions.dir.OverflowException;

public class CheckedNegate extends AbstractUnaryOperation {
    public CheckedNegate(UnifiedExpression expression) {
        super(expression, "-");
    }

    @Override
    protected int process(int a) {
        if (a == Integer.MIN_VALUE) { // -Integer.MIN_VALUE == Integer.MAX_VALUE + 1
            throw new OverflowException();
        }
        return -a;
    }

    @Override
    protected long process(long a) {
        return -a;
    }
}
