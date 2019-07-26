package front_end;

import back_end.Builder;

public class Return implements Statement {
    private final int operand; // TODO implement Expression
    Return(final int operand) {
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
