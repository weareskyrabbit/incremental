package front_end.ast;

public class Assignment implements Statement {
    private final VariableCall variable;
    private final Expression expression;
    public Assignment(final VariableCall variable, final Expression expression) {
        this.variable = variable;
        this.expression = expression;
    }
    @Override
    public String build() {
        return variable.build() +
                expression.build() +
                "  pop  rdi\n" +
                "  pop  rax\n" +
                "  mov  [rax], rdi\n";
    }
    @Override
    public String toS(int tab) {
        tab += 8;
        return "(assign " +
                variable.toS(tab) +
                "\n" +
                expression.toS(tab) +
                ")";
    }
}
