package front_end.ast;

import back_end.Builder;

public class Return implements Statement {
    private final int operand; // TODO implement Expression
    public Return(final int operand) {
        this.operand = operand;
    }
    @Override
    public String build() {
        return "  mov  rax, " +
                operand +
                "\n" +
                Builder.epilogue();
    }
    @Override
    public String toS(int tab) {
        return "(return " + operand + ")";
    }
}
