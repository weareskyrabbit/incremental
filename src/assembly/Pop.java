package assembly;

public class Pop extends Instruction {
    private final Operand operand;
    Pop(final Operand operand) {
        this.operand = operand;
    }
    @Override
    String build() {
        return "  pop  " + operand.build();
    }
}
