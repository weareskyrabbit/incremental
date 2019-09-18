package ast;

import middle_end.Instruction;

import java.util.List;

public abstract class Statement implements Node {
    int after = 0;
    // translate AST into IR
    public void generate(final int before, final int after) {}
    public abstract List<Instruction> gen();
    public List<ir.Code> toIR() {
        return null;
    }
}
