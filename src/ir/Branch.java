package ir;

public class Branch implements Code {
    private final String instruction;
    private final Operand operand;
    private final int label;
    public Branch(final String instruction, final Operand operand, final int label) {
        this.instruction = instruction;
        this.operand = operand;
        this.label = label;
    }
    @Override
    public String toString() {
        return "\t" + instruction + " " + operand.toString() + " goto L" + label + "\n";
    }
    @Override
    public String build() {
        switch (instruction) {
            case "iffalse":
                return operand.build() +
                        "  cmp  rax, 0\n" +
                        "  je   .L" +
                        label +
                        "\n";
            case "":
                return "  jmp  .L" +
                        label +
                        "\n";
            default:
                System.out.println("Building Exception");
                System.exit(1);
                return null;
        }
    }
    @Override
    public Code reduce() {
        return this;
    }
    @Override
    public int toWC() {
        return 0;
    }
    @Override
    public String toAssembly() {
        return "";
    }
}
