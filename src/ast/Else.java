package ast;

import middle_end.Instruction;

import java.util.List;

import static middle_end.IRGenerator.emit;
import static middle_end.IRGenerator.emit_label;
import static middle_end.IRGenerator.new_label;

public class Else extends Statement {
    private final Expression condition;
    private final Statement statement1;
    private final Statement statement2;
    public Else(final Expression condition, final Statement statement1, final Statement statement2) {
        this.condition = condition;
        this.statement1 = statement1;
        this.statement2 = statement2;
    }
    @Override
    public void generate(final int before, final int after) {
        int label1 = new_label();
        int label2 = new_label();
        condition.jumping(0, after);
        emit_label(label1);
        statement1.generate(label1, after);
        emit("goto L" + after);
        emit_label(label2);
        statement2.generate(label2, after);
    }
    @Override
    public String build() {
        return "";
    }
    @Override
    public String toS(final int tab) {
        return "";
    }
    @Override
    public List<Instruction> gen() {
        return null;
    }
}
