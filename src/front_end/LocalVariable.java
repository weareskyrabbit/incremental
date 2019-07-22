package front_end;

public class LocalVariable {
    final String name;
    int value;
    int offset;
    LocalVariable(final String name, final int offset) {
        this.name = name;
        this.value = 0;
        this.offset = offset;
    }
}
