package front_end;

import static front_end.Parser.tab;

public class If implements Statement {
    final int condition;
    final Closure then_closure;
    private static int count;
    If (final int condition, final Closure then_closure) {
        this.condition = condition;
        this.then_closure = then_closure;
    }
    public String toS(int tab) {
        StringBuilder s = new StringBuilder();
        s.append("(if ")
                .append(condition)
                .append('\n');
        tab += 4;
        s.append(tab(tab))
                .append(then_closure.toS(tab))
                .append(')');
        return s.toString();
    }
    @Override
    public String build() {
        return "  mov  rax, " +
                condition +
                "\n" +
                "  cmp  rax, 0\n" +
                "  je   .Lifend" +
                count +
                "\n" +
                then_closure.build() +
                ".Lifend:\n";

    }
}
