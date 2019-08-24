package middle_end;

public class Label {
    private final String name;
    Label(final String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return name;
    }
}
