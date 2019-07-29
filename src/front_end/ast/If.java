package front_end.ast;

public class If implements Statement {
    private final Expression condition;
    private final Closure then_closure;
    private static int count = 0;
    public If (final Expression condition, final Closure then_closure) {
        this.condition = condition;
        this.then_closure = then_closure;
    }
    @Override
    public String toIR() {
        return "if (" + condition.toIR() + ") " + then_closure.toIR();
    }
    @Override
    public String build() {
        count++;
        return condition.build() +
                "  cmp  rax, 0\n" +
                "  je   .Lifend" +
                count +
                "\n" +
                then_closure.build() +
                ".Lifend:\n";

    }
    @Override
    public String toS(int tab) {
        final StringBuilder builder = new StringBuilder();
        tab += 4;
        final String s = condition.toS(tab);
        tab += 1 + s.length();
        builder.append("(if ")
                .append(s)
                .append(' ')
                .append(then_closure.toS(tab))
                .append(')');
        return builder.toString();
    }
}
