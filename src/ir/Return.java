package ir;

import back_end.Builder;

public class Return implements Code {
    private final Operand operand;
    public Return(final Operand operand) {
        this.operand = operand;
    }
    @Override
    public String toString() {
        return "\treturn " + operand.toString() + "\n";
    }
    @Override
    public String build() {
        return "  pop  rax\n" +
                Builder.epilogue();
    }
}
