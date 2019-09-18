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
    @Override
    public Code reduce() {
        return this;
    }
    @Override
    public int toWC() {
        return 0;
    }
    @Override
    public String toAssembly() {
        return "\tmov     rax, " + operand.toAssembly() +
                "\n\tmov     rsp, rbp\n\tpop     rbp\n\tret\n";
    }
}
