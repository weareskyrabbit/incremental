package ast;

import back_end.Builder;
import middle_end.Instruction;
import middle_end.InstructionType;

import static middle_end.IRGenerator._return;
import static middle_end.IRGenerator.emit;

public class Return extends Statement {
    private final Expression expression; // TODO implement Expression
    public Return(final Expression expression) {
        this.expression = expression;
    }
    @Override
    public void generate(final int before, final int after) {
        Expression temporary = expression.reduce();
        emit("return " + temporary.toString());
        _return(temporary.toString());
    }
    @Override
    public String build() {
        return expression.build() +
                Builder.epilogue();
    }
    @Override
    public String toS(int tab) {
        tab += 8;
        return "(return " + expression.toS(tab) + ")";
    }
    @Override
    public Instruction gen() {
        expression.gen();
        return new Instruction(InstructionType.RET, null, null);
    }
}
