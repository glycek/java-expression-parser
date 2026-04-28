package expression.exceptions;

import expression.*;
import expression.exceptions.dir.ParsingException;
import expression.parser.*;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ExpressionParser implements TripleParser {

    public UnifiedExpression parse(final String source) {
        return parse(new StringSource(source));
    }

    private static UnifiedExpression parse(final CharSource source) {
        return new CheckedParser(source).parseExpression();
    }

    private static class CheckedParser extends BaseParser {

        private final List<OperatorType> TERM_OPERATORS = List.of(
                OperatorType.ADD,
                OperatorType.SUBTRACT,
                OperatorType.AREA,
                OperatorType.PERIMETER
        );

        private final List<OperatorType> ELEMENT_OPERATORS = List.of(
                OperatorType.MULTIPLY,
                OperatorType.DIVIDE
        );

        private static final Map<Character, Character> BRACKET_PAIRS = Map.of(
                '(', ')',
                '[', ']',
                '{', '}'
        );

        private static final Set<Character> EXPRESSION_TERMINATORS = Set.of(END);
        private static final Set<Character> TERM_TERMINATORS = Set.of('+', '-', 'a', 'p', END);
        private static final Set<Character> BRACKET_TERMINATORS = Set.of(')', ']', '}');
        private static final Set<Character> BRACKET_TERM_TERMINATORS = Set.of('+', '-', 'a', 'p', ')', ']', '}');

        private int position = 0;

        public CheckedParser(final CharSource source) {
            super(source);
        }

        private UnifiedExpression parseLevel(Supplier<UnifiedExpression> subLevelParser,
                                             List<OperatorType> operators,
                                             Predicate<Character> terminationCondition) {
            return parseLevel(subLevelParser.get(), subLevelParser, operators, terminationCondition);
        }

        private UnifiedExpression parseLevel(UnifiedExpression left,
                                             Supplier<UnifiedExpression> subLevelParser,
                                             List<OperatorType> operators,
                                             Predicate<Character> terminationCondition) {
            skipWhitespace();
            if (terminationCondition.test(peek())) {
                return left;
            }
            OperatorType opType = expectOperators(operators);
            UnifiedExpression right = subLevelParser.get();
            return parseLevel(CheckedBinaryOperatorFactory.createExpression(left, opType, right),
                    subLevelParser,
                    operators,
                    terminationCondition);
        }

        public UnifiedExpression parseExpression() {
            UnifiedExpression result = parseLevel(this::parseTerm, TERM_OPERATORS, EXPRESSION_TERMINATORS::contains);
            expect(END);
            position++;
            return result;
        }

        private UnifiedExpression parseTerm() {
            return parseLevel(this::parseElement, ELEMENT_OPERATORS, TERM_TERMINATORS::contains);
        }

        private UnifiedExpression parseBracket(char expectedClosing) {
            UnifiedExpression result = parseLevel(this::parseBracketTerm, TERM_OPERATORS, BRACKET_TERMINATORS::contains);
            expect(expectedClosing);
            position++;
            return result;
        }

        private UnifiedExpression parseBracketTerm() {
            return parseLevel(this::parseElement, ELEMENT_OPERATORS, BRACKET_TERM_TERMINATORS::contains);
        }

        private OperatorType expectOperators(List<OperatorType> operators) throws ParsingException {
            skipWhitespace();
            StringBuilder sb = new StringBuilder();
            for (OperatorType operator : operators) {
                String operatorString = operator.getSymbol();
                if (operatorString.startsWith(sb.toString())) {
                    while (sb.length() < operatorString.length()) {
                        if (operatorString.charAt(sb.length()) == peek()) {
                            sb.append(take());
                            position++;
                        } else {
                            break;
                        }
                    }
                    if (sb.length() == operatorString.length()) {
                        if (sb.length() > 1 && !Character.isWhitespace(peek()) && !BRACKET_PAIRS.containsKey(peek()) && peek() != '-') {
                            throw new ParsingException("Position: " + position + ", Expected whitespace, left bracket or '-' after \"" + operatorString
                                    + "\", found: " + peek());
                        }
                        return operator;
                    }
                }
            }
            throw new ParsingException("Position: " + position + ", Expected operators: " + operators + ", found: " + sb);
        }
        // unary
        // bracketOpen
        // const
        // variable
        private UnifiedExpression parseElement() {
            skipWhitespace();
            StringBuilder sb = new StringBuilder();
            if (eof()) {
                throw new ParsingException("Position: " + position + ", Unexpected end of expression, expected unary operation, const or variable");
            } else if (BRACKET_PAIRS.containsKey(peek())) {
                char open = take();
                position++;
                char expectedClosing = BRACKET_PAIRS.get(open);
                return parseBracket(expectedClosing);
            } else if (take('-')) {
                position++;
                if (between('0', '9')) {
                    sb.append('-');
                    return parseConst(sb);
                } else {
                    return new CheckedNegate(parseElement());
                }
            } else if (between('0', '9')) {
                return parseConst(sb);
            } else {
                if (Character.isLetter(peek())) {
                    while (Character.isLetter(peek()) || between('0', '9')) {
                        sb.append(take());
                        position++;
                    }
                }
                if (sb.isEmpty()) {
                    skipWhitespace();
                    if (BRACKET_PAIRS.containsValue(peek())) {
                        throw new ParsingException("Position: " + position + ", Expected unary operation, const or variable, operator with missing right operand or empty bracket, next symbol: " + peek());
                    }
                    if (peek() == '+' || peek() == '*' || peek() == '/') {
                        throw new ParsingException("Position: " + position + ", Expected unary operation, const or variable, operator with missing left operand, next symbol: " + peek());
                    }
                    else {
                        throw new ParsingException("Position: " + position + ", Expected unary operation, const or variable, operator with missing operand, next symbol: " + peek());
                    }
                }
                if (sb.toString().equals("sqrt")) {
                    if (Character.isWhitespace(peek()) || BRACKET_PAIRS.containsKey(peek()) || peek() == '-') {
                        return new CheckedSqrt(parseElement());
                    }
                }
                char back = sb.charAt(sb.length() - 1);
                if (back == 'x' || back == 'y' || back == 'z') {
                    return new Variable(String.valueOf(back));
                } else {
                    throw new ParsingException("Position: " + position + ", Wrong variable or operator without operands: " + sb);
                }
            }
        }

        private UnifiedExpression parseConst(StringBuilder sb) {
            if (take('0')) {
                position++;
                sb.append('0');
            } else {
                while (between('0', '9')) {
                    sb.append(take());
                    position++;
                }
            }
            try {
                return new Const(Integer.parseInt(sb.toString()));
            } catch (NumberFormatException e) {
                throw new ParsingException("Position: " + position + ", Wrong number for integer, overflow: " + sb);
            }
        }

        private void skipWhitespace() {
            while (Character.isWhitespace(peek())) {
                take();
                position++;
            }
        }
    }
}
