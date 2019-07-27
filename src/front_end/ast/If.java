package front_end.ast;

import static front_end.Parser.tab;

public class If implements Statement {
    private final Expression condition;
    private final Closure then_closure;
    private static int count;
    public If (final Expression condition, final Closure then_closure) {
        this.condition = condition;
        this.then_closure = then_closure;
    }
    public String toS(int tab) {
        StringBuilder s = new StringBuilder();
        tab += 4;
        s.append("(if ")
                .append(condition.toS(tab))
                .append('\n');
        s.append(tab(tab))
                .append(then_closure.toS(tab))
                .append(')');
        return s.toString();
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
}
