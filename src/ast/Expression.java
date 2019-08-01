package ast;

import static middle_end.IRGenerator.emit_jumps;

public abstract class Expression implements Node {
    String operator;
    Expression(final String operator) {
        this.operator = operator;
    }
    public Expression generate() {
        return this;
    }
    public Expression reduce() {
        return this;
    }
    public void jumping(final int _true, final int _false) {
        emit_jumps(toString(), _true, _false);
    }
    @Override
    public String toString() {
        return operator;
    }
}
