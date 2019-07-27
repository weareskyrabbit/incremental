package front_end;

import front_end.ast.*;

import java.util.*;

public class Parser {
    private String input;
    private ParsingState state;
    private SymbolList current;
    /* private Node pointer; */
    public int line;
    private Map<String, Word> words;

    public List<FunctionDeclaration> parse(final String input) throws ParsingException {
        this.input = input;
        this.state = ParsingState.STANDARD;
        this.current = null;
        this.line = 1;
        this.words = new HashMap<>();
        reserve(new Word("if",    Tag.IF));
        reserve(new Word("else",  Tag.ELSE));
        reserve(new Word("while", Tag.WHILE));
        reserve(new Word("do",    Tag.DO));
        reserve(new Word("break", Tag.BREAK));
        reserve(Word.True);
        reserve(Word.False);
        reserve(Type.Int);
        reserve(Type.Float);
        reserve(Type.Byte);
        reserve(Type.Boolean);
        reserve(Type.Character);

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
            variable_declaration();
            while (match(',')) {
                consume(',');
                variable_declaration();
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
            variable_declaration();
            consume(';');
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
    private void variable_declaration() throws ParsingException {
        type();
        String name = identifier();
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
                // TODO check if function call has correct number of arguments
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
        } else {
            throw new ParsingException();
        }
    }
    private void consume(final char operator) throws ParsingException{
        if (match(operator)) {
            input = input.substring(1);
        } else {
            throw new ParsingException();
        }
    }
    private UnaryOperator minus() throws ParsingException {
        /*
         * consume('-');
         *
         * return new UnaryOperator();
         */
        return null;
    }
    private BinaryOperator add() throws ParsingException {
        /*
         * left = integer();
         * consume('+');
         * int right = integer();
         * return BinaryOperator()
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
            return Integer.parseInt(integer.toString());
        } else {
            throw new ParsingException();
        }
    }
    private void skip_whitespace() {
        char[] array = input.toCharArray();
        int length = 0;
        char previous = ' ';
        for (char head : array) {
            switch (state) {
                case STANDARD:
                    if (head == '\n') {
                        line++;
                        length++;
                        break;
                    } else if (Character.isWhitespace(head)) {
                        length++;
                        break;
                    } else if (previous == '/') {
                        if (head == '/') {
                            length++;
                            previous = ' ';
                            state = ParsingState.SHORT_COMMENT;
                            break;
                        } else if (head == '*') {
                            length++;
                            previous = ' ';
                            state = ParsingState.LONG_COMMENT;
                            break;
                        }
                    } else if (head == '/') {
                        length++;
                        previous = '/';
                        break;
                    } else {
                        input = input.substring(length);
                        return;
                    }
                case SHORT_COMMENT:
                    if (head == '\n') {
                        line++;
                        length++;
                        state = ParsingState.STANDARD;
                        break;
                    } else {
                        length++;
                        break;
                    }
                case LONG_COMMENT:
                    if (head == '\n') {
                        line++;
                        length++;
                        break;
                    } else if (head == '*') {
                        previous = '*';
                        length++;
                        break;
                    } else if (previous == '*' && head == '/') {
                        previous = ' ';
                        length++;
                        state = ParsingState.STANDARD;
                        break;
                    } else {
                        length++;
                        break;
                    }
            }
        }
        input = input.substring(length);
    }
    private int offset = 0;
    private char peek;
    private void reserve(final Word word) {
        words.put(word.lexeme, word);
    }
    private Token tokenize() {
        while (Character.isWhitespace(peek)) {
            if (peek == '\n') {
                line++;
            }
            consume();
        }
        switch (peek) {
            case '&':
                consume();
                if (peek == '&') {
                    consume();
                    return Word.and; // &&
                } else {
                    return new Token('&');
                }
            case '|':
                consume();
                if (peek == '|') return Word.or; // ||
                else return new Token('|');
            case '=':
                consume();
                if (peek == '=') return Word.eq; // ==
                else return new Token('=');
            case '!':
                consume();
                if (peek =='=') return Word.ne; // !=
                else return new Token('!');
            case '<':
                consume();
                if (peek == '=') return Word.le; // <=
                else return new Token('<');
            case '>':
                consume();
                if (peek == '=') return Word.ge; // >=
                else return new Token('>');
        }
        if (Character.isDigit(peek)) {
            int int_value = 0;
            do {
                int_value = 10 * int_value + Character.digit(peek, 10);
                consume();
            } while (Character.isDigit(peek));
            if (peek != '.') return new Num(int_value);

            float float_value = int_value;
            for (int i = 0; ; i++) {
                consume();
                if (!Character.isDigit(peek)) break;
                float_value += Character.digit(peek, 10) / Math.pow(10, i + 1);
            }
            return new Real(float_value);
        } else if (Character.isLetter(peek)) {
            StringBuilder builder = new StringBuilder();
            do {
                builder.append(peek);
                consume();
            } while (Character.isLetterOrDigit(peek));
            String lexeme = builder.toString();
            if (words.containsKey(lexeme)) return words.get(lexeme);
            Word word = new Word(lexeme, Tag.ID);
            words.put(lexeme, word);
            return word;
        } else {
            Token token = new Token(peek);
            peek = ' ';
            return token;
        }
    }
    private void consume() {
        offset++;
        if (offset >= input.length()) {
            System.out.println("EOF");
            System.exit(1);
        }
        peek = input.charAt(offset);
    }
    public static String tab(int tab) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < tab; i++) {
            builder.append(' ');
        }
        return builder.toString();
    }
}
