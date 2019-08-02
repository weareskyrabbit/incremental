package ir;

public class Print implements Code {
    private final Operand operand;
    public Print(final Operand operand) {
        this.operand = operand;
    }
    @Override
    public String toString() {
        return "\tprint " + operand.toString() + "\n";
    }
    @Override
    public String build() {
        return "  out  " + operand.toString();
    }
}
