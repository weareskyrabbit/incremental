package ir;

import java.util.List;
import java.util.stream.Collectors;

public class Function {
    final String name;
    final List<Code> instructions;
    public Function(final String name, final List<Code> instructions) {
        this.name = name;
        this.instructions = instructions;
    }
    public int instructions_size() {
        return instructions.size();
    }
    public List<Integer> toWC() {
        return instructions.stream()
                .map(Code::toWC)
                .collect(Collectors.toList());
    }
    public String toAssembly() {
        return "";
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(name)
                .append(" {\n");
        instructions.forEach(builder::append);
        builder.append("}\n");
        return builder.toString();
    }
}
