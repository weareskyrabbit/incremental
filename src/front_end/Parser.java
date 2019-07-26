package front_end;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {
    private String input;
    private SymbolList current;
    /* private Node pointer; */
    public int line;
    public int offset;

    public List<FunctionDeclaration> parse(String input) throws ParsingException {
        this.input = input;
        this.current = null;
        this.line = 1;
        this.offset = 1;
        final List<FunctionDeclaration> function_declarations = new ArrayList<>();
        while (match("Number")) {
            FunctionDeclaration function_declaration = function_declaration();
            function_declarations.add(function_declaration);
        }
        return function_declarations;
    }
    private FunctionDeclaration function_declaration() throws ParsingException {
        SymbolList symbols = new SymbolList(current);
        current = symbols;
        type();
        String name = identifier();
        consume('(');
        if (!match(')')) {
            type();
            String argument = identifier();
            current.declare(argument);
            while (match(',')) {
                consume(',');
                type();
                argument = identifier();
                current.declare(argument);
            }
        }
        consume(')');
        Closure closure = closure();
        current = current.enclosing;
        return new FunctionDeclaration(name, symbols, closure);
    }
    private Closure closure() throws ParsingException {
        SymbolList symbols = new SymbolList(current);
        current = symbols;
        consume('{');
        while (match("Number")) {
            declaration();
        }
        List<Statement> statements = new ArrayList<>();
        while (!match('}')) {
            Statement statement = statement();
            statements.add(statement);
        }
        consume('}');
        current = current.enclosing;
        return new Closure(symbols, statements);
    }
    private void declaration() throws ParsingException {
        type();
        String name = identifier();
        consume(';');
        current.declare(name);
    }
    private Statement statement() throws ParsingException {
        if (match("if")) {
            consume("if");
            consume('(');
            int condition = integer();
            consume(')');
            Closure then_closure = closure();
            return new If(condition, then_closure);
        } else if (match("while")) {
            consume("while");
            consume('(');
            int condition = integer();
            consume(')');
            Closure closure = closure();
            return new While(condition, closure);
        } else if (match("return")) {
            consume("return");
            int operand = integer();
            consume(";");
            return new Return(operand);
        } else {
            String name = identifier();
            if (match('=')) {
                consume('=');
                int value = integer();
                consume(';');
                return new Assignment(current.get(name), value);
            } else {
                consume('(');
                List<Integer> arguments = new ArrayList<>();
                if (!match(')')) {
                    int argument = integer();
                    arguments.add(argument);
                    while (match(',')) {
                        consume(',');
                        argument = integer();
                        arguments.add(argument);
                    }
                }
                consume(')');
                consume(';');
                return new FunctionCall(name, arguments);
            }
        }
    }
    private boolean match(final String token) {
        skip_whitespace();
        return input.startsWith(token) && !Character.isLetterOrDigit(input.charAt(token.length()));
    }
    private boolean match(final char operator) {
        skip_whitespace();
        return input.charAt(0) == operator;
    }
    private void consume(final String token) throws ParsingException {
        if (match(token)) {
            input = input.substring(token.length());
            offset += token.length();
        } else {
            throw new ParsingException();
        }
    }
    private void consume(final char operator) throws ParsingException{
        if (match(operator)) {
            input = input.substring(1);
            offset++;
        } else {
            throw new ParsingException();
        }
    }
    private UnaryOperator minus() throws ParsingException {
        /*
        consume('-');

        return new UnaryOperator();
        */
        return null;
    }
    private BinaryOperator add() throws ParsingException {
        /*
         left = integer();
        consume('+');
        int right = integer();
        return BinaryOperator()
        */
        return null;
    }
    private BinaryOperator multiply() {
        return null;
    }
    private void term() throws ParsingException {
        // TODO replace it with look-ahead
        char head = input.charAt(0);
        if (Character.isDigit(head)) {
            integer();
        } else if (Character.isLetter(head)) {
            identifier();
            if (match('(')) {
                consume('(');
                consume(')');
            }
        } else {
            consume('(');
            /* expression(); */
            consume(')');
        }
    }
    private void type() throws ParsingException {
        consume("Number");
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
                offset = 1;
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
    public static String tab(int tab) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < tab; i++) {
            builder.append(' ');
        }
        return builder.toString();
    }
}
