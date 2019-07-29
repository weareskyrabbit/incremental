package front_end.ast;

import back_end.Builder;
import front_end.LocalVariable;
import front_end.SymbolList;

import java.util.Iterator;
import java.util.List;

import static front_end.Parser.tab;

public class Closure implements Node {
    private final SymbolList symbols;
    private final List<Statement> statements;
    public Closure(final SymbolList symbols, final List<Statement> statements) {
        this.symbols = symbols;
        this.statements = statements;
    }
    @Override
    public String toIR() {
        if (statements.isEmpty()) {
            return "{}";
        }
        final StringBuilder ir = new StringBuilder();
        ir.append('[');
        if (symbols.symbols.size() > 0) {
            for (Iterator<LocalVariable> iterator = symbols.symbols.values().iterator(); ; ) {
                LocalVariable element = iterator.next();
                ir.append(element.name);
                if (!iterator.hasNext()) {
                    break;
                }
                ir.append(' ');
            }
        }
        ir.append("] {\n");
        for(Iterator<Statement> iterator = statements.iterator(); ; ) {
            Statement element = iterator.next();
            ir.append(element.toIR());
            if (!iterator.hasNext()) {
                break;
            }
            ir.append('\n');
        }
        ir.append('}');
        return ir.toString();
    }
    @Override
    public String build() {
        final StringBuilder assembly = new StringBuilder();
        assembly.append(Builder.prologue(symbols.symbols.size() * 8));
        statements.forEach(statement -> assembly.append(statement.build()));
        return assembly.toString();
    }
    @Override
    public String toS(int tab) {
        if (statements.isEmpty()) {
            return "(closure)";
        }
        final StringBuilder builder = new StringBuilder();
        tab += 9;
        builder.append("(closure ")
                .append('[');
        if (symbols.symbols.size() > 0) {
            for (Iterator<LocalVariable> iterator = symbols.symbols.values().iterator(); ; ) {
                LocalVariable element = iterator.next();
                builder.append(element.name);
                if (!iterator.hasNext()) {
                    break;
                }
                builder.append(' ');
            }
        }
        builder.append("]\n")
                .append(tab(tab));
        for(Iterator<Statement> iterator = statements.iterator(); ; ) {
            Statement element = iterator.next();
            builder.append(element.toS(tab));
            if (!iterator.hasNext()) {
                break;
            }
            builder.append('\n')
                    .append(tab(tab));
        }
        builder.append(')');
        return builder.toString();
    }
}
