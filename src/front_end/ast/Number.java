package front_end.ast;

public class Number implements Expression {
    private final int value;
    public Number(final int value) {
        this.value = value;
    }
    @Override
    public String build() {
        return "  push " + value + "\n";
    }
    @Override
    public String toS(int tab) {
        return "" +value;
    }
}
