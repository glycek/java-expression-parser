package expression;

import java.util.Objects;

public abstract class AbstractBinaryOperation implements UnifiedExpression {
    private final UnifiedExpression left;
    private final UnifiedExpression right;
    protected final String op;

    protected AbstractBinaryOperation(final UnifiedExpression left,
                                      final UnifiedExpression right,
                                      final String op) {
        this.left = left;
        this.right = right;
        this.op = op;
    }

    protected abstract int process(int a, int b);
    protected abstract long process(long a, long b);

    @Override
    public int evaluate(int x) {
        return process(left.evaluate(x), right.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return process(left.evaluate(x, y, z), right.evaluate(x, y, z));
    }

    @Override
    public String toString() {
        return "(" + left.toString() + " " + op + " " + right.toString() + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AbstractBinaryOperation other = (AbstractBinaryOperation) obj;
        return left.equals(other.left) && right.equals(other.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right, getClass());
    }
}
