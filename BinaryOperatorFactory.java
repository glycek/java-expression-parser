package expression;

import expression.parser.OperatorType;

public class BinaryOperatorFactory {
    public static UnifiedExpression createExpression(UnifiedExpression left,
                                                     OperatorType type,
                                                     UnifiedExpression right) {
        return switch (type) {
            case ADD -> new Add(left, right);
            case SUBTRACT -> new Subtract(left, right);
            case MULTIPLY -> new Multiply(left, right);
            case DIVIDE -> new Divide(left, right);
            default -> throw new IllegalArgumentException("Unknown operator type: " + type);
        };
    }
}
