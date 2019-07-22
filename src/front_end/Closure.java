package front_end;

import java.util.List;

public class Closure extends Node {
    private final SymbolList symbols;
    private final List<Statement> statements;
    Closure(final SymbolList symbols, final List<Statement> statements) {
        this.symbols = symbols;
        this.statements = statements;
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
