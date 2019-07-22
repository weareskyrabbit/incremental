package front_end;

import back_end.Builder;

import java.util.List;

public class Closure extends Node {
    private final SymbolList symbols;
    private final List<Statement> statements;
    Closure(final SymbolList symbols, final List<Statement> statements) {
        this.symbols = symbols;
        this.statements = statements;
    }
    public String build() {
        StringBuilder assembly = new StringBuilder();
        assembly.append(Builder.prologue(symbols.symbols.size() * 8));
        statements.forEach(statement -> assembly.append(statement.build()));
        assembly.append(Builder.epilogue());
        return assembly.toString();
    }
    @Override
    public String toString() {
        if (statements.isEmpty()) {
            return "(closure)";
        }
        String list = statements.toString();
        list = list.substring(1, list.length() - 1).replace(",", "\n        ");
        return "(closure " +  list +")";
    }
}
