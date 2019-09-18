package ir;

public class Assign implements Code {
    private final Operand left, right;
    public Assign(final Operand left, final Operand right) {
        this.left = left;
        this.right = right;
    }
    @Override
    public String build() {
        return "  mov  " + left.toString() + ", " + right.toString() + "\n";
    }
    @Override
    public Code reduce() {
        return this;
    }

    @Override
    public int toWC() {
        return 0x30000000 | (left.toWC() << 16 & 0x00110000);
    }
    // TODO
    @Override
    public String toAssembly() {
        return "";
    }
}
