package expression.exceptions;

import expression.*;
import expression.parser.OperatorType;

public class CheckedBinaryOperatorFactory {
    public static UnifiedExpression createExpression(UnifiedExpression left,
                                                     OperatorType type,
                                                     UnifiedExpression right) {
        return switch (type) {
            case ADD -> new CheckedAdd(left, right);
            case SUBTRACT -> new CheckedSubtract(left, right);
            case AREA -> new CheckedArea(left, right);
            case PERIMETER -> new CheckedPerimeter(left, right);
            case MULTIPLY -> new CheckedMultiply(left, right);
            case DIVIDE -> new CheckedDivide(left, right);
            default -> throw new IllegalArgumentException("Unknown operator type: " + type);
        };
    }
}
