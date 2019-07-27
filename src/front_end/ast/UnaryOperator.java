package front_end.ast;

public class UnaryOperator implements Node {
    private final int operand;
    public UnaryOperator(final int operand) {
        this.operand = operand;
    }
    @Override
    public String build() {
        return null;
    }
    @Override
    public String toS(int tab) {
        return "(- " + operand + ")";
    }
}
