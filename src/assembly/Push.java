package assembly;

public class Push extends Instruction {
    private final Operand operand;
    Push(final Operand operand) {
        this.operand = operand;
    }
    @Override
    String build() {
        return "  push " + operand.build();
    }
}
