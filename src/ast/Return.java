package ast;

import back_end.Builder;
import middle_end.Instruction;
import middle_end.InstructionType;
import middle_end.Register;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<Instruction> gen() {
        final List<Instruction> list = new ArrayList<>(expression.red());
        list.add(new Instruction(InstructionType.POP, Register.RAX, null));
        list.add(new Instruction(InstructionType.MOV, Register.RSP, Register.RBP));
        list.add(new Instruction(InstructionType.POP, Register.RBP, null));
        list.add(new Instruction(InstructionType.RET, null, null));
        return list;
    }
    @Override
    public List<ir.Code> toIR() {
        return Collections.singletonList(new ir.Return(expression.toIR()));
    }
}
