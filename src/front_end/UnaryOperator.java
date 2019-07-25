package front_end;

public class UnaryOperator implements Node {
    final int operand;
    UnaryOperator(final int operand) {
        this.operand = operand;
    }
    @Override
    public String build() {
        return null;
    }
    @Override
    public String toString() {
        return "(- " + operand + ")";
    }
}
