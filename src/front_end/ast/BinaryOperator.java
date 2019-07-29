package front_end.ast;

import static front_end.Parser.tab;

public class BinaryOperator implements Expression {
    private final String operator;
    private final Expression left;
    private final Expression right;
    private static int count;
    public BinaryOperator(final String operator, final Expression left, final Expression right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }
    @Override
    public String toIR() {
        count++;
        return "b" + count + " = " + left.toIR() + " " + operator + " " + right.toIR();
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
        final StringBuilder builder = new StringBuilder();
        builder.append('(')
                .append(operator)
                .append(' ')
                .append(left.toS(tab));
        final String s = right.toS(tab);
        if (s.length() > 4) {
            builder.append('\n')
                    .append(tab(tab));
        } else {
            builder.append(' ');
        }
        builder.append(s)
                .append(')');
        return builder.toString();
    }
}
