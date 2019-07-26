package front_end;

import java.util.List;

public class FunctionCall implements Statement {
    final String name;
    final List<Integer> arguments;
    static final String[] registers = {"rdi", "rsi", "rdx", "rcx", "r8", "r9"};
    FunctionCall(final String name, final List<Integer> arguments) {
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
            assembly.append("  mov  ")
                    .append(registers[i])
                    .append(", ")
                    .append(arguments.get(i))
                    .append('\n');
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
