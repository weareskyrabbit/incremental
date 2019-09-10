package ir;

public class Register implements Operand {
    private final String name;
    public Register(final String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return name;
    }
    @Override
    public String build() { // TODO review
        return "  mov  rax, " +
                name +
                "\n";
    }
    @Override
    public int toWordCode() {
        return 0;
    }
}
