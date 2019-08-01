package front_end.token;

public class Token {
    public final int tag;
    public Token(int tag) {
        this.tag = tag;
    }
    @Override
    public String toString() {
        return "" + (char) tag;
    }
}