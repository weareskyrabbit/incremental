package front_end;

import front_end.token.Token;
import front_end.token.Word;

public abstract class Tokenizer {
    // input
    private char[] input;
    // line in file
    private int line;
    // offset in line
    private int offset;
    // reserve keyword
    abstract void reserve(final Word word);
    // tokenize
    abstract Token tokenize();
}
