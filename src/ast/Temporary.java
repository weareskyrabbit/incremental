package ast;

public class Temporary extends Expression {
    private static int count;
    Temporary() {
        super("$" + ++count);
    }
    @Override
    public String toS(int tab) {
        return "";
    }
    @Override
    public String build() {
        return "";
    }
}
