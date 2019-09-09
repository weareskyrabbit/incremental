package assembly;

public class Call extends Instruction {
    final Label label;
    Call(final Label label) {
        this.label = label;
    }
    @Override
    String build() {
        return "  call " + label.name();
    }
}
