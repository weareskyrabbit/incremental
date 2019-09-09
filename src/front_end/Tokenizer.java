package front_end;

import front_end.token.*;
import front_end.token.Number;

import java.util.HashMap;
import java.util.Map;

public class Tokenizer {
    private char[] input;
    private int position;
    private int line;
    private Map<String, Word> words;
    private TokenizerState state;

    Tokenizer(final String input) {
        this.input = input.toCharArray();
        this.position = 0;
        this.line = 1;
        this.state = TokenizerState.STANDARD;
        this.words = new HashMap<>();
        reserve(new Word("if",    Tag.IF));
        reserve(new Word("else",  Tag.ELSE));
        reserve(new Word("while", Tag.WHILE));
        reserve(new Word("do",    Tag.DO));
        reserve(new Word("break", Tag.BREAK));
        reserve(new Word("return", Tag.RET));
        reserve(new Word("print", Tag.PRINT));
        reserve(Word.True);
        reserve(Word.False);
        reserve(Type.Int);
        reserve(Type.Float);
        reserve(Type.Byte);
        reserve(Type.Boolean);
        reserve(Type.Character);
    }
    private void reserve(final Word word) {
        words.put(word.lexeme, word);
    }
    Token tokenize() {
        switch (state) {
            case STANDARD:
                while (Character.isWhitespace(input[position])) {
                    if (input[position] == '\n') {
                        line++;
                    }
                    position++;
                }
                switch (input[position]) {
                    case '&':
                        position++;
                        if (input[position] == '&') {
                            position++;
                            return Word.and; // &&
                        } else {
                            return new Token('&');
                        }

                    case '|':
                        position++;
                        if (input[position] == '|') {
                            position++;
                            return Word.or; // ||
                        } else {
                            return new Token('|');
                        }
                    case '=':
                        position++;
                        if (input[position] == '=') {
                            position++;
                            return Word.eq; // ==
                        } else {
                            return new Token('=');
                        }
                    case '!':
                        position++;
                        if (input[position] == '=') {
                            position++;
                            return Word.ne; // !=
                        } else {
                            return new Token('!');
                        }
                    case '<':
                        position++;
                        if (input[position] == '=') {
                            position++;
                            return Word.le; // <=
                        } else {
                            return new Token('<');
                        }
                    case '>':
                        position++;
                        if (input[position] == '=') {
                            position++;
                            return Word.ge; // >=
                        } else {
                            return new Token('>');
                        }
                    case '/':
                        position++;
                        if (input[position] == '/') {
                            position++;
                            state = TokenizerState.SHORT_COMMENT;
                            return tokenize();
                        } else if (input[position] == '*') {
                            position++;
                            state = TokenizerState.LONG_COMMENT;
                            return tokenize();
                        } else {
                            return new Token('/');
                        }
                }
                if (Character.isDigit(input[position])) {
                    int int_value = 0;
                    do {
                        int_value = 10 * int_value + Character.digit(input[position], 10);
                        position++;
                    } while (Character.isDigit(input[position]));
                    if (input[position] != '.') {
                        return new Number(int_value);
                    }

                    float float_value = int_value;
                    for (int i = 0; ; i++) {
                        position++;
                        if (!Character.isDigit(input[position])) break;
                        float_value += Character.digit(input[position], 10) / Math.pow(10, i + 1);
                    }
                    return new Real(float_value);
                } else if (Character.isLetter(input[position])) {
                    StringBuilder builder = new StringBuilder();
                    do {
                        builder.append(input[position]);
                        position++;
                    } while (Character.isLetterOrDigit(input[position]));
                    String lexeme = builder.toString();
                    if (words.containsKey(lexeme)) {
                        return words.get(lexeme);
                    }
                    Word word = new Word(lexeme, Tag.ID);
                    words.put(lexeme, word);
                    return word;
                } else {
                    Token token = new Token(input[position]);
                    input[position] = ' ';
                    return token;
                }
            case SHORT_COMMENT:
                while (input[position] != '\n') {
                    position++;
                }
                line++;
                position++;
                state = TokenizerState.STANDARD;
                return tokenize();
            case LONG_COMMENT:
                while (input[position] != '*' || input[position + 1] != '/') {
                    if (input[position] == '\n') {
                        line++;
                    }
                    position++;
                }
                position += 2;
                state = TokenizerState.STANDARD;
                return tokenize();
        }
        return tokenize();
    }
}
