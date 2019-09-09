package ast;

import middle_end.Instruction;

import java.util.List;

import static middle_end.IRGenerator.*;

public class While extends Statement {
    private final Expression condition;
    private final Closure closure;
    private static int count = 0;
    public While(final Expression condition, final Closure closure) {
        this.condition = condition;
        this.closure = closure;
    }
    @Override
    public void generate(final int before, int after) {
        this.after = after;
        final int label = new_label();
        condition.jumping(0, after);
        emit_label(label);
        closure.generate(label, before);
        emit("goto L" + before);
        _goto(before);
    }
    @Override
    public String build() {
        final String assembly = ".Lwhilebegin" +
                count +
                ":\n" +
                condition.build() +
                "  cmp  rax, 0\n" +
                "  je   .Lend" +
                count +
                '\n' +
                closure.build() +
                "  jmp  .Lwhilebegin" +
                count +
                '\n' +
                ".Lwhileend" +
                count +
                ":\n";
        count++;
        return assembly;
    }
    public String toS(int tab) {
        final StringBuilder builder = new StringBuilder();
        tab += 7;
        final String s = condition.toS(tab);
        tab += 1 + s.length();
        builder.append("(while ")
                .append(s)
                .append(' ')
                .append(closure.toS(tab))
                .append(')');
        return builder.toString();
    }
    @Override
    public List<Instruction> gen() {
        return null;
    }
}
