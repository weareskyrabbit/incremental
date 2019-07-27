package front_end.ast;

import front_end.SymbolList;

import static front_end.Parser.tab;

public class FunctionDeclaration implements Node {
    private final String name;
    private final SymbolList arguments;
    private final Closure closure;
    public FunctionDeclaration(final String name, final SymbolList arguments, final Closure closure) {
        this.name = name;
        this.arguments = arguments;
        this.closure = closure;
    }
    public String toS(int tab) {
        StringBuilder s = new StringBuilder();
        s.append(tab(tab))
                .append("(declare ")
                .append(name)
                .append('\n');
        tab += 9;
        s.append(tab(tab))
                .append(closure.toS(tab))
                .append(')');
        return s.toString();
    }

    @Override
    public String build() {
        return ".global " + name + "\n" +
                name + ":\n" +
                closure.build();
    }
}
