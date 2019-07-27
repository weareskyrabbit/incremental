package front_end;

public class LocalVariable {
    public final String name;
    public int offset;
    LocalVariable(final String name, final int offset) {
        this.name = name;
        this.offset = offset;
    }
}
