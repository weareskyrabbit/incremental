package front_end.ast;

import front_end.LocalVariable;

public class VariableCall implements Expression {
    private final LocalVariable local;

    public VariableCall(final LocalVariable local) {
        this.local = local;
    }
    @Override
    public String build() {
        return "  mov  rax, rbp\n" +
                "  sub  rax, " +
                local.offset +
                "\n" +
                "  push [rax]\n";
    }
    @Override
    public String toS(int tab) {
        return local.name;
    }
}
