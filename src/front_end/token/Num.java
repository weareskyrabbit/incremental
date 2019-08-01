package front_end.token;

import front_end.Tag;

public class Num extends Token {
    public final int value;
    public Num(int value) {
        super(Tag.NUM);
        this.value = value;
    }
    @Override
    public String toString() {
        return "" + value;
    }
}
