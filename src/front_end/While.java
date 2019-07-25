package front_end;

import static front_end.Parser.tab;

public class While implements Statement {
    final int condition;
    final Closure closure;
    private static int count;
    While(final int condition, final Closure closure) {
        this.condition = condition;
        this.closure = closure;
    }
    @Override
    public String build() {
        String assembly = ".Lwhilebegin" +
                count +
                ":\n" +
                "  mov  rax, " +
                condition +
                '\n' +
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
        s.append("(while ")
                .append(condition)
                .append('\n');
        tab += 7;
        s.append(tab(tab))
                .append(closure.to_S(tab))
                .append(')');
        return s.toString();
    }
}
