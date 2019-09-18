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
    @Override
    public Code reduce() {
        return this;
    }

    @Override
    public int toWC() {
        if (operand instanceof String_) {
            return 0x30000000 | (operand.toWC() << 16 & 0xff0000);
        } else {
            return 0x31000000 | (operand.toWC() << 16 & 0xff0000);
        }
    }
    // TODO
}
