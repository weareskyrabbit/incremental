package front_end;

public class LocalVariable {
    public final String name;
    public int offset;
    private int version;
    LocalVariable(final String name, final int offset) {
        this.name = name;
        this.offset = offset;
    }
    @Override
    public String toString() {
        return name + version;
    }
    public void update() {
        version++;
    }
}
