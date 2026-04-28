package expression.parser;

public enum OperatorType {
    ADD("+"),
    SUBTRACT("-"),
    MULTIPLY("*"),
    DIVIDE("/"),
    NEGATE("-"),
    SQRT("sqrt"),
    AREA("area"),
    PERIMETER("perimeter");

    private final String symbol;

    OperatorType(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}