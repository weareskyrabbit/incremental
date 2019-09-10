package ir;

public class ThreeAddress implements Code {
    private final Register register;
    private final Operand left;
    private final String operator;
    private final Operand right;
    public ThreeAddress(final Register register, final Operand left, final String operator,
                 final Operand right) {
        this.register = register;
        this.left = left;
        this.operator = operator;
        this.right = right;
    }
    @Override
    public String toString() {
        return "\t" + register.toString() + " = " + left.toString() + " " + operator + " " +
                right.toString() + "\n";
    }
    @Override
    public String build() {
        final StringBuilder assembly = new StringBuilder();
        assembly.append(register.build())
                .append("  pop  rdi\n  pop  rax\n");
        switch (operator) {
            case "+":
                assembly.append("  add  rax, rdi\n  push rax\n");
            case "-":
                assembly.append("  sub  rax, rdi\n  push rax\n");
            case "*":
                assembly.append("  imul rax, rdi\n  push rax\n");
            case "/":
                assembly.append("  cqo\n  idiv rdi\n  push rax\n");
        }
        assembly.append(left.build());
        assembly.append(right.build());
        return assembly.toString();
    }
    public Code reduce() {
        if (left instanceof Immediate && right instanceof Immediate) {
            final int left_value = ((Immediate)left).value;
            final int right_value = ((Immediate)right).value;
            switch (operator) {
                case "+":
                    return new TwoAddress(register, new Immediate(left_value + right_value));
                case "-":
                    return new TwoAddress(register, new Immediate(left_value - right_value));
                case "*":
                    return new TwoAddress(register, new Immediate(left_value * right_value));
                case "/":
                    return new TwoAddress(register, new Immediate(left_value / right_value));
            }
        } else if (left instanceof Immediate) {
            final int left_value = ((Immediate)left).value;
            switch (operator) {
                case "+": case "-":
                    if (left_value == 0) {
                        return new TwoAddress(register, left);
                    }
                case "*": case "/":
                    if (left_value == 1) {
                        return new TwoAddress(register, left);
                    } else if (left_value == 0) {
                        return new TwoAddress(register, new Immediate(0));
                    }
            }
        } else if (right instanceof Immediate) {
            final int right_value = ((Immediate)right).value;
            switch (operator) {
                case "+": case "-":
                    if (right_value == 0) {
                        return new TwoAddress(register, right);
                    }
                case "*": case "/":
                    if (right_value == 1) {
                        return new TwoAddress(register, right);
                    } else if (right_value == 0) {
                        return new TwoAddress(register, new Immediate(0));
                    }
            }
        }
        return this;
    }
    @Override
    public int toWordCode() {
        return 0;
    }
}
