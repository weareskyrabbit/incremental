package front_end;

public class UnaryOperator extends Node {
    final int operand;
    UnaryOperator(final int operand) {
        this.operand = operand;
    }
    @Override
    public String toString() {
        return "(- " + operand + ")";
    }
}
