package ast;

import middle_end.Instruction;

import java.util.List;

import static middle_end.IRGenerator.emit_label;
import static middle_end.IRGenerator.new_label;

public class If extends Statement {
    private final Expression condition;
    private final Closure then_closure;
    private /*final*/ Closure else_closure;
    private static int count = 0;
    public If (final Expression condition, final Closure then_closure) {
        this.condition = condition;
        this.then_closure = then_closure;
    }
    @Override
    public void generate(final int before, final int after) {
        final int label = new_label();
        condition.jumping(0, after);
        emit_label(label);
        then_closure.generate(label, after);
    }
    @Override
    public String build() {
        count++;
        return condition.build() +
                "  cmp  rax, 0\n" +
                "  je   .Lifend" +
                count +
                "\n" +
                then_closure.build() +
                ".Lifend:\n";

    }
    @Override
    public String toS(int tab) {
        final StringBuilder builder = new StringBuilder();
        tab += 4;
        final String s = condition.toS(tab);
        tab += 1 + s.length();
        builder.append("(if ")
                .append(s)
                .append(' ')
                .append(then_closure.toS(tab))
                .append(')');
        return builder.toString();
    }
    @Override
    public List<Instruction> gen() {
        return null;
    }
}
