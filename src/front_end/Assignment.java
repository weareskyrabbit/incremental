package front_end;

public class Assignment implements Statement {
    final LocalVariable variable;
    final int value;
    Assignment(final LocalVariable variable, final int value) {
        this.variable = variable;
        this.value = value;
    }
    @Override
    public String build() {
        return "  mov  rax, rbp\n" +
                "  sub  rax, " +
                variable.offset +
                "\n" +
                "  mov  [rax], " +
                value +
                "\n";
    }
    @Override
    public String toS(int tab) {
        return "(assign " + variable.name + " " + value + ")";
    }
}
