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
    public List<Integer> toWordCode() {
        return instructions.stream()
                .map(Code::toWordCode)
                .collect(Collectors.toList());
    }
}
