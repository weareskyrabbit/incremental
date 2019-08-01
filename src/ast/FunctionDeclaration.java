package ast;

import front_end.LocalVariable;
import front_end.SymbolList;

import java.util.Iterator;

import static front_end.Parser.tab;
import static middle_end.IRGenerator.*;

public class FunctionDeclaration implements Node {
    private final String name;
    private final SymbolList arguments;
    private final Closure closure;
    public FunctionDeclaration(final String name, final SymbolList arguments, final Closure closure) {
        this.name = name;
        this.arguments = arguments;
        this.closure = closure;
    }
    public String generate() {
        final StringBuilder ir = new StringBuilder();
        clear();
        final int before = new_label();
        final int after = new_label();
        emit_label(before);
        closure.generate(before, after);
        emit_label(after);
        ir.append('[')
                .append(name)
                .append("]\n")
                .append(getIR());
        return ir.toString();
    }
    @Override
    public String build() {
        return ".global " + name + "\n" +
                name + ":\n" +
                closure.build();
    }
    public String toS(int tab) {
        tab += 9;
        StringBuilder builder = new StringBuilder();
        builder.append("(declare ")
                .append(name)
                .append(' ')
                .append('[');
        if (arguments.symbols.size() > 0) {
            for (Iterator<LocalVariable> iterator = arguments.symbols.values().iterator(); ; ) {
                LocalVariable element = iterator.next();
                builder.append(element.name);
                if (!iterator.hasNext()) {
                    break;
                }
                builder.append(' ');
            }
        }
        builder.append("]\n")
                .append(tab(tab))
                .append(closure.toS(tab))
                .append(')');
        return builder.toString();
    }
}
