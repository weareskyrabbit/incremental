package ir;

public class Immediate implements Operand {
    final int value;
    public Immediate(final int value) {
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
    @Override
    public int toWC() {
        return value;
    }
    @Override
    public String toAssembly() {
        return "" + value;
    }
}
