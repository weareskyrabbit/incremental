package assembly;

public class Memory extends Operand {
    private final Operand operand;
    Memory(final Operand operand) {
        this.operand = operand;
    }
    @Override
    String build() {
        return "[" + operand.build() + "]";
    }
}
