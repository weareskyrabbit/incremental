package back_end;

import front_end.Closure;

public class Builder {
    public String build(final Closure tree) {
        return ".intel_syntax noprefix\n" +
                ".global main\n" +
                "main:\n" +
                tree.build();
    }
    public static String prologue(final int locals) {
        return "  push rbp\n" +
                "  mov rbp, rsp\n" +
                "  sub rsp, " +
                locals +
                '\n';
    }
    public static String epilogue() {
        return "  mov rsp, rbp\n" +
                "  pop rbp\n" +
                "  ret\n";
    }
}
