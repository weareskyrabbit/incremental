package front_end.ast;

public class UnaryOperator implements Expression {
    private final String operator;
    private final Expression operand;
    public UnaryOperator(final String operator, final Expression operand) {
        this.operator = operator;
        this.operand = operand;
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
}
