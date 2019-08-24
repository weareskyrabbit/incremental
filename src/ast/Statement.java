package ast;

import middle_end.Instruction;

public abstract class Statement implements Node {
    int after = 0;
    // translate AST into IR
    public void generate(final int before, final int after) {}
    public abstract Instruction gen();
}
