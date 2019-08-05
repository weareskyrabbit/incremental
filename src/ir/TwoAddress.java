package ir;

public class TwoAddress implements Code {
    private final Register register;
    private final Operand operand;
    public TwoAddress(final Register register, final Operand operand) {
        this.register = register;
        this.operand = operand;
    }
    @Override
    public String toString() {
        return "\t" + register.toString() + " = " + operand.toString() + "\n";
    }
    @Override
    public String build() {
        return operand.build() +
                register.build() +
                "  mov  rax, rdi\n";
    }
    @Override
    public Code reduce() {
        return this;
    }
}
