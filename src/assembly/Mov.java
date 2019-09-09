package assembly;

public class Mov extends Instruction {
    private final Operand left, right;
    Mov(final Operand left, final Operand right) {
        this.left = left;
        this.right = right;
    }
    @Override
    String build() {
        return "  mov  " + left.build() + ", " + right.build();
    }
}
