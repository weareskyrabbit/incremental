package ir;

public class Number implements Operand {
    private final int value;
    Number(final int value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return "" + value;
    }
    @Override
    public String build() {
        return "push " + value;
    }
}
