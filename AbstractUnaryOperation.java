package expression;

import java.util.Objects;

public abstract class AbstractUnaryOperation implements UnifiedExpression {
    private final UnifiedExpression expression;
    protected final String op;

    protected AbstractUnaryOperation(UnifiedExpression expression, String op) {
        this.expression = expression;
        this.op = op;
    }

    protected abstract int process(int a);
    protected abstract long process(long a);

    @Override
    public int evaluate(int x) {
        return process(expression.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return process(expression.evaluate(x, y, z));
    }

    @Override
    public String toString() {
        return op + "(" + expression.toString() + ")";
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        AbstractUnaryOperation that = (AbstractUnaryOperation) object;
        return Objects.equals(expression, that.expression) && Objects.equals(op, that.op);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expression, op);
    }
}
