package front_end;

import static front_end.Parser.tab;

public class Function implements Node {
    final String name;
    final SymbolList arguments;
    final Closure closure;
    Function(final String name, final SymbolList arguments, final Closure closure) {
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
                .append(closure.to_S(tab))
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
