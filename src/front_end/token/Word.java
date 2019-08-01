package front_end.token;

import front_end.Tag;

public class Word extends Token {
    public String lexeme = "";
    public Word(String lexeme, int tag) {
        super(tag);
        this.lexeme = lexeme;
    }
    @Override
    public String toString() {
        return lexeme;
    }
    public static final Word
        and   = new Word("&&", Tag.AND),      or    = new Word("||", Tag.OR),
        eq    = new Word("==", Tag.EQ),       ne    = new Word("!=", Tag.NE),
        le    = new Word("<=", Tag.LE),       ge    = new Word(">=", Tag.GE),
        minus = new Word("minus", Tag.MINUS), temp  = new Word("t", Tag.TEMP),
        True  = new Word("true", Tag.TRUE),   False = new Word("false", Tag.FALSE);

}
