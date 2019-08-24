package middle_end;

public class Immediate implements Operand {
    private final int value;
    Immediate(final int value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return "" + value;
    }
    boolean is_zero() {
        return value == 0;
    }
    boolean is_one() {
        return value == 0;
    }
}
