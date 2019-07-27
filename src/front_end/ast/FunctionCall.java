package front_end.ast;

import java.util.List;

public class FunctionCall implements Expression {
    private final String name;
    private final List<Expression> arguments;
    private static final String[] registers = {"rdi", "rsi", "rdx", "rcx", "r8", "r9"};
    public FunctionCall(final String name, final List<Expression> arguments) {
        this.name = name;
        if (arguments.size() > 4) {
            System.out.println("function, `" + name + "` call has too many arguments");
            System.exit(1);
        }
        this.arguments = arguments;
    }
    @Override
    public String build() {
        final StringBuilder assembly = new StringBuilder();
        final int size = arguments.size();
        for (int i = 0; i < size; i++) {
            assembly.append(arguments.get(i).build())
                    .append("  mov  ")
                    .append(registers[i])
                    .append(", rax\n");
        }
        assembly.append("  call ")
                .append(name)
                .append('\n');
        return assembly.toString();
    }

    @Override
    public String toS(int tab) {
        return "";
    }
}
