package expression;

import java.util.Objects;

public class Const implements UnifiedExpression {
    private final int value;
    private final long valueL;
    private final boolean isL;

    public Const(final int value) {
        this.value = value;
        this.valueL = 0;
        this.isL = false;
    }

    public Const(final long value) {
        this.value = 0;
        this.valueL = value;
        this.isL = true;
    }

    @Override
    public int evaluate(int x) {
        return value;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return value;
    }

    @Override
    public String toString() {
        if (isL) {
            return String.valueOf(valueL);
        }
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Const aConst = (Const) object;
        return value == aConst.value && valueL == aConst.valueL && isL == aConst.isL;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, valueL, isL);
    }
}
