package front_end.ast;

import static front_end.Parser.tab;

public class Assignment implements Statement {
    private final VariableCall variable;
    private final Expression expression;
    public Assignment(final VariableCall variable, final Expression expression) {
        this.variable = variable;
        this.expression = expression;
    }
    @Override
    public String toIR() {
        return variable.toIR() + " = 0 + " + expression.toIR();
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
        final StringBuilder builder = new StringBuilder();
        builder.append("(assign ")
                .append(variable.toS(tab));
        final String s = expression.toS(tab);
        if (s.length() > 4) {
            builder.append('\n')
                    .append(tab(tab));
        } else {
            builder.append(' ');
        }
        builder.append(s)
                .append(')');
        return  builder.toString();
    }
}
