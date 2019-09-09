package ast;

import middle_end.Instruction;
import middle_end.InstructionType;
import middle_end.Register;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static front_end.RecursiveDescentParser.tab;
import static middle_end.IRGenerator.emit;
import static middle_end.IRGenerator.three_address;

public class Assignment extends Statement {
    private final VariableCall variable;
    private final Expression expression;
    public Assignment(final VariableCall variable, final Expression expression) {
        this.variable = variable;
        this.expression = expression;
    }
    @Override
    public void generate(final int before, final int after) {
        emit(variable.toString()  + " = " + expression.generate().toString());
        three_address(variable.toString(), expression.generate().toString());
    }
    @Override
    public String build() {
        return variable.build() +
                expression.build() +
                "  pop  rdi\n" +
                "  pop  rax\n" +
                "  mov  [rax], rdi\n";
    }
    @Override
    public String toS(int tab) {
        tab += 8;
        final StringBuilder builder = new StringBuilder();
        builder.append("(assign ")
                .append(variable.toS(tab));
        final String s = expression.toS(tab);
        if (s.length() > 4) {
            builder.append('\n')
                    .append(tab(tab));
        } else {
            builder.append(' ');
        }
        builder.append(s)
                .append(')');
        return  builder.toString();
    }
    @Override
    public List<Instruction> gen() {
        final List<Instruction> list = new ArrayList<>(expression.red());
        list.add(new Instruction(InstructionType.POP, new Register(variable.toString()), null));
        return list;
    }
}
