package ast;

import back_end.Builder;
import front_end.LocalVariable;
import front_end.SymbolList;
import middle_end.Instruction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static front_end.RecursiveDescentParser.tab;

public class Closure extends Statement {
    // TODO replace it with Seq in mGroupy/src/front_end/inter
    final SymbolList symbols;
    private final List<Statement> statements;
    public Closure(final SymbolList symbols, final List<Statement> statements) {
        this.symbols = symbols;
        this.statements = statements;
    }
    @Override
    public void generate(final int before, final int after) {
        if (statements.isEmpty()) {
            return;
        }
        Sequence head = new Sequence(null);
        Sequence current = head;
        for(Iterator<Statement> iterator = statements.iterator(); ; ) {
            if (!iterator.hasNext()) {
                break;
            }
            Sequence element = new Sequence(iterator.next());
            current.next = element;
            current = element;
        }
        head.next.generate(before, after);
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
    @Override
    public List<Instruction> gen() {
        final List<Instruction> list = new ArrayList<>();
        statements.stream()
                .map(Statement::gen)
                .filter(Objects::nonNull)
                .forEach(list::addAll);
        return list.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
    public List<ir.Code> toIR2() {
        return statements.stream()
                .map(Statement::toIR)
                .collect(Collectors.toList());
    }
}
