package middle_end;

public class Register implements Operand {
    private final String name;
    Register(final String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return name;
    }
    public static final Register
            RAX = new Register("rax"),
            RBP = new Register("rbp"),
            RSP = new Register("rsp"),
            RDI = new Register("rdi"),
            RSI = new Register("rsi"),
            RDX = new Register("rdx"),
            RCX = new Register("rcx"),
            R8  = new Register("r8"),
            R9  = new Register("r9");
}
