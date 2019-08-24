package ast;

import middle_end.Instruction;
import middle_end.InstructionType;
import middle_end.Register;

import static middle_end.IRGenerator._print;
import static middle_end.IRGenerator.emit;

public class Print extends Statement {
    private final Expression expression;
    public Print(final Expression expression) {
        this.expression = expression;
    }
    @Override
    public void generate(final int before, final int after) {
        Expression temporary = expression.reduce();
        emit("print " + temporary.toString());
        _print(temporary.toString());
    }
    @Override
    public String build() {
        return expression.build() + "  out  rax\n";
    }
    @Override
    public String toS(int tab) {
        tab += 7;
        return "(print " + expression.toS(tab) + ")";
    }
    @Override
    public Instruction gen() {
        expression.gen();
        return new Instruction(InstructionType.OUT, Register.RAX, null);
    }
}
