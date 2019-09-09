package assembly;

public class Label extends Instruction {
    private final String name;
    private final boolean is_global;
    Label(final String name, final boolean is_global) {
        this.name = name;
        this.is_global = is_global;
    }
    @Override
    String build() {
        if (is_global) {
            return ".global " + name + "\n" + name + ":";
        } else {
            return ".L" + name + ":";
        }
    }
    String name() {
        return name;
    }
}
