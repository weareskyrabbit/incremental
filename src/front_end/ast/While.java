package front_end.ast;

public class While implements Statement {
    private final Expression condition;
    private final Closure closure;
    private static int count = 0;
    public While(final Expression condition, final Closure closure) {
        this.condition = condition;
        this.closure = closure;
    }
    @Override
    public String toIR() {
        return "while (" + condition.toIR() + ") " + closure.toIR();
    }
    @Override
    public String build() {
        final String assembly = ".Lwhilebegin" +
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
        final StringBuilder builder = new StringBuilder();
        tab += 7;
        final String s = condition.toS(tab);
        tab += 1 + s.length();
        builder.append("(while ")
                .append(s)
                .append(' ')
                .append(closure.toS(tab))
                .append(')');
        return builder.toString();
    }
}
