package ast;

import middle_end.Instruction;
import middle_end.InstructionType;
import middle_end.Register;

import java.util.ArrayList;
import java.util.List;

import static middle_end.IRGenerator.emit;
import static middle_end.IRGenerator.three_address;

public abstract class Operator extends Expression {
    Operator(final String operator) {
        super(operator);
    }
    /*
     * reduce expression
     * example : `(+ 1 (* 2 3))` -> `t0 = 2 * 3\nt1 = 1 + t0`
     */
    /*
    @Override
    public Expression reduce() {
        final Temporary temporary = new Temporary();
        final Expression expression = generate();
        emit(temporary.toString() + " = " + expression.toString());
        if (expression instanceof BinaryOperator) {
            three_address(temporary.toString(), ((BinaryOperator)expression).left_toOperand(),
                    expression.operator, ((BinaryOperator)expression).right_toOperand());
        } else if (expression instanceof FunctionCall) {
            three_address(temporary.toString(), expression.toString());
        }
        // TODO remove `reduce()` in `FunctionCall::toString()`
        // if reduce() is called in extra toString(), emit extra line
        return temporary;
    }
    */
    public List<Instruction> red() {
        final List<Instruction> list = new ArrayList<>();
        final Temporary temporary = new Temporary();
        if (this instanceof BinaryOperator) {
            list.addAll(((BinaryOperator)this).gen());
        }
        list.add(new Instruction(InstructionType.POP, new Register(temporary.toString()), null));
        return list;
    }
}
