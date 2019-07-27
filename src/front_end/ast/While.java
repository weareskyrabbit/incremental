package front_end.ast;

import static front_end.Parser.tab;

public class While implements Statement {
    private final Expression condition;
    private final Closure closure;
    private static int count;
    public While(final Expression condition, final Closure closure) {
        this.condition = condition;
        this.closure = closure;
    }
    @Override
    public String build() {
        String assembly = ".Lwhilebegin" +
                count +
                ":\n" +
                condition.build() +
                "  cmp  rax, 0\n" +
                "  je   .Lend" +
                count +
                '\n' +
                closure.build() +
                "  jmp  .Lwhilebegin" +
                count +
                '\n' +
                ".Lwhileend" +
                count +
                ":\n";
        count++;
        return assembly;
    }
    public String toS(int tab) {
        StringBuilder s = new StringBuilder();
        tab += 7;
        s.append("(while ")
                .append(condition.toS(tab))
                .append('\n');
        s.append(tab(tab))
                .append(closure.toS(tab))
                .append(')');
        return s.toString();
    }
}
