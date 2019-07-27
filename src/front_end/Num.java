package front_end;

public class Num extends Token {
    /* member variables */
    public final int value;
    /* constructors */
    public Num(int value) {
        super(Tag.NUM);
        this.value = value;
    }
    /* member functions */
    @Override
    public String toString() {
        return "" + value;
    }
}
