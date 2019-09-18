package front_end;

import ast.*;
import ast.String_;
import front_end.token.*;
import front_end.token.Number;

import java.util.*;

public class RecursiveDescentParser implements Parser {
    /*
    private static final String[] reserved_words = {
            // reserve words or symbols which have more than 2 letters

            "void", "boolean",
            "byte", "short", "int", "long", "char",
            "float", "double",

            "static", "abstract", "final",
            "public", "protected", "private",
            "extends", "implements", "throws",

            "class", "enum", "interface",

            "if", "else", "for", "while", "do", "try", "catch", "finally",
            "return", "break", "continue", "throw",

            "true", "false", "null",

            "+=", "-=", "*=", "/=", "%=",
            ">=", "<=", "==", "!=",
            "&&", "||",
            ">>", "<<", ">>>", "<<<", ">>=", "<<=",
            "->",
            "::"
    };
    */
    private String input;
    private Tokenizer tokenizer;
    private Token now;
    private SymbolList current;
    private List<String> strings;
    /* private Node pointer; */
    public int line;

    public RecursiveDescentParser() {}
    private boolean match(final int tag) {
        return now.tag == tag;
    }
    private void consume(final int tag) throws ParsingException {
        if (match(tag)) {
            now = tokenizer.tokenize();
        } else {
            if (tag < 0xff && now.tag < 0xff) {
                throw new ParsingException("expecting `" + (char) tag +
                        "`, but found `" + (char) now.tag + "`");
            } else if (tag < 0xff) {
                throw new ParsingException("expecting `" + (char) tag +
                        "`, but found `" + now.tag + "`");
            } else if (now.tag < 0xff) {
                throw new ParsingException("expecting `" + tag +
                        "`, but found `" + (char) now.tag + "`");
            } else {
                throw new ParsingException("expecting `" + tag +
                        "`, but found `" + now.tag + "`");
            }
        }
    }
    private int integer() throws ParsingException {
        final int value;
        if (match(Tag.NUM)) {
            value = ((Number)now).value;
            now = tokenizer.tokenize();
        } else {
            throw new ParsingException("expecting `NUM`, but found `" + now.tag + "`");
        }
        return value;
    }
    private String identifier() throws ParsingException {
        final String id;
        if (match(Tag.ID)) {
            id = ((Word)now).lexeme;
            now = tokenizer.tokenize();
        } else {
            throw new ParsingException("expecting `ID`, but found `" + now.tag + "`");
        }
        return id;
    }
    private String string() throws ParsingException {
        final String string;
        if (match(Tag.STR)) {
            string = ((front_end.token.String_)now).value;
            if (!strings.contains(string)) {
                strings.add(string);
            }
            now = tokenizer.tokenize();
        } else {
            throw new ParsingException("expecting `STR`, but found `" + now.tag + "`");
        }
        return string;
    }
    public List<String> strings() {
        return strings;
    }
    public List<FunctionDeclare> parse(final String input) throws ParsingException {
        this.input = input;
        this.tokenizer = new Tokenizer(input);
        this.now = tokenizer.tokenize();
        this.current = null;
        this.strings = new ArrayList<>();
        this.line = 1;

        final List<FunctionDeclare> function_declarations = new ArrayList<>();
        while (match(Tag.INT)) {
            final FunctionDeclare function_declaration = function_declaration();
            function_declarations.add(function_declaration);
        }
        return function_declarations;
    }
    private FunctionDeclare function_declaration() throws ParsingException {
        final SymbolList symbols = new SymbolList(current);
        current = symbols;
        type();
        final String name = identifier();
        consume('(');
        if (!match(')')) {
            variable_declaration();
            while (match(',')) {
                consume(',');
                variable_declaration();
            }
        }
        consume(')');
        final Closure closure = closure();
        current = current.enclosing;
        return new FunctionDeclare(name, symbols, closure);
    }
    private Closure closure() throws ParsingException {
        final SymbolList symbols = new SymbolList(current);
        current = symbols;
        consume('{');
        while (match(Tag.INT)) {
            variable_declaration();
            consume(';');
        }
        final List<Statement> statements = new ArrayList<>();
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
        if (match(Tag.IF)) {
            consume(Tag.IF);
            consume('(');
            final Expression condition = expression();
            consume(')');
            final Closure then_closure = closure();
            return new If(condition, then_closure);
        } else if (match(Tag.WHILE)) {
            consume(Tag.WHILE);
            consume('(');
            final Expression condition = expression();
            consume(')');
            final Closure closure = closure();
            return new While(condition, closure);
        } else if (match(Tag.RET)) {
            consume(Tag.RET);
            Expression expression = expression();
            consume(';');
            return new Return(expression);
        } else if (match(Tag.PRINT)) {
            consume(Tag.PRINT);
            Expression expression;
            if (match(Tag.STR)) {
                final String string= string();
                expression = new String_(string, strings.indexOf(string));
            } else {
                expression = expression();
            }
            consume(';');
            return new Print(expression);
        } else {
            final String name = identifier();
            consume('=');
            final Expression expression = expression();
            consume(';');

            final LocalVariable variable = current.get(name);
            variable.update();
            return new Assign(new VariableCall(variable), expression);
        }
    }
    private Expression expression() throws ParsingException {
        Expression left = term();
        while (true) {
            if (match('+')) {
                consume('+');
                final Expression right = term();
                left =  new BinaryOperator("+", left, right);
            } else if (match('-')) {
                consume('-');
                final Expression right = term();
                left = new BinaryOperator("-", left, right);
            } else {
                return left;
            }
        }
    }
    private Expression term() throws ParsingException {
        Expression left = unary();
        while (true) {
            if (match('*')) {
                consume('*');
                final Expression right = unary();
                left = new BinaryOperator("*", left, right);
            } else if (match('/')) {
                consume('/');
                final Expression right = unary();
                left = new BinaryOperator("/", left, right);
            } else {
                return left;
            }
        }
    }
    private Expression unary() throws ParsingException {
        if (match('-')) {
            consume('-');
            final Expression operand = factor();
            return new UnaryOperator("-", operand);
        } else {
            final Expression expression = factor();
            return expression;
        }
    }
    private Expression factor() throws ParsingException {
        if (match(Tag.NUM)) {
            final int value = integer();
            return new ast.Number(value);
        } else if (match(Tag.ID)) {
            final String name = identifier();
            if (match('(')) {
                consume('(');
                final List<Expression> arguments = new ArrayList<>();
                if (!match(')')) {
                    Expression expression = expression();
                    arguments.add(expression);
                    while (match(',')) {
                        consume(',');
                        expression = expression();
                        arguments.add(expression);
                    }
                }
                consume(')');
                return new FunctionCall(name, arguments);
            } else {
                return new VariableCall(current.get(name));
            }
        } else {
            consume('(');
            final Expression expression = expression();
            consume(')');
            return expression;
        }
    }
    private void type() throws ParsingException {
        consume(Tag.INT);
    }
    public static String tab(int tab) {
        // TODO improve tab system
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < tab; i++) {
            builder.append(' ');
        }
        return builder.toString();
    }
}
