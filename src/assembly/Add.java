package assembly;

public class Add extends Instruction {
    private final Operand left, right;
    Add(final Operand left, final Operand right) {
        this.left = left;
        this.right = right;
    }
    @Override
    String build() {
        return "  sub  " + left.build() + ", " + right.build();
    }
}
