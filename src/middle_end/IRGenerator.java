package middle_end;

import io.Writer;
import ir.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class IRGenerator {
    private static final StringBuilder ir = new StringBuilder();
    private static List<Code> codes = new ArrayList<>();
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
        codes.add(new LocalLabel(index));
    }
    public static void emit_label(final String name) {
        ir.append(name)
                .append(":\n");
        codes.add(new GlobalLabel(name));

    }
    public static void emit(final String code) {
        ir.append("\t")
                .append(code)
                .append('\n');
    }
    public static void three_address(final String register, final String operator) {
        codes.add(new ThreeAddress(new Register(register),
                new Register(""),
                operator,
                new Register("")));
    }
    public static void three_address(final String register, final Operand left, final String operator,
                                     final Operand right) {
        codes.add(new ThreeAddress(new Register(register),
                left,
                operator,
                right));
    }
    public static void _return(final String operand) {
        codes.add(new Return(new Register(operand)));
    }
    public static void _print(final String operand) {
        codes.add(new Print(new Register(operand)));
    }
    public static void _goto(final int label) {
        codes.add(new Branch("", new Register(""), label));
    }
    public static void emit_jumps(final String condition, final int _true, final int _false) {
        if (_true != 0 && _false != 0) {
            emit("if " + condition + " goto L" + _true);
            emit("goto L" + _false);
        } else if (_true != 0) {
            emit("if " + condition + " goto L" + _true);
            codes.add(new Branch("if", new Register(condition), _true));
        } else if (_false != 0) {
            emit("iffalse "+ condition + " goto L" + _false);
            codes.add(new Branch("iffalse", new Register(condition), _false));
        }
    }
    public static String getIR() {
        constant_fold();
        final StringBuilder builder = new StringBuilder();
        codes.forEach(builder::append);
        final StringBuilder assembly = new StringBuilder(".intel_syntax noprefix\n");
        codes.forEach(code -> assembly.append(code.build()));
        try {
            Writer.use("test.ir2", writer -> writer.write(builder.toString()));
            Writer.use("test.s2", writer -> writer.write(assembly.toString()));
        } catch (IOException exception) {
            System.out.println("IOException");
        }
        return ir.toString();
    }
    // optimize
    private static void constant_fold() {
        codes = codes.stream()
                .map(Code::reduce)
                .collect(Collectors.toList());
    }
    private static void register_allocate() {

    }
    private static void reduce_deadcode() {
    }
}
