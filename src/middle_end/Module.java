package middle_end;


import java.util.List;

public class Module {
    private final List<Function> functions;
    Module(final List<Function> functions) {
        this.functions = functions;
    }
    public void build() {
        Builder.append(".intel_syntax noprefix\n");
        for (final Function function : functions) {
            function.build();
        }
    }
}
