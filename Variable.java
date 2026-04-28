package expression;

import java.util.Objects;

public class Variable implements UnifiedExpression {
    private final String identifier;

    public Variable(final String identifier) {
        this.identifier = identifier;
    }

    @Override
    public int evaluate(int x) {
        return x;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        if (Objects.equals(identifier, "x")) {
            return x;
        } else if (Objects.equals(identifier, "y")) {
            return y;
        } else if (Objects.equals(identifier, "z")) {
            return z;
        } else {
            throw new IllegalArgumentException("Invalid identifier: " + identifier);
        }
    }

    @Override
    public String toString() {
        return identifier;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Variable other = (Variable) obj;
        return identifier.equals(other.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, getClass());
    }
}
