package front_end;

public class Assignment extends Statement {
    final LocalVariable variable;
    final int value;
    Assignment(final LocalVariable variable, final int value) {
        this.variable = variable;
        this.value = value;
    }
    @Override
    String build() {
        return "  mov rax, rbp\n" +
                "  sub rax, " +
                variable.offset +
                "\n" +
                "  mov [rax], " +
                value +
                "\n";
    }
    @Override
    public String toString() {
        return "(assign " + variable.name + " " + value + ")";
    }
}
