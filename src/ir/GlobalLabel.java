package ir;

public class GlobalLabel implements Code {
    private final String name;
    public GlobalLabel(final String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return name + ":\n";
    }
    @Override
    public String build() {
        return ".global " + name + "\n" + name + ":\n";
    }
    @Override
    public Code reduce() {
        return this;
    }
}
