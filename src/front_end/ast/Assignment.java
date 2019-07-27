package front_end.ast;

import front_end.LocalVariable;

public class Assignment implements Statement {
    private final LocalVariable variable;
    private final int value;
    public Assignment(final LocalVariable variable, final int value) {
        this.variable = variable;
        this.value = value;
    }
    @Override
    public String build() {
        return "  mov  rax, rbp\n" +
                "  sub  rax, " +
                variable.offset +
                "\n" +
                "  mov  [rax], " +
                value +
                "\n";
    }
    @Override
    public String toS(int tab) {
        return "(assign " + variable.name + " " + value + ")";
    }
}
