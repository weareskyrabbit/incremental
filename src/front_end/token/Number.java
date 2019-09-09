package front_end.token;

public class Number extends Token {
    public final int value;
    public Number(int value) {
        super(Tag.NUM);
        this.value = value;
    }
    @Override
    public String toString() {
        return "" + value;
    }
}
