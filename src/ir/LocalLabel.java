package ir;

public class LocalLabel implements Code {
    private final int index;
    public LocalLabel(final int index) {
        this.index = index;
    }
    @Override
    public String toString() {
        return "L" + index + ":";
    }
    @Override
    public String build() {
        return ".L" + index + ":\n";
    }
    @Override
    public Code reduce() {
        return this;
    }
    @Override
    public int toWC() {
        return 0;
    }
}
