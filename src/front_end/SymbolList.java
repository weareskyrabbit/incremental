package front_end;

import java.util.HashMap;
import java.util.Map;

public class SymbolList {
    final SymbolList enclosing;
    private final Map<String, Integer> symbols;
    SymbolList(final SymbolList enclosing) {
        this.enclosing = enclosing;
        symbols = new HashMap<>();
    }
    void declare(final String name) {
        if (!symbols.containsKey(name)) {
            symbols.put(name, 0);
        } else {
            System.out.println("`" + name + "` is already declared");
            System.exit(1);
        }
    }
    boolean declared(final String name) {
        if (symbols.containsKey(name)) {
            return true;
        } else if (enclosing != null) {
            return enclosing.declared(name);
        } else {
            return false;
        }
    }
    void assign(final String name, final int value) {
        if (symbols.containsKey(name)) {
            symbols.replace(name, value);
        } else if (enclosing != null) {
            enclosing.assign(name, value);
        } else {
            System.out.println("`" + name + "` is undeclared");
            System.exit(1);
        }
    }
}
