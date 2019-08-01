package ast;

public class UnaryOperator extends Operator {
    private final Expression operand;
    private static int count = 0;
    public UnaryOperator(final String operator, final Expression operand) {
        super(operator);
        this.operand = operand;
    }
    @Override
    public Expression generate() {
        return new UnaryOperator(operator, operand.reduce());
    }
    @Override
    public String build() {
        switch (operator) {
            case "-":
                return "  pop  rax\n  neg  rax\n  push rax\n";
            default:
                System.out.println("expected operator, but found `" + operator + "`");
                System.exit(1);
                return null;
        }
    }
    @Override
    public String toS(int tab) {
        tab += 2 + operator.length();
        return "(" + operator + " " + operand.toS(tab) + ")";
    }
    @Override
    public String toString() {
        return operator + " " + operand.toString();
    }
}
