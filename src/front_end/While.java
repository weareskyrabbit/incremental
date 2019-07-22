package front_end;

public class While extends Statement {
    final int condition;
    final Closure closure;
    private int count;
    While(final int condition, final Closure closure) {
        this.condition = condition;
        this.closure = closure;
    }
    @Override
    String build() {
        String assembly = ".Lbegin" +
                count +
                ":\n" +
                "  mov rax, " +
                condition +
                '\n' +
                "  cmp rax, 0\n" +
                "  je  .Lend" +
                count +
                '\n' +
                closure.build() +
                "  jmp .Lbegin" +
                count +
                '\n' +
                ".Lend" +
                count +
                ":\n";
        count++;
        return assembly;
    }
    @Override
    public String toString() {
        return "(while " + condition + " " + closure + ")";
    }
}
