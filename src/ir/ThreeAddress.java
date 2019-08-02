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
}
