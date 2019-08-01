package ast;

import static middle_end.IRGenerator.emit;

public class Print extends Statement {
    private final Expression expression;
    public Print(final Expression expression) {
        this.expression = expression;
    }
    @Override
    public void generate(final int before, final int after) {
        Expression temporary = expression.reduce();
        emit("print " + temporary.toString());
    }
    @Override
    public String build() {
        return expression.build() + "  out  rax\n";
    }
    @Override
    public String toS(int tab) {
        tab += 7;
        return "(print " + expression.toS(tab) + ")";
    }
}
