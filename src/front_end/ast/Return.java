package front_end.ast;

import back_end.Builder;

public class Return implements Statement {
    private final Expression expression; // TODO implement Expression
    public Return(final Expression expression) {
        this.expression = expression;
    }
    @Override
    public String toIR() {
        return "return " + expression.toIR();
    }
    @Override
    public String build() {
        return expression.build() +
                Builder.epilogue();
    }
    @Override
    public String toS(int tab) {
        tab += 8;
        return "(return " + expression.toS(tab) + ")";
    }
}
