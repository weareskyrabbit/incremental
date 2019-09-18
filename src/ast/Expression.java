package ast;

import middle_end.Instruction;
import middle_end.InstructionType;

import java.util.Collections;
import java.util.List;

import static middle_end.IRGenerator.emit_jumps;

public abstract class Expression implements Node {
    String operator;
    Expression(final String operator) {
        this.operator = operator;
    }
    // translate AST into IR
    public Expression generate() {
        return this;
    }
    public Expression reduce() {
        return this;
    }
    /*
     * emit jumping
     * example : `if (n) { A }` -> `iffalse n goto L0\nA\nL0:`
     */
    public void jumping(final int _true, final int _false) {
        emit_jumps(toString(), _true, _false);
    }
    @Override
    public String toString() {
        return operator;
    }
    public List<Instruction> red() {
        return Collections.singletonList(new Instruction(InstructionType.NOP, null, null));
    }
    public ir.Operand toIR() {
        return null;
    }
}
