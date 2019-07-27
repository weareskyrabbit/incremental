package front_end.ast;

public class Print implements Statement {
    private final Expression expression;
    public Print(final Expression expression) {
        this.expression = expression;
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
