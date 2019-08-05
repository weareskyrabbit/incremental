package ast;

import ir.Immediate;
import ir.Operand;
import ir.Register;

import static front_end.RecursiveDescentParser.tab;

public class BinaryOperator extends Operator {
    private final Expression left;
    private final Expression right;
    public BinaryOperator(final String operator, final Expression left, final Expression right) {
        super(operator);
        this.left = left;
        this.right = right;
    }
    @Override
    public Expression generate() {
        return new BinaryOperator(operator, left.reduce(), right.reduce());
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
    @Override
    public String toString() {
        return left.toString() + " " + operator + " " + right.toString();
    }
    Operand left_toOperand() {
        if (left instanceof Temporary) {
            return new Register(left.toString());
        } else if (left instanceof Number) {
            return new Immediate(((Number)left).value);
        }
        return new Register("");
    }
    Operand right_toOperand() {
        if (right instanceof Temporary) {
            return new Register(right.toString());
        } else if (right instanceof Number) {
            return new Immediate(((Number)right).value);
        }
        return new Register("");
    }
}
