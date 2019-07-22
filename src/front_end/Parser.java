package front_end;

import java.util.ArrayList;
import java.util.Arrays;

public class Parser {
    private String input;
    private SymbolList current;
    public int line;
    public int offset;

    public Closure parse(String input) throws ParsingException {
        this.input = input;
        this.current = null;
        this.line = 0;
        Closure closure = closure();
        return closure;
    }
    private Closure closure() throws ParsingException {
        SymbolList symbols = new SymbolList(current);
        current = symbols;
        consume("{");
        while (match("Number")) {
            declaration();
        }
        ArrayList<Statement> statements = new ArrayList<>();
        while (!match("}")) {
            Statement statement = statement();
            statements.add(statement);
        }
        consume("}");
        current = current.enclosing;
        return new Closure(symbols, statements);
    }
    private void declaration() throws ParsingException {
        consume("Number");
        String name = identifier();
        consume(";");
        current.declare(name);
    }
    private Statement statement() throws ParsingException {
        if (match("while")) {
            consume("while");
            consume("(");
            int condition = integer();
            consume(")");
            Closure closure = closure();
            return new While(condition, closure);
        } else {
            String name = identifier();
            consume("=");
            int value = integer();
            consume(";");
            return new Assignment(current.get(name), value);
        }
    }
    private boolean match(final String token) {
        skip_whitespace();
        return input.startsWith(token);
    }
    private void consume(final String token) throws ParsingException {
        if (match(token)) {
            input = input.substring(token.length());
            offset += token.length();
        } else {
            throw new ParsingException();
        }
    }
    private UnaryOperator minus() throws ParsingException {
        /*
        consume("-");

        return new UnaryOperator();
        */
        return null;
    }
    private BinaryOperator add() throws ParsingException {
        /*
         left = integer();
        consume("+");
        int right = integer();
        return BinaryOperator()
        */
        return null;
    }
    private BinaryOperator multiply() {
        return null;
    }
    private String identifier() throws ParsingException {
        skip_whitespace();
        char[] array = input.toCharArray();
        if (Character.isLetter(array[0])) {
            final StringBuilder identifier = new StringBuilder();
            identifier.append(array[0]);
            array = Arrays.copyOfRange(array, 1, array.length);
            for (char head : array) {
                if (Character.isLetterOrDigit(head)) {
                    identifier.append(head);
                } else {
                    break;
                }
            }
            input = input.substring(identifier.length());
            offset += identifier.length();
            return identifier.toString();
        } else {
            throw new ParsingException();
        }
    }
    private int integer() throws ParsingException {
        skip_whitespace();
        char[] array = input.toCharArray();
        if (Character.isDigit(array[0])) {
            final StringBuilder integer = new StringBuilder();
            integer.append(array[0]);
            array = Arrays.copyOfRange(array, 1, array.length);
            for (char head : array) {
                if (Character.isDigit(head)) {
                    integer.append(head);
                } else {
                    break;
                }
            }
            input = input.substring(integer.length());
            offset += integer.length();
            return Integer.parseInt(integer.toString());
        } else {
            throw new ParsingException();
        }
    }
    private void skip_whitespace() {
        char[] array = input.toCharArray();
        int length = 0;
        for (char head : array) {
            if (head == '\n') {
                line++;
                offset = 0;
                length++;
            } else if (Character.isWhitespace(head)) {
                length++;
            } else {
                break;
            }
        }
        input = input.substring(length);
        offset += length;
    }
}
