package front_end.ast;

public class BinaryOperator implements Expression {
    private final String operator;
    private final Expression left;
    private final Expression right;
    public BinaryOperator(final String operator, final Expression left, final Expression right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }
    @Override
    public String build() {
        switch (operator) {
            case "+":
                return "  pop  rdi\n" +
                        "  pop  rax\n" +
                        "  add  rax, rdi\n" +
                        "  push rax\n";
            case "-":
                return "  pop  rdi\n" +
                        "  pop  rax\n" +
                        "  sub  rax, rdi\n" +
                        "  push rax\n";
            case "*":
                return "  pop  rdi\n" +
                        "  pop  rax\n" +
                        "  imul rax, rdi\n" +
                        "  push rax\n";
            case "/":
                return "  pop  rdi\n" +
                        "  pop  rax\n" +
                        "  cqo\n" +
                        "  idiv rdi\n" +
                        "  push rax\n";
            default:
                System.out.println("expected operator, but found `" + operator + "`");
                System.exit(1);
                return null;
        }
    }
    @Override
    public String toS(int tab) {
        tab += 2 + operator.length();
        return "(" + operator + " " + left.toS(tab) + "\n" + right.toS(tab);
    }
}
