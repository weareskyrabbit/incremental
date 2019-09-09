package assembly;

public class Sub extends Instruction {
    private final Operand left, right;
    Sub(final Operand left, final Operand right) {
        this.left = left;
        this.right = right;
    }
    @Override
    String build() {
        return "  sub  " + left.build() + ", " + right.build();
    }
}
