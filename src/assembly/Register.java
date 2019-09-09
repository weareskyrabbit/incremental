package assembly;

public class Register extends Operand {
    private final String name;
    private Register(final String name) {
        this.name = name;
    }
    static final Register
            RBP = new Register("rbp"),
            RSP = new Register("rsp"),
            RAX = new Register("rax"),
            RDI = new Register("rdi"),
            RSI = new Register("rsi");
    @Override
    String build() {
        return name;
    }
}
