package assembly;

public class Immediate extends Operand {
    private final int value;
    Immediate(final int value) {
        this.value = value;
    }
    @Override
    String build() {
        return "" + value;
    }
}
