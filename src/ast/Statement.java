package ast;

public abstract class Statement implements Node {
    int after = 0;
    // translate AST into IR
    public void generate(final int before, final int after) {}
}
