package front_end;

public class Assignment extends Statement {
    final String name;
    final int value;
    Assignment(final String name, final int value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return "(assign " + name + " " + value + ")";
    }
}
