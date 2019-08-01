package ast;

import static middle_end.IRGenerator.emit;

public abstract class Operator extends Expression {
    Operator(final String operator) {
        super(operator);
    }
    /*
     * reduce expression
     * example : `(+ 1 (* 2 3))` -> `t0 = 2 * 3\nt1 = 1 + t0`
     */
    @Override
    public Expression reduce() {
        final Temporary temporary = new Temporary();
        final Expression expression = generate();
        emit(temporary.toString() + " = " + expression.toString());
        return temporary;
    }
}
