package middle_end;

public class Address implements Operand {
    private final Register register;
    Address(final Register register) {
        this.register = register;
    }
    @Override
    public String toString() {
        return "[" + register.toString() + "]";
    }
}
