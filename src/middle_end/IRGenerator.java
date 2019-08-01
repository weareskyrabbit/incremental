package middle_end;

public class IRGenerator {
    private static final StringBuilder ir = new StringBuilder();
    private static int label_count;
    public static void clear() {
        ir.delete(0, ir.toString().length());
    }
    public static int new_label() {
        return label_count++;
    }
    public static void emit_label(final int index) {
        ir.append('L')
                .append(index)
                .append(':');
    }
    public static void emit(final String code) {
        ir.append("\t")
                .append(code)
                .append('\n');
    }
    public static void emit_jumps(final String condition, final int _true, final int _false) {
        if (_true != 0 && _false != 0) {
            emit("if " + condition + " goto L" + _true);
            emit("goto L" + _false);
        } else if (_true != 0) {
            emit("if " + condition + " goto L" + _true);
        } else if (_false != 0) {
            emit("iffalse "+ condition + " goto L" + _false);
        }
    }
    public static String getIR() {
        return ir.toString();
    }
}
